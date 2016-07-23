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

import java.util.UUID;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;

import org.fuin.objects4j.common.ContractViolationException;

/**
 * Check that a given string is a valid {@link java.util.UUID}.
 */
public final class UUIDStrValidator implements ConstraintValidator<UUIDStr, String> {

    @Override
    public final void initialize(final UUIDStr constraintAnnotation) {
    }

    @Override
    public final boolean isValid(final String value, final ConstraintValidatorContext context) {
        return isValid(value);
    }

    /**
     * Check that a given string is a valid {@link java.util.UUID}.
     * 
     * @param value
     *            Value to check.
     * 
     * @return Returns <code>true</code> if it's a valid UUIDd else
     *         <code>false</code> is returned.
     */
    public static final boolean isValid(final String value) {
        if (value == null) {
            return true;
        }
        if (value.length() != 36) {
            return false;
        }
        try {
            UUID.fromString(value);
            return true;
        } catch (final RuntimeException ex) {
            return false;
        }
    }

    /**
     * Tries to parse the argument is valid and throws an exception if this is
     * not possible.
     * 
     * @param name
     *            Name of the value for a possible error message.
     * @param value
     *            Value to check.
     * 
     * @return Parsed value.
     * 
     * @throws ContractViolationException
     *             The value was not valid.
     */
    // CHECKSTYLE:OFF:RedundantThrows
    public static UUID parseArg(@NotNull final String name, @NotNull final String value)
            throws ContractViolationException {
        // CHECKSTYLE:ON

        if (value.length() != 36) {
            throw new ContractViolationException("The argument '" + name + "' is not valid: '"
                    + value + "'");
        }
        try {
            return UUID.fromString(value);
        } catch (final RuntimeException ex) {
            throw new ContractViolationException("The argument '" + name + "' is not valid: '"
                    + value + "'");
        }

    }

}
