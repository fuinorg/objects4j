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

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;

import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.ContractViolationException;

/**
 * Check that a given string is not <code>null</code> and the trimmed length is
 * greater than zero.
 */
public class TrimmedNotEmptyValidator implements ConstraintValidator<TrimmedNotEmpty, String> {

    @Override
    public final void initialize(final TrimmedNotEmpty constraintAnnotation) {
        // Not used
    }

    @Override
    public final boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.trim().length() > 0;
    }

    /**
     * Parses the value and throws an exception if the trimmed value is empty.
     * 
     * @param name
     *            Name of the value for a possible error message.
     * @param value
     *            Value to check.
     * 
     * @throws ContractViolationException
     *             The trimmed value was empty.
     */
    // CHECKSTYLE:OFF:RedundantThrows
    public static void requireArgValid(@NotNull final String name, @NotNull final String value)
            throws ContractViolationException {
        // CHECKSTYLE:ON
        final String trimmedValue = value.trim();
        Contract.requireArgNotEmpty(name, trimmedValue);
    }

}
