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

import org.fuin.objects4j.core.HourRanges;
import org.fuin.units4j.AbstractPersistenceTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HourRangesTest extends AbstractPersistenceTest {

    @Test
    public void testJPA() {

        // PREPARE
        beginTransaction();
        getEm().persist(new HourRangesParentEntity(1));
        commitTransaction();

        // TEST UPDATE
        beginTransaction();
        final HourRangesParentEntity entity = getEm().find(HourRangesParentEntity.class, 1L);
        entity.setHourRanges(new HourRanges("00:00-24:00"));
        commitTransaction();

        // VERIFY
        beginTransaction();
        final HourRangesParentEntity copy = getEm().find(HourRangesParentEntity.class, 1L);
        assertThat(copy).isNotNull();
        assertThat(copy.getId()).isEqualTo(1);
        assertThat(copy.getHourRanges()).isNotNull();
        assertThat(copy.getHourRanges().toString()).isEqualTo("00:00-24:00");
        commitTransaction();

    }

}
