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

import org.fuin.objects4j.common.ContractViolationException;
import org.fuin.units4j.AbstractPersistenceTest;
import org.junit.Test;
import static org.fuin.utils4j.Utils4J.deserialize;
import static org.fuin.utils4j.Utils4J.serialize;

//TESTCODE:BEGIN
public final class UserNameTest extends AbstractPersistenceTest {

    @Test
    public final void testSerialize() {
        final String userName = "michael-1_a";
        final UserName original = new UserName(userName);
        final UserName copy = deserialize(serialize(original));
        assertThat(original).isEqualTo(copy);
    }

    @Test
    public final void testCreateValidLowerCase() {
        final String userName = "michael-1_a";
        assertThat(new UserName(userName).toString()).isEqualTo(userName);
        assertThat(new UserName(userName).length()).isEqualTo(userName.length());
    }

    @Test
    public final void testCreateValidUpperCase() {
        final String userName = "MICHAEL-1_a";
        assertThat(new UserName(userName).toString()).isEqualTo(userName.toLowerCase());
        assertThat(new UserName(userName).length()).isEqualTo(userName.length());
    }

    @Test
    public final void testEqualsLowerUpperCase() {
        assertThat(new UserName("abc-1_B")).isEqualTo(new UserName("AbC-1_b"));
    }

    @Test(expected = ContractViolationException.class)
    public final void testCreateEmpty() {
        new UserName("");
    }

    @Test(expected = ContractViolationException.class)
    public final void testCreateTooShort() {
        new UserName("ab");
    }

    @Test(expected = ContractViolationException.class)
    public final void testCreateTooLong() {
        new UserName("a12345678901234567890");
    }

    @Test(expected = ContractViolationException.class)
    public final void testCreateStartsWithNumber() {
        new UserName("1abc");
    }

    @Test(expected = ContractViolationException.class)
    public final void testCreateStartsWithUnderscore() {
        new UserName("_abc");
    }

    @Test(expected = ContractViolationException.class)
    public final void testCreateStartsWithHyphen() {
        new UserName("-abc");
    }

    @Test(expected = ContractViolationException.class)
    public final void testCreateContainsIllegalDoubleCross() {
        new UserName("abc#1");
    }

    @Test(expected = ContractViolationException.class)
    public final void testCreateContainsIllegalAtAndDot() {
        new UserName("abc@def.com");
    }

    @Test
    public void testJPA() {

        // PREPARE
        beginTransaction();
        getEm().persist(new UserNameParentEntity(1));
        commitTransaction();

        // TEST UPDATE
        beginTransaction();
        final UserNameParentEntity entity = getEm().find(UserNameParentEntity.class, 1L);
        entity.setUserName(new UserName("michael-1_a"));
        commitTransaction();

        // VERIFY
        beginTransaction();
        final UserNameParentEntity copy = getEm().find(UserNameParentEntity.class, 1L);
        assertThat(copy).isNotNull();
        assertThat(copy.getId()).isEqualTo(1);
        assertThat(copy.getUserName()).isNotNull();
        assertThat(copy.getUserName()).isEqualTo(new UserName("michael-1_a"));
        commitTransaction();

    }

}
// TESTCODE:END
