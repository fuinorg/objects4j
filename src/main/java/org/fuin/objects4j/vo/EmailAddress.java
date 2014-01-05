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

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.ContractViolationException;
import org.fuin.objects4j.common.Immutable;
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
@XmlJavaTypeAdapter(EmailAddressFactory.class)
public final class EmailAddress extends AbstractStringValueObject<EmailAddress> {

    private static final long serialVersionUID = 811127657088134517L;

    @NotNull
    @EmailAddressStr
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
    public EmailAddress(@NotNull @EmailAddressStr final String emailAddress) {
        super();
        Contract.requireArgNotEmpty("emailAddress", emailAddress);
        this.emailAddress = parseArg("emailAddress", emailAddress);
    }

    @Override
    public final String toString() {
        return emailAddress.toString();
    }

    private static InternetAddress parseArg(@NotNull final String name, @NotNull final String value) {

        final String trimmedLowerCaseValue = value.trim().toLowerCase();
        try {
            final InternetAddress[] addr = InternetAddress.parse(trimmedLowerCaseValue, false);
            if (addr.length != 1) {
                throw new ContractViolationException(
                        "The argument 'emailAddress' is not a single address: '"
                                + trimmedLowerCaseValue + "'");
            }
            return addr[0];
        } catch (final AddressException ex) {
            throw new ContractViolationException("The argument 'emailAddress' is not valid: '"
                    + trimmedLowerCaseValue + "'");
        }

    }

}
