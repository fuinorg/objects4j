package org.fuin.objects4j.springboot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Currency;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the JSON-B, JAX-B and JPA adapters.
 *
 * Unfortunately Rest Assured cannot be used because it's not on "jakarta" namespace yet.
 * <a href="https://github.com/rest-assured/rest-assured/issues/1651">rest-assured issue #1651</a>
 * java.lang.NoClassDefFoundError: javax/json/bind/Jsonb
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class SpringBootAppTest {

    @Container
    @ServiceConnection
    static MariaDBContainer<?> db = new MariaDBContainer<>("mariadb:11")
            .withDatabaseName("testdb")
            .withUsername("mary")
            .withPassword("abc");

    @DynamicPropertySource
    static void elasticProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> db.getJdbcUrl());
    }

    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    @BeforeAll
    static void startContainers() {
        db.start();
    }

    @AfterAll
    static void stopContainers() {
        db.stop();
    }

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper mapper;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/data";
    }

    @Test
    void createReadDelete() throws URISyntaxException, IOException, InterruptedException {

        final String mediaType = MediaType.APPLICATION_JSON_VALUE;

        final UUID id = UUID.randomUUID();
        final Data original = new Data();
        original.id = id.toString();
        original.email = new EmailAddress("peter.parker@nowhere-no-no.com");
        original.passwordSha512 = new PasswordSha512(new Password("abc123xyz"));
        original.userName = new UserName("peter-parker");
        original.uuid = UUID.randomUUID();
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
        final String body = toJson(original);
        final HttpResponse<String> create = send(newBuilder(mediaType, getBaseUrl()).POST(HttpRequest.BodyPublishers.ofString(body)).build());
        assertThat(create.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        // READ
        final HttpResponse<String> read = send(newBuilder(mediaType, getBaseUrl() + "/" + id).GET().build());
        assertThat(read.statusCode()).isEqualTo(HttpStatus.OK.value());
        final Data copy = fromJson(read.body());
        assertThat(copy.id).isEqualTo(original.id);
        assertThat(copy.email).isEqualTo(original.email);
        assertThat(copy.passwordSha512).isEqualTo(original.passwordSha512);
        assertThat(copy.userName).isEqualTo(original.userName);
        assertThat(copy.uuid).isEqualTo(original.uuid);
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
        final HttpResponse<String> delete = send(newBuilder(mediaType, getBaseUrl() + "/" + id).DELETE().build());
        assertThat(delete.statusCode()).isEqualTo(HttpStatus.OK.value());

    }

    private static HttpRequest.Builder newBuilder(final String mediaType, final String uri) {
        return HttpRequest.newBuilder()
                .uri(asURI(uri))
                .headers("Content-Type", mediaType + ";charset=" + StandardCharsets.UTF_8.name(),
                        "Accept", mediaType + ";charset=" + StandardCharsets.UTF_8.name());
    }

    private String toJson(final Data obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (final JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Data fromJson(final String json) {
        if (json.isEmpty()) {
            throw new IllegalArgumentException("JSON cannot be empty!");
        }
        try {
            return mapper.readValue(json, Data.class);
        } catch (final JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
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