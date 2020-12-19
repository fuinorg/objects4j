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
import java.util.Set;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.NotNull;

import org.fuin.objects4j.common.ConstraintViolationException;

/**
 * Check that a given string is a well-formed currency.
 */
public final class CurrencyStrValidator implements ConstraintValidator<CurrencyStr, String> {

    private static final String EXPRESSION = "[A-Z]{3}";

    @Override
    public final void initialize(final CurrencyStr constraintAnnotation) {
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
     * @return Returns <code>true</code> if it's a valid currency amount else <code>false</code> is returned.
     */
    public static boolean isValid(final String value) {
        if (value == null) {
            return true;
        }
        if (value.length() == 0) {
            return false;
        }
        if (!(value.matches(EXPRESSION))) {
            return false;
        }
        final Set<Currency> currencies = Currency.getAvailableCurrencies();
        for (final Currency currency : currencies) {
            if (currency.getCurrencyCode().equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the argument is a valid currency amount and throws an exception if this is not the case.
     * 
     * @param name
     *            Name of the value for a possible error message.
     * @param value
     *            Value to check.
     * 
     * @throws ConstraintViolationException
     *             The value was not valid.
     */
    // CHECKSTYLE:OFF:RedundantThrows
    public static void requireArgValid(@NotNull final String name, @NotNull final String value) throws ConstraintViolationException {
        // CHECKSTYLE:ON

        if (!isValid(value)) {
            throw new ConstraintViolationException("The argument '" + name + "' is not valid: '" + value + "'");
        }

    }

}
