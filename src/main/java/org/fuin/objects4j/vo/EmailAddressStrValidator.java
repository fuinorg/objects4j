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

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Check that a given string is a well-formed email address.
 */
public final class EmailAddressStrValidator implements ConstraintValidator<EmailAddressStr, String> {

    private boolean strict = false;

    @Override
    public final void initialize(final EmailAddressStr annotation) {
        strict = annotation.strict();
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return isValid(value, strict);
    }

    /**
     * Check that a given string is a well-formed email address.
     * 
     * @param value
     *            Value to check.
     * @param strict
     *            Determines if the detailed syntax of the address is checked.
     *            If <code>strict</code> is true, many (but not all) of the
     *            RFC822 syntax rules are enforced.
     * 
     * @return Returns <code>true</code> if it's a valid email address else
     *         <code>false</code> is returned.
     */
    public static boolean isValid(final String value, final boolean strict) {
        if (value == null) {
            return true;
        }
        if (value.length() == 0) {
            return false;
        }
        try {
            InternetAddress.parse(value, strict);
            return true;
        } catch (final AddressException ex) {
            return false;
        }
    }

}
