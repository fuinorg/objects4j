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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

// CHECKSTYLE:OFF
@Entity(name = "PASSWORD_SHA512_PARENT")
public class PasswordSha512ParentEntity {

    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "PW", nullable = true)
    private PasswordSha512 pw;

    public PasswordSha512ParentEntity() {
        super();
    }

    public PasswordSha512ParentEntity(long id) {
        super();
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PasswordSha512 getPasswordSha512() {
        return pw;
    }

    public void setPasswordSha512(PasswordSha512 pw) {
        this.pw = pw;
    }

}
// CHECKSTYLE:ON
