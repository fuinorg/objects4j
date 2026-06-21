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
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class CurrencyAmountAttributeConverterTest {

    @Test
    public void testNull() {
        final CurrencyAmountAttributeConverter testee = new CurrencyAmountAttributeConverter();
        assertThat(testee.convertToDatabaseColumn(null)).isNull();
        assertThat(testee.convertToEntityAttribute(null)).isNull();
    }

    @Test
    public void testRoundTrip() {
        final CurrencyAmountAttributeConverter testee = new CurrencyAmountAttributeConverter();
        final CurrencyAmount value = new CurrencyAmount("1234.56", "EUR");
        assertThat(testee.convertToEntityAttribute(testee.convertToDatabaseColumn(value))).isEqualTo(value);
    }

}
