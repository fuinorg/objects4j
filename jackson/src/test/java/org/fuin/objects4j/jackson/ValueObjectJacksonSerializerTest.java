/**
 * Copyright (C) 2013 Future Invent Informationsmanagement GmbH. All rights
 * reserved. <http://www.fuin.org/>
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fuin.objects4j.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.fuin.objects4j.common.ValueObjectWithBaseType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link ValueObjectJacksonSerializer}.
 */
class ValueObjectJacksonSerializerTest {

    @Test
    void testWritesTheBaseValueAndNotAnObject() throws Exception {
        // The point of the class: a value object is one value, so it must not become {"value":...}.
        assertThat(mapper().writeValueAsString(new Amount(new BigDecimal("1234.56")))).isEqualTo("1234.56");
    }

    @Test
    void testNullBecomesNull() throws Exception {
        assertThat(mapper().writeValueAsString(new Amount[] {null})).isEqualTo("[null]");
    }

    @Test
    void testTheMapperConfigurationOfTheBaseTypeApplies() throws Exception {
        // The base value goes through the mapper rather than being converted here, so a feature set for
        // the base type reaches the value object too - the reason writeObject is used over writeString.
        final ObjectMapper plain = mapper().disable(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
        assertThat(plain.writeValueAsString(new Amount(new BigDecimal("1E+2")))).isEqualTo("1E+2");
        final ObjectMapper asPlain = mapper().enable(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
        assertThat(asPlain.writeValueAsString(new Amount(new BigDecimal("1E+2")))).isEqualTo("100");
    }

    /** Value object whose base type is not a string, which is what the generic pair exists for. */
    static final class Amount implements ValueObjectWithBaseType<BigDecimal> {

        private final BigDecimal value;

        Amount(final BigDecimal value) {
            this.value = value;
        }

        @Override
        public Class<BigDecimal> getBaseType() {
            return BigDecimal.class;
        }

        @Override
        public BigDecimal asBaseType() {
            return value;
        }

    }

    /** Registers the pair under test for the fixture type. */
    private static ObjectMapper mapper() {
        final SimpleModule module = new SimpleModule();
        module.addSerializer(new ValueObjectJacksonSerializer<BigDecimal, Amount>(Amount.class));
        module.addDeserializer(Amount.class,
                new ValueObjectJacksonDeserializer<>(Amount.class, BigDecimal.class, Amount::new));
        return new ObjectMapper().registerModule(module);
    }
}
