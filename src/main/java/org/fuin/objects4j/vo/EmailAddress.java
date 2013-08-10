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

import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.ContractViolationException;
import org.fuin.objects4j.common.Immutable;
import org.fuin.objects4j.common.Requires;
import org.fuin.objects4j.ui.Label;
import org.fuin.objects4j.ui.ShortLabel;
import org.fuin.objects4j.ui.Tooltip;

/**
 * A valid email address with all characters lower case.
 */
@Immutable
@ShortLabel("Email")
@Label("Email address")
@Tooltip("Identifies an email box to which email messages are delivered")
public final class EmailAddress extends AbstractStringBasedType<EmailAddress> implements
        StringSerializable {

    private static final long serialVersionUID = -7976802296383690770L;

    private String emailAddress;

    /**
     * Protected default constructor for deserialization.
     */
    protected EmailAddress() {
        super();
    }

    /**
     * Constructor with email address.
     * 
     * @param emailAddress
     *            Email address.
     */
    @Requires("(emailAddress!=null) && EmailAddressStrValidator.isValid(emailAddress)")
    public EmailAddress(final String emailAddress) {
        super();
        Contract.requireArgNotEmpty("emailAddress", emailAddress);
        if (!EmailAddressStrValidator.isValid(emailAddress)) {
            throw new ContractViolationException("The argument 'emailAddress' is not valid: '"
                    + emailAddress + "'");
        }

        this.emailAddress = emailAddress.trim().toLowerCase();
    }

    @Override
    public final String toString() {
        return emailAddress;
    }

    @Override
    public final String asString() {
        return emailAddress;
    }

    /**
     * Reconstructs the object from a given string.
     * 
     * @param str
     *            String as created by {@link #asString()}.
     * 
     * @return New instance parsed from <code>str</code>.
     */
    public static EmailAddress create(final String str) {
        return new EmailAddress(str);
    }

}
