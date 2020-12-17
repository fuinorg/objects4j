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

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

// CHECKSTYLE:OFF
@Entity(name = "HOUR_RANGES_PARENT")
public class HourRangesParentEntity {

    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "HOUR_RANGES", nullable = true)
    @Convert(converter = HourRangesConverter.class)
    private HourRanges hourRanges;

    public HourRangesParentEntity() {
        super();
    }

    public HourRangesParentEntity(long id) {
        super();
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public HourRanges getHourRanges() {
        return hourRanges;
    }

    public void setHourRanges(HourRanges hourRanges) {
        this.hourRanges = hourRanges;
    }

}
// CHECKSTYLE:ON
