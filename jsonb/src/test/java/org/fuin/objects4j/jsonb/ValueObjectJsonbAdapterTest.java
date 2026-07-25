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
package org.fuin.objects4j.jsonb;

import org.fuin.objects4j.common.ValueObjectWithBaseType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test for the {@link ValueObjectJsonbAdapter} class.
 */
class ValueObjectJsonbAdapterTest {

    private final AmountAdapter testee = new AmountAdapter();

    @Test
    void testAdaptToJsonKeepsTheBaseType() throws Exception {
        // The point of the class: the base value is handed to JSON-B as it is, so a number stays a
        // number instead of being turned into a string as the AsStringCapable adapter would.
        assertThat(testee.adaptToJson(new Amount(new BigDecimal("1234.56"))))
                .isEqualByComparingTo(new BigDecimal("1234.56"));
    }

    @Test
    void testAdaptFromJsonBuildsTheValueObject() throws Exception {
        assertThat(testee.adaptFromJson(new BigDecimal("1234.56")).asBaseType())
                .isEqualByComparingTo(new BigDecimal("1234.56"));
    }

    @Test
    void testRoundTrip() throws Exception {
        final Amount original = new Amount(new BigDecimal("19.00"));
        assertThat(testee.adaptFromJson(testee.adaptToJson(original)).asBaseType())
                .isEqualByComparingTo(original.asBaseType());
    }

    @Test
    void testNullBecomesNull() throws Exception {
        assertThat(testee.adaptToJson(null)).isNull();
        assertThat(testee.adaptFromJson(null)).isNull();
    }

    @Test
    void testMandatoryArguments() {
        assertThatThrownBy(() -> new ValueObjectJsonbAdapter<BigDecimal, Amount>(null) {
        }).isInstanceOf(Exception.class);
    }

    /** JSON-B binds an adapter by its reified type arguments, which this subclass supplies. */
    static final class AmountAdapter extends ValueObjectJsonbAdapter<BigDecimal, Amount> {
        AmountAdapter() {
            super(Amount::new);
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
