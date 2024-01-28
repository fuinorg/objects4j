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

@Entity(name = "ANY_STR_PARENT")
public class AnyStrParentEntity {

    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "ANY_STR", nullable = true)
    private AnyStr anyStr;

    public AnyStrParentEntity() {
        super();
    }

    public AnyStrParentEntity(long id) {
        super();
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AnyStr getAnyStr() {
        return anyStr;
    }

    public void setAnyStr(AnyStr anyStr) {
        this.anyStr = anyStr;
    }

}
