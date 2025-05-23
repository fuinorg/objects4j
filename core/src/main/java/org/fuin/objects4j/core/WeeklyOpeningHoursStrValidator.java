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

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Verifies if this represents weekly opening hours separated by a comma ','.<br>
 * Example: 'Mon-Fri 09:00-12:00+13:00-17:00,Sat/Sun 09:-12:00'.
 */
public final class WeeklyOpeningHoursStrValidator implements ConstraintValidator<WeeklyOpeningHoursStr, String> {

    @Override
    public final void initialize(final WeeklyOpeningHoursStr constraintAnnotation) {
        // No initialization required
    }

    @Override
    public final boolean isValid(final String value, final ConstraintValidatorContext context) {
        return isValid(value);
    }

    /**
     * Check that a given string is a valid {@link org.fuin.objects4j.core.WeeklyOpeningHours}.
     * 
     * @param value
     *            Value to check.
     * 
     * @return Returns {@literal true} if it's a valid string representation else {@literal false} is returned.
     */
    public static boolean isValid(final String value) {
        return WeeklyOpeningHours.isValid(value);
    }

}
