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
 * User name with the following rules.
 * <ul>
 * <li>3-20 characters in length</li>
 * <li>Lower case letters (a-z)</li>
 * <li>Numbers (0-9)</li>
 * <li>Hyphens (-)</li>
 * <li>Underscores (_)</li>
 * <li>Starts not with an underscore, hyphen or number</li>
 * </ul>
 */
@Immutable
public final class UserName extends AbstractStringBasedType<UserName> {

    private static final long serialVersionUID = 9055520843135472634L;

    @NotNull
    @UserNameStr
    private final String userName;

    /**
     * Constructor with user name.
     * 
     * @param userName
     *            User name.
     */
    @Requires("(userName!=null) && ValidUserNameValidator.isValid(userName)")
    public UserName(final String userName) {
        super();
        this.userName = userName.trim().toLowerCase();
        Contract.requireValid(this);
    }

    @Override
    public String toString() {
        return userName;
    }

}
