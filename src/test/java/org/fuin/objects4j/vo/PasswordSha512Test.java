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

import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.units4j.AbstractPersistenceTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fuin.utils4j.Utils4J.deserialize;
import static org.fuin.utils4j.Utils4J.serialize;

//TESTCODE:BEGIN
public final class PasswordSha512Test extends AbstractPersistenceTest {

    @Test
    public final void testSerialize() {
        final PasswordSha512 original = new PasswordSha512(new Password("very-secret"));
        final PasswordSha512 copy = deserialize(serialize(original));
        assertThat(original).isEqualTo(copy);
    }

    @Test
    public final void testCreateWithPasswordValid() {
        assertThat(new PasswordSha512(new Password("very-secret"))).isEqualTo(new PasswordSha512(new Password("very-secret")));
        assertThat(new PasswordSha512(new Password("very-secret"))).isNotEqualTo(new PasswordSha512(new Password("very-secret2")));
    }

    @Test
    public final void testCreateWithStringValid() {
        final String verySecret = new PasswordSha512(new Password("very-secret")).toString();
        final String verySecret2 = new PasswordSha512(new Password("very-secret2")).toString();

        assertThat(new PasswordSha512(verySecret)).isEqualTo(new PasswordSha512(verySecret));
        assertThat(new PasswordSha512(verySecret)).isNotEqualTo(new PasswordSha512(verySecret2));
    }

    @Test
    public final void testCreateEmpty() {
        assertThatThrownBy(() -> {
            new PasswordSha512("");
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public final void testCreateTooShort() {
        assertThatThrownBy(() -> {
            new PasswordSha512("b4d4b13a02230f9672c92fd45aa44fd202bdba7de3fda640ac84ee5d54c"
                    + "57fde293b4bbddce1b9a56d63d674b47c4dd7e6d89536a1e126ebf0cd662e76e8be");
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public final void testCreateTooLong() {
        assertThatThrownBy(() -> {
            new PasswordSha512("b4d4b13a02230f9672c92fd45aa44fd202bdba7de3fda640ac84ee5d54c"
                    + "57fde293b4bbddce1b9a56d63d674b47c4dd7e6d89536a1e126ebf0cd662e76e8bec3b4");
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void testJPA() {

        // PREPARE
        beginTransaction();
        getEm().persist(new PasswordSha512ParentEntity(1));
        commitTransaction();

        // TEST UPDATE
        beginTransaction();
        final PasswordSha512ParentEntity entity = getEm().find(PasswordSha512ParentEntity.class, 1L);
        entity.setPasswordSha512(new PasswordSha512(new Password("abcd1234")));
        commitTransaction();

        // VERIFY
        beginTransaction();
        final PasswordSha512ParentEntity copy = getEm().find(PasswordSha512ParentEntity.class, 1L);
        assertThat(copy).isNotNull();
        assertThat(copy.getId()).isEqualTo(1);
        assertThat(copy.getPasswordSha512()).isNotNull();
        assertThat(copy.getPasswordSha512()).isEqualTo(new PasswordSha512(new Password("abcd1234")));
        commitTransaction();

    }

}
// TESTCODE:END
