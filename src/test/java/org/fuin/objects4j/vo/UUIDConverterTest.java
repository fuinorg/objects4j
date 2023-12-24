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

import org.fuin.units4j.AbstractPersistenceTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

//TESTCODE:BEGIN
public class UUIDConverterTest extends AbstractPersistenceTest {

    @Test
    public void testMarshal() throws Exception {

        // PREPARE
        final String str = "ec5468bd-9cd9-4a0f-a502-96190f6a0995";
        final UUID uuid = UUID.fromString(str);

        // TEST & VERIFY
        assertThat(new UUIDConverter().marshal(uuid)).isEqualTo(str);
        assertThat(new UUIDConverter().marshal(null)).isNull();

    }

    @Test
    public void testUnmarshal() throws Exception {

        // PREPARE
        final String str = "ec5468bd-9cd9-4a0f-a502-96190f6a0995";
        final UUID uuid = UUID.fromString(str);

        // TEST & VERIFY
        assertThat(new UUIDConverter().unmarshal(str)).isEqualTo(uuid);
        assertThat(new UUIDConverter().unmarshal(null)).isNull();

    }

    @Test
    public final void testJPA() {

        // PREPARE
        beginTransaction();
        getEm().persist(new UUIDParentEntity(1));
        commitTransaction();

        // TEST UPDATE
        final UUID uuid = UUID.randomUUID();
        beginTransaction();
        final UUIDParentEntity entity = getEm().find(UUIDParentEntity.class, 1L);
        entity.setUUID(uuid);
        commitTransaction();

        // VERIFY
        beginTransaction();
        final UUIDParentEntity copy = getEm().find(UUIDParentEntity.class, 1L);
        assertThat(copy).isNotNull();
        assertThat(copy.getId()).isEqualTo(1);
        assertThat(copy.getUUID()).isNotNull();
        assertThat(copy.getUUID()).isEqualTo(uuid);
        commitTransaction();

    }

}
// TESTCODE:END
