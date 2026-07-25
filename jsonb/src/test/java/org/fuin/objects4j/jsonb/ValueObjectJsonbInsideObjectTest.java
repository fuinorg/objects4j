package org.fuin.objects4j.jsonb;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fuin.objects4j.jsonb.JsonbHelper.fromJson;
import static org.fuin.objects4j.jsonb.JsonbHelper.toJson;

/**
 * Verifies the generic value object adapter as a <em>field of an object</em>, which is how it actually
 * occurs - the isolated adapter test says nothing about the surrounding object. {@link Data} is the
 * container every test in this package shares, so the two types are covered by its other tests as well.
 */
class ValueObjectJsonbInsideObjectTest {

    private static final String JSON = """
            {
                "anyDecimal": 19.00,
                "anyLong": 123456789012345678
            }""";

    @Test
    void testWriteInsideAnObject() {
        final Data data = new Data();
        data.anyDecimal = new AnyDecimal(new BigDecimal("19.00"));
        data.anyLong = new AnyLong(123456789012345678L);

        // Both must be JSON numbers: not quoted, and not a nested object.
        assertThat(toJson(data)).isEqualTo("{\"anyDecimal\":19.00,\"anyLong\":123456789012345678}");
    }

    @Test
    void testReadInsideAnObject() {
        final Data data = fromJson(JSON, Data.class);
        assertThat(data.anyDecimal.asBaseType()).isEqualByComparingTo(new BigDecimal("19.00"));
        assertThat(data.anyLong.asBaseType()).isEqualTo(123456789012345678L);
    }

    @Test
    void testRoundTripKeepsTheValues() {
        final Data read = fromJson(toJson(fromJson(JSON, Data.class)), Data.class);
        assertThat(read.anyLong.asBaseType()).isEqualTo(123456789012345678L);
        assertThat(read.anyDecimal.asBaseType()).isEqualByComparingTo(new BigDecimal("19.00"));
    }

}
