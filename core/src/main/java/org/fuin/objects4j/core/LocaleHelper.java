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

import jakarta.annotation.Nullable;

import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Helper functions for the {@link Locale} class.
 */
public final class LocaleHelper {

    private LocaleHelper() {
        throw new UnsupportedOperationException("It's not allowed to create an instance of this utility class");
    }

    /**
     * Returns the given string as locale. The locale is NOT checked against the Java list of available locales.
     *
     * @param value
     *            Value to convert into a locale.
     *
     * @return Locale.
     */
    @Nullable
    public static Locale asLocale(@Nullable final String value) {
        if (value == null) {
            return null;
        }
        final Locale locale;
        final int p = value.indexOf("__");
        if (p > -1) {
            locale = new Locale(value.substring(0, p), null, value.substring(p + 2));
        } else {
            final StringTokenizer tok = new StringTokenizer(value, "_");
            if (tok.countTokens() == 1) {
                locale = new Locale(value);
            } else if (tok.countTokens() == 2) {
                locale = new Locale(tok.nextToken(), tok.nextToken());
            } else if (tok.countTokens() == 3) {
                locale = new Locale(tok.nextToken(), tok.nextToken(), tok.nextToken());
            } else {
                throw new IllegalArgumentException("Cannot convert: '" + value + "'");
            }
        }
        return locale;
    }

    /**
     * Verifies if the give locale is in the Java list of known locales.
     *
     * @param locale
     *            Locale to verify.
     *
     * @return TRUE if the locale is known else FALSE.
     */
    public static boolean validLocale(final Locale locale) {
        for (final Locale found : Locale.getAvailableLocales()) {
            if (found.equals(locale)) {
                return true;
            }
        }
        return false;
    }

}
