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
 * Verifies the string is a valid abbreviated English day of the week 'Mon'-'Sun'(Monday to Sunday) plus 'PH' (Public Holiday).
 */
public final class DayOfTheWeekStrValidator implements ConstraintValidator<DayOfTheWeekStr, String> {

    @Override
    public final void initialize(final DayOfTheWeekStr constraintAnnotation) {
        // No initialization required
    }

    @Override
    public final boolean isValid(final String value, final ConstraintValidatorContext context) {
        return isValid(value);
    }

    /**
     * Check that a given string is a valid {@link org.fuin.objects4j.vo.DayOfTheWeek}.
     * 
     * @param value
     *            Value to check.
     * 
     * @return Returns <code>true</code> if it's a valid string else <code>false</code> is returned.
     */
    public static final boolean isValid(final String value) {
        return DayOfTheWeek.isValid(value);
    }

}
