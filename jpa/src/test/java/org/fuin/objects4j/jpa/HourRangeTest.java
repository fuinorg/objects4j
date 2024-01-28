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

import org.fuin.objects4j.core.HourRange;
import org.fuin.units4j.AbstractPersistenceTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HourRangeTest extends AbstractPersistenceTest {

    @Test
    public void testJPA() {

        // PREPARE
        beginTransaction();
        getEm().persist(new HourRangeParentEntity(1));
        commitTransaction();

        // TEST UPDATE
        beginTransaction();
        final HourRangeParentEntity entity = getEm().find(HourRangeParentEntity.class, 1L);
        entity.setHourRange(new HourRange("00:00-24:00"));
        commitTransaction();

        // VERIFY
        beginTransaction();
        final HourRangeParentEntity copy = getEm().find(HourRangeParentEntity.class, 1L);
        assertThat(copy).isNotNull();
        assertThat(copy.getId()).isEqualTo(1);
        assertThat(copy.getHourRange()).isNotNull();
        assertThat(copy.getHourRange().toString()).isEqualTo("00:00-24:00");
        commitTransaction();

    }


}
