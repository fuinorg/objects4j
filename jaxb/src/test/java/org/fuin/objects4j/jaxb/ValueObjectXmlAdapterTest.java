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
package org.fuin.objects4j.jaxb;

import org.fuin.objects4j.common.ValueObjectWithBaseType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test for the {@link ValueObjectXmlAdapter} class.
 */
class ValueObjectXmlAdapterTest {

    private final AmountXmlAdapter testee = new AmountXmlAdapter();

    @Test
    void testMarshalUsesTheBaseValue() {
        // XML has no notion of a number, so the text is the base value - what this adds over the
        // AsStringCapable adapter is that the value object itself needs no asString().
        assertThat(testee.marshal(new Amount(new BigDecimal("1234.56")))).isEqualTo("1234.56");
    }

    @Test
    void testUnmarshalGoesThroughTheBaseType() {
        assertThat(testee.unmarshal("1234.56").asBaseType()).isEqualByComparingTo(new BigDecimal("1234.56"));
    }

    @Test
    void testMarshalUnmarshal() {
        final Amount original = new Amount(new BigDecimal("19.00"));
        assertThat(testee.unmarshal(testee.marshal(original)).asBaseType())
                .isEqualByComparingTo(original.asBaseType());
        // The scale survives, because the text is the base value's own representation.
        assertThat(testee.marshal(original)).isEqualTo("19.00");
    }

    @Test
    void testNullBecomesNull() {
        assertThat(testee.marshal(null)).isNull();
        assertThat(testee.unmarshal(null)).isNull();
    }

    @Test
    void testUnmarshalOfSomethingThatIsNoBaseValue() {
        assertThatThrownBy(() -> testee.unmarshal("not a number")).isInstanceOf(NumberFormatException.class);
    }

    @Test
    void testMandatoryArguments() {
        assertThatThrownBy(() -> new ValueObjectXmlAdapter<BigDecimal, Amount>(null, Amount::new) {
        }).isInstanceOf(Exception.class);
        assertThatThrownBy(() -> new ValueObjectXmlAdapter<BigDecimal, Amount>(BigDecimal::new, null) {
        }).isInstanceOf(Exception.class);
    }

    /** JAX-B binds an adapter by its reified type arguments, which this subclass supplies. */
    static final class AmountXmlAdapter extends ValueObjectXmlAdapter<BigDecimal, Amount> {
        AmountXmlAdapter() {
            super(BigDecimal::new, Amount::new);
        }
    }

    /** Value object whose base type is not a string, which is what the generic adapter exists for. */
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
}
