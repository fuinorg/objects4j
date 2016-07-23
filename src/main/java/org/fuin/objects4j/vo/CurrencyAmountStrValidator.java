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

import java.util.Currency;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;

import org.fuin.objects4j.common.ContractViolationException;

/**
 * Check that a given string is a well-formed currency amount.
 */
public final class CurrencyAmountStrValidator implements
        ConstraintValidator<CurrencyAmountStr, String> {

    private static final String DECIMAL = "((\\+|-)?([0-9]+(\\.?[0-9]+)))";

    private static final String INTEGER = "((\\+|-)?[0-9]+)";

    @Override
    public final void initialize(final CurrencyAmountStr constraintAnnotation) {
        // Not used

    }

    @Override
    public final boolean isValid(final String value, final ConstraintValidatorContext context) {
        return isValid(value);
    }

    /**
     * Check that a given string is a well-formed currency amount.
     * 
     * @param value
     *            Value to check.
     * 
     * @return Returns <code>true</code> if it's a valid currency amount else
     *         <code>false</code> is returned.
     */
    public static boolean isValid(final String value) {
        if (value == null) {
            return true;
        }
        if (value.length() == 0) {
            return false;
        }
        final int space = value.indexOf(' ');
        if (space == -1) {
            return false;
        }

        // Try converting amount
        final String amount = value.substring(0, space);
        if (!(amount.matches(DECIMAL) || amount.matches(INTEGER))) {
            return false;
        }

        // Try to convert code into a currency
        final String currencyCode = value.substring(space + 1);
        try {
            Currency.getInstance(currencyCode);
        } catch (RuntimeException ex) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the argument is a valid currency amount and throws an exception
     * if this is not the case.
     * 
     * @param name
     *            Name of the value for a possible error message.
     * @param value
     *            Value to check.
     * 
     * @throws ContractViolationException
     *             The value was not valid.
     */
    // CHECKSTYLE:OFF:RedundantThrows
    public static void requireArgValid(@NotNull final String name, @NotNull final String value)
            throws ContractViolationException {
        // CHECKSTYLE:ON

        if (!isValid(value)) {
            throw new ContractViolationException("The argument '" + name + "' is not valid: '"
                    + value + "'");
        }

    }

}
