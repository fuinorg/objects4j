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
 * Verifies the string is an hour of a day (24 hours, sometimes called Military Time).
 * Valid examples are:
 * <ul> 
 * <li>'00:00' Midnight next/new day</li>
 * <li>'01:00' One hour after midnight</li>
 * <li>'11:30' Half hour before noon</li>
 * <li>'12:00' Noon</li>
 * <li>'13:00' One hour after noon</li>
 * <li>'23:59' One minute before midnight</li>
 * <li>'24:00' Midnight previous/current day</li>
 * </ul>
 */
public final class HourStrValidator implements ConstraintValidator<HourStr, String> {

    @Override
    public final void initialize(final HourStr constraintAnnotation) {
    }

    @Override
    public final boolean isValid(final String value, final ConstraintValidatorContext context) {
        return isValid(value);
    }

    /**
     * Check that a given string is a valid {@link org.fuin.objects4j.vo.Hour}.
     * 
     * @param value
     *            Value to check.
     * 
     * @return Returns <code>true</code> if it's a valid Hourd else <code>false</code> is returned.
     */
    public static final boolean isValid(final String value) {
        return Hour.isValid(value);
    }

    /**
     * Checks if the argument is valid and throws an exception if this is not the case.
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
    public static void requireArgValid(@NotNull final String name, @NotNull final String value) throws ConstraintViolationException {
        // CHECKSTYLE:ON

        if (!Hour.isValid(value)) {
            throw new ConstraintViolationException(
                    "The argument '" + name + "' does not represent a valid hour like '00:00' or '23:59' or '24:00': '" + value + "'");
        }

    }

}
