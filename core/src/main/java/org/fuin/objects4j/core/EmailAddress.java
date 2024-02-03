/**
 * Copyright (C) 2015 Michael Schnell. All rights reserved. 
 * http://www.fuin.org/
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
 * along with this library. If not, see http://www.gnu.org/licenses/.
 */
package org.fuin.objects4j.core;

import jakarta.annotation.Nullable;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.validation.constraints.NotNull;
import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.HasPublicStaticIsValidMethod;
import org.fuin.objects4j.common.HasPublicStaticValueOfMethod;
import javax.annotation.concurrent.Immutable;
import org.fuin.objects4j.ui.Label;
import org.fuin.objects4j.ui.ShortLabel;
import org.fuin.objects4j.ui.Tooltip;

import java.io.Serial;

/**
 * A valid email address with all characters lower case.
 */
@Immutable
@ShortLabel("Email")
@Label("Email address")
@Tooltip("Identifies an email box to which email messages are delivered")
@HasPublicStaticIsValidMethod
@HasPublicStaticValueOfMethod
public final class EmailAddress extends AbstractStringValueObject {

    @Serial
    private static final long serialVersionUID = 811127657088134517L;

    @NotNull
    private InternetAddress adr;

    /**
     * Protected default constructor for deserialization.
     */
    protected EmailAddress() {// NOSONAR Ignore JAXB default constructor
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
        this.adr = parseArg("emailAddress", emailAddress);
    }

    @Override
    public final String asBaseType() {
        return adr.toString();
    }

    @Override
    public final String toString() {
        return asBaseType();
    }

    /**
     * Converts a given string into an instance of this class.
     *
     * @param str
     *            String to convert.
     *
     * @return New instance.
     */
    @Nullable
    public static EmailAddress valueOf(@Nullable final String str) {
        if (str == null) {
            return null;
        }
        return new EmailAddress(str);
    }

    /**
     * Check that a given string is a well-formed email address.
     *
     * @param value
     *            Value to check.
     *
     * @return Returns {@literal true} if it's a valid email address else {@literal false} is returned.
     */
    public static boolean isValid(final String value) {
        if (value == null) {
            return true;
        }
        if (value.isEmpty()) {
            return false;
        }
        try {
            InternetAddress.parse(value, false);
            return true;
        } catch (final AddressException ex) {
            return false;
        }
    }

    /**
     * Checks if the argument is a valid email and throws an exception if this is not the case.
     *
     * @param name
     *            Name of the value for a possible error message.
     * @param value
     *            Value to check.
     *
     * @throws ConstraintViolationException
     *             The value was not valid.
     */
    public static void requireArgValid(@NotNull final String name, @NotNull final String value) throws ConstraintViolationException {

        final String trimmedLowerCaseValue = value.trim().toLowerCase();
        if (!isValid(trimmedLowerCaseValue)) {
            throw new ConstraintViolationException("The argument '" + name + "' is not valid: '" + trimmedLowerCaseValue + "'");
        }

    }

    private static InternetAddress parseArg(@NotNull final String name, @NotNull final String value) {

        final String trimmedLowerCaseValue = value.trim().toLowerCase();
        try {
            final InternetAddress[] addr = InternetAddress.parse(trimmedLowerCaseValue, false);
            if (addr.length != 1) {
                throw new ConstraintViolationException(
                        "The argument 'emailAddress' is not a single address: '" + trimmedLowerCaseValue + "'");
            }
            return addr[0];
        } catch (final AddressException ex) {
            throw new ConstraintViolationException("The argument 'emailAddress' is not valid: '" + trimmedLowerCaseValue + "'");
        }

    }

}
