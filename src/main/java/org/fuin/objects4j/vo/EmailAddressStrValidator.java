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
package org.fuin.objects4j.vo;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;

import org.fuin.objects4j.common.ConstraintViolationException;

/**
 * Check that a given string is a well-formed email address.
 */
public final class EmailAddressStrValidator
        implements ConstraintValidator<EmailAddressStr, String> {

    @Override
    public final void initialize(final EmailAddressStr annotation) {
        // Not used
    }

    @Override
    public boolean isValid(final String value,
            final ConstraintValidatorContext context) {
        return isValid(value);
    }

    /**
     * Check that a given string is a well-formed email address.
     * 
     * @param value
     *            Value to check.
     * 
     * @return Returns <code>true</code> if it's a valid email address else
     *         <code>false</code> is returned.
     */
    public static boolean isValid(final String value) {
        if (value == null) {
            return true;
        }
        if (value.length() == 0) {
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
     * Checks if the argument is a valid email and throws an exception if this
     * is not the case.
     * 
     * @param name
     *            Name of the value for a possible error message.
     * @param value
     *            Value to check.
     * 
     * @throws ConstraintViolationException
     *             The value was not valid.
     */
    // CHECKSTYLE:OFF:RedundantThrows
    public static void requireArgValid(@NotNull final String name,
            @NotNull final String value) throws ConstraintViolationException {
        // CHECKSTYLE:ON

        final String trimmedLowerCaseValue = value.trim().toLowerCase();
        if (!isValid(trimmedLowerCaseValue)) {
            throw new ConstraintViolationException("The argument '" + name
                    + "' is not valid: '" + trimmedLowerCaseValue + "'");
        }

    }

}
