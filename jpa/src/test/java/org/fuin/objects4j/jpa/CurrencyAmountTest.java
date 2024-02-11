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

import jakarta.persistence.Query;
import org.fuin.objects4j.core.CurrencyAmount;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the {@link CurrencyAmount} class.
 */
public class CurrencyAmountTest extends AbstractPersistenceTest {

    @Test
    public void testJPA() {

        // PREPARE
        beginTransaction();
        final CurrencyAmountParentEntity cap1 = new CurrencyAmountParentEntity(1);
        getEm().persist(cap1);
        final CurrencyAmountParentEntity cap2 = new CurrencyAmountParentEntity(2);
        cap2.setAmount(new CurrencyAmount("100.10", Currency.getInstance("USD")));
        getEm().persist(cap2);
        commitTransaction();

        // TEST UPDATE
        beginTransaction();
        CurrencyAmountParentEntity ca = getEm().find(CurrencyAmountParentEntity.class, 1L);
        ca.setAmount(new CurrencyAmount(new BigDecimal(1234.56).setScale(2, RoundingMode.HALF_UP), Currency.getInstance("EUR")));
        commitTransaction();

        // VERIFY
        beginTransaction();

        // Read using entity manager
        CurrencyAmountParentEntity cap = getEm().find(CurrencyAmountParentEntity.class, 1L);
        assertThat(cap).isNotNull();
        assertThat(cap.getId()).isEqualTo(1);
        assertThat(cap.getAmount().getAmount()).isEqualTo(new BigDecimal(1234.56).setScale(2, RoundingMode.HALF_UP));
        assertThat(cap.getAmount().getCurrency()).isEqualTo(Currency.getInstance("EUR"));

        // Select using native SQL
        assertThat(executeSingleResult("select * from CURRENCY_AMOUNT_PARENT where AMOUNT=1234.56 AND CURRENCY='EUR'")).isNotNull();

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
