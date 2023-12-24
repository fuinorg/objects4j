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

import jakarta.persistence.Query;
import org.fuin.units4j.AbstractPersistenceTest;
import org.junit.jupiter.api.Test;

import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the {@link Currency} class.
 */
// CHECKSTYLE:OFF
public class CurrencyTest extends AbstractPersistenceTest {

    @Test
    public void testJPA() {

        // PREPARE
        beginTransaction();
        final CurrencyParentEntity cap1 = new CurrencyParentEntity(1);
        getEm().persist(cap1);
        final CurrencyParentEntity cap2 = new CurrencyParentEntity(2);
        cap2.setCurrency(Currency.getInstance("USD"));
        getEm().persist(cap2);
        commitTransaction();

        // TEST UPDATE
        beginTransaction();
        CurrencyParentEntity ca = getEm().find(CurrencyParentEntity.class, 1L);
        ca.setCurrency(Currency.getInstance("EUR"));
        commitTransaction();

        // VERIFY
        beginTransaction();

        // Read using entity manager
        CurrencyParentEntity cap = getEm().find(CurrencyParentEntity.class, 1L);
        assertThat(cap).isNotNull();
        assertThat(cap.getId()).isEqualTo(1);
        assertThat(cap.getCurrency()).isEqualTo(Currency.getInstance("EUR"));

        // Select using native SQL
        assertThat(executeSingleResult("select * from CURRENCY_PARENT where CURRENCY='EUR'")).isNotNull();

        commitTransaction();

    }

    private Object executeSingleResult(String sql) {
        final Query query = getEm().createNativeQuery(sql);
        return query.getSingleResult();
    }

}
// CHECKSTYLE:ON
