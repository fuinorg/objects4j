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

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Check that a given string is a well-formed date/time based on a pattern.
 */
public final class DateStrValidator implements ConstraintValidator<DateStr, String> {

    private SimpleDateFormat sdf;

    @Override
    public final void initialize(final DateStr constraintAnnotation) {
        sdf = new SimpleDateFormat(constraintAnnotation.value());
        sdf.setLenient(false);
    }

    @Override
    public final boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (value.isEmpty()) {
            return false;
        }
        try {
            sdf.parse(value);
        } catch (final ParseException ex) {
            return false;
        }
        return true;
    }

}
