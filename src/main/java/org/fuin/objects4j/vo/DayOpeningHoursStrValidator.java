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

/**
/**
 * Represents the opening hours of one day of the week.<br>
 * <br>
 * Valid examples are:
 * <ul>
 * <li>'Mon 09:00-12:00+13:00-17:00'</li>
 * <li>'Tue 09:00-17:00'</li>
 * <li>'Fri 18:00-03:00 (Every Friday from 6 pm to early morning next day</li>
 * </ul>
 */
public final class DayOpeningHoursStrValidator implements ConstraintValidator<DayOpeningHoursStr, String> {

    @Override
    public final void initialize(final DayOpeningHoursStr constraintAnnotation) {
        // No initialization required
    }

    @Override
    public final boolean isValid(final String value, final ConstraintValidatorContext context) {
        return isValid(value);
    }

    /**
     * Check that a given string is a valid {@link org.fuin.objects4j.vo.DayOpeningHours}.
     * 
     * @param value
     *            Value to check.
     * 
     * @return Returns <code>true</code> if it's a valid string representation else <code>false</code> is returned.
     */
    public static final boolean isValid(final String value) {
        return DayOpeningHours.isValid(value);
    }

}
