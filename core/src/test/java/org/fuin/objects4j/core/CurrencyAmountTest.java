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
package org.fuin.objects4j.core;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fuin.utils4j.Utils4J.deserialize;
import static org.fuin.utils4j.Utils4J.serialize;

/**
 * Tests the {@link CurrencyAmount} class.
 */
public class CurrencyAmountTest  {

    @Test
    void testEqualsHashCode() {
        EqualsVerifier.forClass(CurrencyAmount.class)
                .suppress(Warning.BIGDECIMAL_EQUALITY)
                .withPrefabValues(Currency.class, cu("EUR"), cu("USD")).verify();
    }

    @Test
    @SuppressWarnings("java:S117") // Underscore in var name is ok here
    void testCompareTo() {

        final CurrencyAmount eur123_2 = new CurrencyAmount(bd(123, 2), cu("EUR"));
        final CurrencyAmount usd123_2 = new CurrencyAmount(bd(123, 2), cu("USD"));

        assertThat(eur123_2.compareTo(usd123_2)).isLessThan(0);
        assertThat(usd123_2.compareTo(eur123_2)).isGreaterThan(0);
        assertThat(usd123_2.compareTo(usd123_2)).isEqualTo(0);

    }

    @Test
    void testSerialize() {
        final CurrencyAmount original = new CurrencyAmount(bd(123.456, 3), cu("EUR"));
        final CurrencyAmount copy = deserialize(serialize(original));
        assertThat(original).isEqualTo(copy);
    }

    @Test
    void testAmountToStr() {

        assertThat(CurrencyAmount.amountToStr(null)).isNull();
        assertThat(CurrencyAmount.amountToStr(bd(-123.456, 3))).isEqualTo("-123.456");
        assertThat(CurrencyAmount.amountToStr(bd(123.456, 3))).isEqualTo("123.456");
        assertThat(CurrencyAmount.amountToStr(bd(123, 3))).isEqualTo("123.000");
        assertThat(CurrencyAmount.amountToStr(bd(12, 3))).isEqualTo("12.000");
        assertThat(CurrencyAmount.amountToStr(bd(1, 3))).isEqualTo("1.000");
        assertThat(CurrencyAmount.amountToStr(bd(0, 3))).isEqualTo("0.000");
        assertThat(CurrencyAmount.amountToStr(bd(0.1, 3))).isEqualTo("0.100");
        assertThat(CurrencyAmount.amountToStr(bd(0.12, 3))).isEqualTo("0.120");
        assertThat(CurrencyAmount.amountToStr(bd(0.123, 3))).isEqualTo("0.123");

    }

    @Test
    void testStrToAmount() {

        assertThat(CurrencyAmount.strToAmount(null)).isNull();
        assertThat(CurrencyAmount.strToAmount("-123.456")).isEqualTo(bd(-123.456, 3));
        assertThat(CurrencyAmount.strToAmount("123.456")).isEqualTo(bd(123.456, 3));
        assertThat(CurrencyAmount.strToAmount("123.000")).isEqualTo(bd(123, 3));
        assertThat(CurrencyAmount.strToAmount("12.000")).isEqualTo(bd(12, 3));
        assertThat(CurrencyAmount.strToAmount("1.000")).isEqualTo(bd(1, 3));
        assertThat(CurrencyAmount.strToAmount("0.000")).isEqualTo(bd(0, 3));
        assertThat(CurrencyAmount.strToAmount("0.100")).isEqualTo(bd(0.1, 3));
        assertThat(CurrencyAmount.strToAmount("0.120")).isEqualTo(bd(0.12, 3));
        assertThat(CurrencyAmount.strToAmount("0.123")).isEqualTo(bd(0.123, 3));

    }

    private static Currency cu(String code) {
        return Currency.getInstance(code);
    }

    private static BigDecimal bd(int number, int scale) {
        return new BigDecimal(number).setScale(scale, RoundingMode.HALF_UP);
    }

    private static BigDecimal bd(double number, int scale) {
        return new BigDecimal(number).setScale(scale, RoundingMode.HALF_UP);
    }

}
