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
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.fuin.objects4j.common.ValueObjectWithBaseType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test for {@link ValueObjectJacksonDeserializer}.
 */
class ValueObjectJacksonDeserializerTest {

    @Test
    void testReadsTheBaseValue() throws Exception {
        assertThat(mapper().readValue("1234.56", Amount.class).asBaseType())
                .isEqualByComparingTo(new BigDecimal("1234.56"));
    }

    @Test
    void testReadsWhatTheSerializerWrote() throws Exception {
        final ObjectMapper mapper = mapper();
        final String json = mapper.writeValueAsString(new Amount(new BigDecimal("19.00")));
        assertThat(mapper.readValue(json, Amount.class).asBaseType()).isEqualByComparingTo(new BigDecimal("19.00"));
    }

    @Test
    void testCoercesAQuotedNumber() throws Exception {
        // A base value written as a string - a long serialized with a ToStringSerializer, for example -
        // still reads back, because the mapper coerces it before the factory is applied.
        assertThat(mapper().readValue("\"1234.56\"", Amount.class).asBaseType())
                .isEqualByComparingTo(new BigDecimal("1234.56"));
    }

    @Test
    void testNullBecomesNull() throws Exception {
        assertThat(mapper().readValue("[null]", Amount[].class)[0]).isNull();
    }

    @Test
    void testMandatoryArguments() {
        assertThatThrownBy(() -> new ValueObjectJacksonDeserializer<>(Amount.class, null, Amount::new))
                .isInstanceOf(Exception.class);
        assertThatThrownBy(() -> new ValueObjectJacksonDeserializer<BigDecimal, Amount>(Amount.class,
                BigDecimal.class, null)).isInstanceOf(Exception.class);
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
