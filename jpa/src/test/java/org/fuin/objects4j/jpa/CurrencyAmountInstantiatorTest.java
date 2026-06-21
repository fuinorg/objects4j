/**
 * Copyright (C) 2013 Future Invent Informationsmanagement GmbH. All rights
 * reserved. <http://www.fuin.org/>
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fuin.objects4j.jpa;

import org.fuin.objects4j.core.CurrencyAmount;
import org.hibernate.metamodel.spi.ValueAccess;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

public final class CurrencyAmountInstantiatorTest {

    @Test
    public void testIsInstance() {
        final CurrencyAmountInstantiator testee = new CurrencyAmountInstantiator();
        assertThat(testee.isInstance(new CurrencyAmount("1234.56", "EUR"), null)).isTrue();
        assertThat(testee.isInstance("x", null)).isFalse();
    }

    @Test
    public void testIsSameClass() {
        final CurrencyAmountInstantiator testee = new CurrencyAmountInstantiator();
        assertThat(testee.isSameClass(new CurrencyAmount("1234.56", "EUR"), null)).isTrue();
    }

    @Test
    public void testInstantiate() {
        final CurrencyAmountInstantiator testee = new CurrencyAmountInstantiator();
        final BigDecimal amount = new BigDecimal("1234.56");
        final Currency currency = Currency.getInstance("EUR");
        final ValueAccess valueAccess = new ValueAccess() {
            @Override
            public Object[] getValues() {
                return new Object[] { amount, currency };
            }

            @Override
            @SuppressWarnings("unchecked")
            public <X> X getValue(final int i, final Class<X> aClass) {
                if (i == 0) {
                    return (X) amount;
                }
                if (i == 1) {
                    return (X) currency;
                }
                return null;
            }
        };
        assertThat(testee.instantiate(valueAccess, null)).isEqualTo(new CurrencyAmount(amount, currency));
    }

    @Test
    public void testInstantiateAllNull() {
        final CurrencyAmountInstantiator testee = new CurrencyAmountInstantiator();
        final ValueAccess valueAccess = new ValueAccess() {
            @Override
            public Object[] getValues() {
                return new Object[] { null, null };
            }

            @Override
            public <X> X getValue(final int i, final Class<X> aClass) {
                return null;
            }
        };
        assertThat(testee.instantiate(valueAccess, null)).isNull();
    }

}
