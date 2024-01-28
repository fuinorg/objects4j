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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.fuin.objects4j.core.DayOfTheWeek;

@Entity(name = "DAY_OF_THE_WEEK_PARENT")
public class DayOfTheWeekParentEntity {

    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "DAY_OF_THE_WEEK", nullable = true)
    private DayOfTheWeek dayOfTheWeek;

    public DayOfTheWeekParentEntity() {
        super();
    }

    public DayOfTheWeekParentEntity(long id) {
        super();
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DayOfTheWeek getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(DayOfTheWeek dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

}
