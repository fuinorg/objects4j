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

import org.fuin.objects4j.core.WeeklyOpeningHours;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WeeklyOpeningHoursTest extends AbstractPersistenceTest {

    @Test
    public void testJPA() {

        // PREPARE
        beginTransaction();
        getEm().persist(new WeeklyOpeningHoursParentEntity(1));
        commitTransaction();

        // TEST UPDATE
        beginTransaction();
        final WeeklyOpeningHoursParentEntity entity = getEm().find(WeeklyOpeningHoursParentEntity.class, 1L);
        entity.setWeeklyOpeningHours(w("Mon/Tue 06:00-18:00,Wed 06:00-12:00"));
        commitTransaction();

        // VERIFY
        beginTransaction();
        final WeeklyOpeningHoursParentEntity copy = getEm().find(WeeklyOpeningHoursParentEntity.class, 1L);
        assertThat(copy).isNotNull();
        assertThat(copy.getId()).isEqualTo(1);
        assertThat(copy.getWeeklyOpeningHours()).isNotNull();
        assertThat(copy.getWeeklyOpeningHours()).isEqualTo(w("Mon/Tue 06:00-18:00,Wed 06:00-12:00"));
        commitTransaction();

    }

    private WeeklyOpeningHours w(final String str) {
        return new WeeklyOpeningHours(str);
    }


}
