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
 * Check that a given string is a well-formed email address.
 * <p>
 * Based on code from Hibernate Validator 4.0.2.GA.
 * </p>
 * 
 * @see org.hibernate.validator.constraints.impl.EmailValidator.
 * @author Emmanuel Bernard
 */
public final class EmailAddressStrValidator implements ConstraintValidator<EmailAddressStr, String> {

    private static final String ATOM = "[^\\x00-\\x1F^\\(^\\)^\\<^\\>^\\@^\\,^\\;^\\:"
            + "^\\\\^\\\"^\\.^\\[^\\]^\\s]";

    private static final String DOMAIN = "(" + ATOM + "+(\\." + ATOM + "+)*";

    private static final String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";

    private static final Pattern PATTERN = Pattern.compile("^" + ATOM + "+(\\." + ATOM + "+)*@"
            + DOMAIN + "|" + IP_DOMAIN + ")$", java.util.regex.Pattern.CASE_INSENSITIVE);

    @Override
    public final void initialize(final EmailAddressStr annotation) {
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return isValid(value);
    }

    /**
     * Check that a given string is a well-formed email address.
     * 
     * @param value
     *            Value to check.
     * 
     * @return Returns <code>true</code> if it's a valid email address else
     *         <code>false</code> is returned.
     */
    public static boolean isValid(final String value) {
        if (value == null) {
            return true;
        }
        if (value.length() == 0) {
            return false;
        }
        return PATTERN.matcher(value).matches();
    }

}
