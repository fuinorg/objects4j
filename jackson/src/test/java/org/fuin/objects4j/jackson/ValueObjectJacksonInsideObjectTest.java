package org.fuin.objects4j.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies the generic value object (de)serializers as <em>fields of an object</em>, which is how they
 * actually occur - the isolated tests say nothing about the surrounding object. {@link Data} is the
 * container every test in this package shares, so the two types are covered by its other tests as well.
 */
class ValueObjectJacksonInsideObjectTest {

    private static final String JSON = """
            {
              "anyDecimal": 19.00,
              "anyLong": 123456789012345678
            }""";

    @Test
    void testWriteInsideAnObject() throws Exception {
        final Data data = new Data();
        data.anyDecimal = new AnyDecimal(new BigDecimal("19.00"));
        data.anyLong = new AnyLong(123456789012345678L);

        // Asserted per member: both must be JSON numbers carrying the value. The scale of the written
        // decimal is Jackson's to choose (it may write 19.0 for 19.00), so it is compared numerically -
        // what matters is that a value object does not become a string or a nested object.
        final ObjectMapper mapper = mapper();
        final JsonNode node = mapper.readTree(mapper.writeValueAsString(data));
        assertThat(node.get("anyDecimal").isNumber()).isTrue();
        assertThat(node.get("anyDecimal").decimalValue()).isEqualByComparingTo(new BigDecimal("19.00"));
        assertThat(node.get("anyLong").isNumber()).isTrue();
        assertThat(node.get("anyLong").asLong()).isEqualTo(123456789012345678L);
    }

    @Test
    void testReadInsideAnObject() throws Exception {
        final Data data = mapper().readValue(JSON, Data.class);
        assertThat(data.anyDecimal.asBaseType()).isEqualByComparingTo(new BigDecimal("19.00"));
        assertThat(data.anyLong.asBaseType()).isEqualTo(123456789012345678L);
    }

    @Test
    void testTheScaleAndThePrecisionSurviveARoundTrip() throws Exception {
        final ObjectMapper mapper = mapper();
        final Data read = mapper.readValue(mapper.writeValueAsString(mapper.readValue(JSON, Data.class)), Data.class);
        // A long beyond 2^53 would be rounded if it went through a double on the way.
        assertThat(read.anyLong.asBaseType()).isEqualTo(123456789012345678L);
        assertThat(read.anyDecimal.asBaseType()).isEqualByComparingTo(new BigDecimal("19.00"));
    }

    /**
     * A mapper knowing the two test types. Their (de)serializers are not added to
     * {@link Objects4JJacksonModule} - that module registers the library's own types, not test ones.
     */
    private static ObjectMapper mapper() {
        final SimpleModule module = new SimpleModule();
        module.addSerializer(new ValueObjectJacksonSerializer<BigDecimal, AnyDecimal>(AnyDecimal.class));
        module.addDeserializer(AnyDecimal.class,
                new ValueObjectJacksonDeserializer<>(AnyDecimal.class, BigDecimal.class, AnyDecimal::new));
        module.addSerializer(new ValueObjectJacksonSerializer<Long, AnyLong>(AnyLong.class));
        module.addDeserializer(AnyLong.class,
                new ValueObjectJacksonDeserializer<>(AnyLong.class, Long.class, AnyLong::new));
        return new ObjectMapper().enable(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .registerModule(module);
    }

}
