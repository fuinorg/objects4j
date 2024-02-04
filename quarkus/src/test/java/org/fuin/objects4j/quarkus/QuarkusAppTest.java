package org.fuin.objects4j.quarkus;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.adapter.JsonbAdapter;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.eclipse.yasson.FieldAccessStrategy;
import org.fuin.objects4j.core.CurrencyAmount;
import org.fuin.objects4j.core.DayOfTheWeek;
import org.fuin.objects4j.core.DayOpeningHours;
import org.fuin.objects4j.core.EmailAddress;
import org.fuin.objects4j.core.Hour;
import org.fuin.objects4j.core.HourRange;
import org.fuin.objects4j.core.HourRanges;
import org.fuin.objects4j.core.MultiDayOfTheWeek;
import org.fuin.objects4j.core.Password;
import org.fuin.objects4j.core.PasswordSha512;
import org.fuin.objects4j.core.UserName;
import org.fuin.objects4j.core.WeeklyOpeningHours;
import org.fuin.objects4j.jaxb.JaxbUtils;
import org.fuin.objects4j.jsonb.JsonbUtils;
import org.fuin.utils4j.jaxb.MarshallerBuilder;
import org.fuin.utils4j.jaxb.UnmarshallerBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Currency;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fuin.utils4j.jaxb.JaxbUtils.unmarshal;

/**
 * Tests the JSON-B, JAX-B and JPA adapters.
 *
 * Unfortunately Rest Assured cannot be used because it's not on "jakarta" namespace yet.
 * <a href="https://github.com/rest-assured/rest-assured/issues/1651">rest-assured issue #1651</a>
 * java.lang.NoClassDefFoundError: javax/json/bind/Jsonb
 *
 */
@QuarkusTest
@QuarkusTestResource(MariaDbResource.class)
class QuarkusAppTest {

    private static final Map<String, Function<Data, String>> TO_STRING = Map.of(
            MediaType.APPLICATION_JSON, QuarkusAppTest::toJson,
            MediaType.APPLICATION_XML, QuarkusAppTest::toXml
    );

    private static final Map<String, Function<String, Data>> FROM_STRING = Map.of(
            MediaType.APPLICATION_JSON, QuarkusAppTest::fromJson,
            MediaType.APPLICATION_XML, QuarkusAppTest::fromXml
    );

    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    private static final String BASE_URL = "http://localhost:8081/data";

