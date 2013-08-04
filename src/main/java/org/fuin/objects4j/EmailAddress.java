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
package org.fuin.objects4j;

import javax.validation.constraints.NotNull;

import org.fuin.objects4j.validation.EmailAddressStr;

/**
 * A valid email address with all characters lower case.
 */
@Immutable
public final class EmailAddress extends AbstractStringBasedType<EmailAddress> {

    private static final long serialVersionUID = -7976802296383690770L;

    @NotNull
    @EmailAddressStr
    private final String emailAddress;

    /**
     * Constructor with email address.
     * 
     * @param emailAddress
     *            Email address.
     */
    @Requires("(emailAddress!=null) && ValidEmailAddressValidator.isValid(emailAddress)")
    public EmailAddress(final String emailAddress) {
        super();
        this.emailAddress = emailAddress.trim().toLowerCase();
        Contract.requireValid(this);
    }

    @Override
    public final String toString() {
        return emailAddress;
    }

}
