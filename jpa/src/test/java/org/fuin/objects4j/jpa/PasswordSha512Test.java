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

import org.fuin.objects4j.core.Password;
import org.fuin.objects4j.core.PasswordSha512;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

//TESTCODE:BEGIN
public final class PasswordSha512Test extends AbstractPersistenceTest {

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