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

import javax.validation.constraints.NotNull;

import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.Immutable;
import org.fuin.objects4j.common.Requires;

/**
 * A password with a length between 8 and 20 characters.
 */
@Immutable
public final class Password extends AbstractStringBasedType<Password> {

    private static final long serialVersionUID = -7745110729063955842L;

    @NotNull
    @PasswordStr
    private final String password;

    /**
     * Constructor with password.
     * 
     * @param password
     *            Password.
     */
    @Requires("(password!=null) && ValidPasswordValidator.isValid(password)")
    public Password(final String password) {
        super();
        this.password = password;
        Contract.requireValid(this);
    }

    @Override
    public String toString() {
        return password;
    }

}
