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

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;

import org.fuin.objects4j.common.ConstraintViolationException;

/**
 * Check that a given string is an allowed password.
 */
public final class PasswordStrValidator
        implements ConstraintValidator<PasswordStr, String> {

    @Override
    public final void initialize(final PasswordStr constraintAnnotation) {
    }

    @Override
    public final boolean isValid(final String value,
            final ConstraintValidatorContext context) {
        return isValid(value);
    }

    /**
     * Check that a given string is an allowed password.
     * 
     * @param value
     *            Value to check.
     * 
     * @return Returns <code>true</code> if it's an allowed password else
     *         <code>false</code> is returned.
     */
    public static final boolean isValid(final String value) {
        if (value == null) {
            return true;
        }
        if ((value.length() < 8) || (value.length() > 20)) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the argument is valid and throws an exception if this is not
     * the case.
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
        final String trimmedValue = value.trim();
        if (!isValid(trimmedValue)) {
            throw new ConstraintViolationException("The argument '" + name
                    + "' is not valid: '" + trimmedValue + "'");
        }
    }

}
