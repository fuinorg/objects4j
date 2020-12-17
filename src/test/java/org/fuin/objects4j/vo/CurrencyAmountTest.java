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
package org.fuin.objects4j.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fuin.utils4j.Utils4J.deserialize;
import static org.fuin.utils4j.Utils4J.serialize;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import jakarta.persistence.Query;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.fuin.units4j.AbstractPersistenceTest;
import org.junit.Test;

/**
 * Tests the {@link CurrencyAmount} class.
 */
// CHECKSTYLE:OFF
public class CurrencyAmountTest extends AbstractPersistenceTest {

    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(CurrencyAmount.class).withPrefabValues(Currency.class, cu("EUR"), cu("USD")).verify();
    }

    @Test
    public void testCompareTo() {

        final CurrencyAmount eur123_2 = new CurrencyAmount(bd(123, 2), cu("EUR"));
        final CurrencyAmount usd123_2 = new CurrencyAmount(bd(123, 2), cu("USD"));

        assertThat(eur123_2.compareTo(usd123_2)).isLessThan(0);
        assertThat(usd123_2.compareTo(eur123_2)).isGreaterThan(0);
        assertThat(usd123_2.compareTo(usd123_2)).isEqualTo(0);

    }

    @Test
    public final void testSerialize() {
        final CurrencyAmount original = new CurrencyAmount(bd(123.456, 3), cu("EUR"));
        final CurrencyAmount copy = deserialize(serialize(original));
        assertThat(original).isEqualTo(copy);
    }

    @Test
    public void testAmountToStr() {

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
    public void testStrToAmount() {

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

    @Test
    public void testJPA() {

        // PREPARE
        beginTransaction();
        final CurrencyAmountParentEntity cap1 = new CurrencyAmountParentEntity(1);
        getEm().persist(cap1);
        final CurrencyAmountParentEntity cap2 = new CurrencyAmountParentEntity(2);
        cap2.setAmount(new CurrencyAmount("100.10", Currency.getInstance("USD")));
        getEm().persist(cap2);
        final CurrencyAmountParentEntity cap3 = new CurrencyAmountParentEntity(3);
        cap3.setPrice(new CurrencyAmount("0.25", Currency.getInstance("USD")));
        getEm().persist(cap3);
        commitTransaction();

        // TEST UPDATE
        beginTransaction();
        CurrencyAmountParentEntity ca = getEm().find(CurrencyAmountParentEntity.class, 1L);
        ca.setAmount(new CurrencyAmount(new BigDecimal(1234.56).setScale(2, RoundingMode.HALF_UP), Currency.getInstance("EUR")));
        ca.setPrice(new CurrencyAmount(new BigDecimal(175701).setScale(0), Currency.getInstance("JPY")));
        commitTransaction();

        // VERIFY
        beginTransaction();

        // Read using entity manager
        CurrencyAmountParentEntity cap = getEm().find(CurrencyAmountParentEntity.class, 1L);
        assertThat(cap).isNotNull();
        assertThat(cap.getId()).isEqualTo(1);
        assertThat(cap.getAmount().getAmount()).isEqualTo(new BigDecimal(1234.56).setScale(2, RoundingMode.HALF_UP));
        assertThat(cap.getAmount().getCurrency()).isEqualTo(Currency.getInstance("EUR"));
        assertThat(cap.getPrice().getAmount()).isEqualTo(new BigDecimal(175701).setScale(0));
        assertThat(cap.getPrice().getCurrency()).isEqualTo(Currency.getInstance("JPY"));

        // Select using native SQL
        assertThat(executeSingleResult("select * from CURRENCY_AMOUNT_PARENT where AMOUNT='1234.56' AND CURRENCY='EUR'")).isNotNull();
        assertThat(executeSingleResult("select * from CURRENCY_AMOUNT_PARENT where PRICE='0.25 USD'")).isNotNull();
        assertThat(executeSingleResult("select * from CURRENCY_AMOUNT_PARENT where PRICE='175701 JPY'")).isNotNull();

        commitTransaction();

    }

    private Object executeSingleResult(String sql) {
        final Query query = getEm().createNativeQuery(sql);
        return query.getSingleResult();
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
// CHECKSTYLE:ON
