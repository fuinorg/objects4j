/**
 * Copyright (C) 2013 Future Invent Informationsmanagement GmbH. All rights
 * reserved. <http://www.fuin.org/>
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
 * along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fuin.objects4j.vo;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Check that a given string is a well-formed user id.
 */
public final class UserNameStrValidator implements ConstraintValidator<UserNameStr, String> {

    private static final Pattern PATTERN = Pattern.compile("[a-z][0-9a-z_\\-]*");

    @Override
    public final void initialize(final UserNameStr constraintAnnotation) {
    }

    @Override
    public final boolean isValid(final String value, final ConstraintValidatorContext context) {
        return isValid(value);
    }

    /**
     * Check that a given string is a well-formed user id.
     * 
     * @param value
     *            Value to check.
     * 
     * @return Returns <code>true</code> if it's a valid user id else
     *         <code>false</code> is returned.
     */
    public static final boolean isValid(final String value) {
        if (value == null) {
            return true;
        }
        if ((value.length() < 3) || (value.length() > 20)) {
            return false;
        }
        return PATTERN.matcher(value.toString()).matches();
    }

}
