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

import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.units4j.AbstractPersistenceTest;
import org.junit.Test;

//TESTCODE:BEGIN
public final class PasswordTest extends AbstractPersistenceTest {

    @Test
    public final void testSerialize() {
        final String password = "very-secret";
        final Password original = new Password(password);
        final Password copy = deserialize(serialize(original));
        assertThat(original).isEqualTo(copy);
    }

    @Test
    public final void testCreateValid() {
        final String password = "very-secret";
        assertThat(new Password(password).toString()).isEqualTo(password);
        assertThat(new Password(password).length()).isEqualTo(password.length());
    }

    @Test(expected = ConstraintViolationException.class)
    public final void testCreateEmpty() {
        new Password("");
    }

    @Test(expected = ConstraintViolationException.class)
    public final void testCreateTooShort() {
        new Password("abc");
    }

    @Test(expected = ConstraintViolationException.class)
    public final void testCreateTooLong() {
        new Password("123456789012345678901");
    }

    @Test
    public void testJPA() {

        // PREPARE
        beginTransaction();
        getEm().persist(new PasswordParentEntity(1));
        commitTransaction();

        // TEST UPDATE
        beginTransaction();
        final PasswordParentEntity entity = getEm().find(PasswordParentEntity.class, 1L);
        entity.setPassword(new Password("abcd1234"));
        commitTransaction();

        // VERIFY
        beginTransaction();
        final PasswordParentEntity copy = getEm().find(PasswordParentEntity.class, 1L);
        assertThat(copy).isNotNull();
        assertThat(copy.getId()).isEqualTo(1);
        assertThat(copy.getPassword()).isNotNull();
        assertThat(copy.getPassword()).isEqualTo(new Password("abcd1234"));
        commitTransaction();

    }

}
// TESTCODE:END
