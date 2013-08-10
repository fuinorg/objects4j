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
 * Check that a given string is an allowed HEX encoded SHA512 password.
 */
public final class PasswordSha512StrValidator implements
        ConstraintValidator<PasswordSha512Str, String> {

    private static final Pattern PATTERN = Pattern.compile("[0-9a-fA-F]*",
            java.util.regex.Pattern.CASE_INSENSITIVE);

    @Override
    public final void initialize(final PasswordSha512Str constraintAnnotation) {
    }

    @Override
    public final boolean isValid(final String value, final ConstraintValidatorContext context) {
        return isValid(value);
    }

    /**
     * Check that a given string is an HEX encoded SHA512 password.
     * 
     * @param value
     *            Value to check.
     * 
     * @return Returns <code>true</code> if it's an allowed value else
     *         <code>false</code> is returned.
     */
    public static final boolean isValid(final String value) {
        if (value == null) {
            return true;
        }
        if (value.length() != 128) {
            return false;
        }
        return PATTERN.matcher(value).matches();
    }

}
