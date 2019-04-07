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
 * Verifies the string is a range of hours of a day (24 hourRanges representation).<br>
 * <br>
 * Valid examples are:
 * <ul>
 * <li>'00:00-24:00' Full day</li>
 * <li>'09:00-17:00' From 9 am to 5 pm</li>
 * <li>'06:00-18:00' From 6 in the morning to 6 in the evening</li>
 * <li>'12:00-24:00' From noon to midnight</li>
 * <li>'17:00-03:00' From 5 pm (late afternoon) to 3 am (early morning)</li>
 * </ul>
 */
public final class HourRangeStrValidator implements ConstraintValidator<HourRangeStr, String> {

    @Override
    public final void initialize(final HourRangeStr constraintAnnotation) {
    }

    @Override
    public final boolean isValid(final String value, final ConstraintValidatorContext context) {
        return isValid(value);
    }

    /**
     * Check that a given string is a valid {@link org.fuin.objects4j.vo.HourRange}.
     * 
     * @param value
     *            Value to check.
     * 
     * @return Returns <code>true</code> if it's a valid string representation else <code>false</code> is returned.
     */
    public static final boolean isValid(final String value) {
        return HourRange.isValid(value);
    }

}
