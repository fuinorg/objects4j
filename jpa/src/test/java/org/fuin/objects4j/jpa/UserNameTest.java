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

import org.fuin.objects4j.core.UserName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

//TESTCODE:BEGIN
public final class UserNameTest extends AbstractPersistenceTest {

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