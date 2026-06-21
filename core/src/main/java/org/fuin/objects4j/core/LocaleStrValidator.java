/**
 * Copyright (C) 2015 Michael Schnell. All rights reserved.
 * http://www.fuin.org/
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see http://www.gnu.org/licenses/.
 */
package org.fuin.objects4j.core;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.common.ThreadSafe;

import java.util.Locale;
import java.util.Objects;

/**
 * Check that a given string is a valid {@link java.util.Locale}.
 */
@ThreadSafe
public final class LocaleStrValidator implements ConstraintValidator<LocaleStr, String> {

    @Override
    public final void initialize(final LocaleStr constraintAnnotation) {
        // No initialization required
    }

    @Override
    public final boolean isValid(final String value, final ConstraintValidatorContext context) {
        return isValid(value);
    }

    /**
     * Check that a given string is a valid {@link java.util.Locale}.
     *
     * @param value
     *            Value to check.
     *
     * @return Returns {@literal true} if it's a valid Locale else {@literal false} is returned.
     */
    public static boolean isValid(final String value) {
        if (value == null) {
            return true;
        }
        try {
            return LocaleHelper.validLocale(LocaleHelper.asLocale(value));
        } catch (final RuntimeException ex) {
            return false;
        }
    }

    /**
     * Tries to parse the argument is valid and throws an exception if this is not possible.
     *
     * @param name
     *            Name of the value for a possible error message.
     * @param value
     *            Value to check.
     *
     * @return Parsed value.
     *
     * @throws ConstraintViolationException
     *             The value was not valid.
     */
    // CHECKSTYLE:OFF:RedundantThrows
    public static Locale parseArg(final String name, final String value) throws ConstraintViolationException {
        // CHECKSTYLE:ON

        try {
            final Locale locale = LocaleHelper.asLocale(value);
            if (!LocaleHelper.validLocale(locale)) {
                throw new ConstraintViolationException("The argument '" + name + "' is not valid: '" + value + "'");
            }
            return Objects.requireNonNull(locale);
        } catch (final RuntimeException ex) {
            throw new ConstraintViolationException("The argument '" + name + "' is not valid: '" + value + "'");
        }

    }

}
