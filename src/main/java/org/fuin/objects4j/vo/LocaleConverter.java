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

import java.util.Locale;
import java.util.StringTokenizer;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.AttributeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.fuin.objects4j.common.ThreadSafe;

/**
 * Converts a {@link Locale} into a String and back.
 */
@ThreadSafe
@ApplicationScoped
public final class LocaleConverter extends XmlAdapter<String, Locale> implements
        AttributeConverter<Locale, String> {

    @Override
    public final String marshal(final Locale value) throws Exception {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    @Override
    public final Locale unmarshal(final String value) throws Exception {
        return asLocale(value);
    }

    @Override
    public final String convertToDatabaseColumn(final Locale value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    @Override
    public final Locale convertToEntityAttribute(final String value) {
        return asLocale(value);
    }

    /**
     * Returns the given string as locale. The locale is NOT checked against the
     * Java list of avilable locales.
     * 
     * @param value
     *            Value to convert into a locale.
     * 
     * @return Locale.
     */
    public static Locale asLocale(final String value) {
        Locale locale;
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