    @ParameterizedTest
    @ValueSource(strings = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    void createReadDelete(String mediaType) throws URISyntaxException, IOException, InterruptedException {

        final UUID id = UUID.randomUUID();
        final Data original = new Data();
        original.id = id.toString();
        original.email = new EmailAddress("peter.parker@nowhere-no-no.com");
        original.passwordSha512 = new PasswordSha512(new Password("abc123xyz"));
        original.userName = new UserName("peter-parker");
        original.uuid = UUID.randomUUID();
        // TODO original.amount = new CurrencyAmount(BigDecimal.TEN, Currency.getInstance("EUR"));
        original.price = new CurrencyAmount(BigDecimal.ONE, Currency.getInstance("EUR"));
        original.currency = Currency.getInstance("EUR");
        original.hour = new Hour("23:59");
        original.hourRange = new HourRange("00:00-24:00");
        original.hourRanges = new HourRanges("09:00-12:00+13:00-17:00");
        original.dayOfTheWeek = DayOfTheWeek.FRI;
        original.multiDayOfTheWeek = new MultiDayOfTheWeek("Mon/Tue");
        original.dayOpeningHours = new DayOpeningHours("MON 00:00-24:00");
        original.weeklyOpeningHours = new WeeklyOpeningHours("MON-FRI 06:00-18:00,SAT/SUN 06:00-12:00");
        original.locale = new Locale("en", "US", "NY");

        // CREATE
        final String body = TO_STRING.get(mediaType).apply(original);
        final HttpResponse<String> create = send(newBuilder(mediaType, BASE_URL).POST(HttpRequest.BodyPublishers.ofString(body)).build());
        assertThat(create.statusCode()).isEqualTo(Response.Status.CREATED.getStatusCode());

        // READ
        final HttpResponse<String> read = send(newBuilder(mediaType, BASE_URL + "/" + id).GET().build());
        assertThat(read.statusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        final Data copy = FROM_STRING.get(mediaType).apply(read.body());
        assertThat(copy.id).isEqualTo(original.id);
        assertThat(copy.email).isEqualTo(original.email);
        assertThat(copy.passwordSha512).isEqualTo(original.passwordSha512);
        assertThat(copy.userName).isEqualTo(original.userName);
        assertThat(copy.uuid).isEqualTo(original.uuid);
        // TODO assertThat(copy.amount).isEqualTo(original.amount);
        assertThat(copy.price).isEqualTo(original.price);
        assertThat(copy.hour).isEqualTo(original.hour);
        assertThat(copy.hourRange).isEqualTo(original.hourRange);
        assertThat(Optional.ofNullable(copy.hourRanges)).isEqualTo(Optional.of(original.hourRanges));
        assertThat(copy.dayOfTheWeek).isEqualTo(original.dayOfTheWeek);
        assertThat(Optional.ofNullable(copy.multiDayOfTheWeek)).isEqualTo(Optional.of(original.multiDayOfTheWeek));
        assertThat(copy.dayOpeningHours).isEqualTo(original.dayOpeningHours);
        assertThat(Optional.ofNullable(copy.weeklyOpeningHours)).isEqualTo(Optional.of(original.weeklyOpeningHours));
        assertThat(copy.locale).isEqualTo(original.locale);

        // DELETE
        final HttpResponse<String> delete = send(newBuilder(mediaType, BASE_URL + "/" + id).DELETE().build());
        assertThat(delete.statusCode()).isEqualTo(Response.Status.OK.getStatusCode());

    }

    private static HttpRequest.Builder newBuilder(final String mediaType, final String uri) {
        return HttpRequest.newBuilder()
                .uri(asURI(uri))
                .headers("Content-Type", mediaType + ";charset=" + StandardCharsets.UTF_8.name(),
                        "Accept", mediaType + ";charset=" + StandardCharsets.UTF_8.name());
    }

    private static String toJson(final Data obj) {
        final JsonbConfig config = new JsonbConfig()
                .withAdapters(JsonbUtils.getJsonbAdapters().toArray(new JsonbAdapter[0]))
                .withPropertyVisibilityStrategy(new FieldAccessStrategy());
        final Jsonb jsonb = JsonbBuilder.create(config);
        return jsonb.toJson(obj, obj.getClass());
    }

    private static Data fromJson(final String json) {
        if (json.isEmpty()) {
            throw new IllegalArgumentException("JSON cannot be empty!");
        }
        final JsonbConfig config = new JsonbConfig()
                .withAdapters(JsonbUtils.getJsonbAdapters().toArray(new JsonbAdapter[0]))
                .withPropertyVisibilityStrategy(new FieldAccessStrategy());
        final Jsonb jsonb = JsonbBuilder.create(config);
        return jsonb.fromJson(json, Data.class);
    }

    private static String toXml(Data data) {
        final Marshaller marshaller = new MarshallerBuilder()
                .addClassesToBeBound(Data.class)
                .withHandler(event -> false)
                .addAdapters(JaxbUtils.getJaxbAdapters())
                .build();
        try {
            final StringWriter writer = new StringWriter();
            marshaller.marshal(data, writer);
            return writer.toString();
        } catch (final JAXBException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Data fromXml(String xml) {
        if (xml.isEmpty()) {
            throw new IllegalArgumentException("XML cannot be empty!");
        }
        final Unmarshaller unmarshaller = new UnmarshallerBuilder()
                .addClassesToBeBound(Data.class)
                .withHandler(event -> false)
                .addAdapters(JaxbUtils.getJaxbAdapters())
                .build();
        return unmarshal(unmarshaller, xml);
    }

    private static HttpResponse<String> send(final HttpRequest request) {
        try {
            return CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (final IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static URI asURI(String uri) {
        try {
        return new URI(uri);
        } catch (final URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

}