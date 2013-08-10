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

import java.io.Serializable;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

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
public final class EmailAddress implements Comparable<EmailAddress>, Serializable,
        StringSerializable {

    private static final long serialVersionUID = -7976802296383690770L;

    private boolean strict;

    private InternetAddress emailAddress;

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
    @Requires("(emailAddress!=null) && EmailAddressStrValidator.isValid(emailAddress, false)")
    public EmailAddress(final String emailAddress) {
        this(emailAddress, false);
    }

    /**
     * Constructor with email address and strict check information.
     * 
     * @param emailAddress
     *            Email address.
     * @param strict
     *            Determines if the detailed syntax of the address is checked.
     *            If <code>strict</code> is true, many (but not all) of the
     *            RFC822 syntax rules are enforced.
     */
    @Requires("(emailAddress!=null) && EmailAddressStrValidator.isValid(emailAddress, strict)")
    public EmailAddress(final String emailAddress, final boolean strict) {
        super();
        Contract.requireArgNotEmpty("emailAddress", emailAddress);
        final String toCheck = emailAddress.trim().toLowerCase();
        try {
            final InternetAddress[] addr = InternetAddress.parse(toCheck, strict);
            if (addr.length != 1) {
                throw new ContractViolationException(
                        "The argument 'emailAddress' is not a single address: '" + toCheck + "'");
            }
            this.strict = strict;
            this.emailAddress = addr[0];
        } catch (final AddressException ex) {
            throw new ContractViolationException("The argument 'emailAddress' is not valid: '"
                    + toCheck + "'");
        }
    }
    
    // CHECKSTYLE:OFF Generated code
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
        result = prime * result + (strict ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EmailAddress other = (EmailAddress) obj;
        if (emailAddress == null) {
            if (other.emailAddress != null)
                return false;
        } else if (!emailAddress.equals(other.emailAddress))
            return false;
        if (strict != other.strict)
            return false;
        return true;
    }
    // CHECKSTYLE:ON
   

    @Override
    public final int compareTo(final EmailAddress other) {
        return this.toString().compareTo(other.toString());
    }

    /**
     * Returns the length.
     * 
     * @return Number of characters.
     */
    public final int length() {
        return toString().length();
    }

    @Override
    public final String toString() {
        return emailAddress.toString();
    }

    @Override
    public final String asString() {
        return emailAddress.toString() + "|" + strict;
    }

    /**
     * Reconstructs the object from a given string.
     * 
     * @param str
     *            String as created by {@link #asString()}.
     * 
     * @return New instance parsed from <code>str</code>.
     */
    @Requires({ "str != null", "A valid format as returned by 'asString()'" })
    public static EmailAddress create(final String str) {
        Contract.requireArgNotEmpty("str", str);
        final int p = str.indexOf('|');
        if (p < 0) {
            throw new ContractViolationException(
                    "The argument 'emailAddress' is not a valid format as returned by 'asString()': '"
                            + str + "'");
        }
        return new EmailAddress(str.substring(0, p), Boolean.parseBoolean(str.substring(p + 1)));
    }

}
