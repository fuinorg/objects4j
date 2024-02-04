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
package org.fuin.objects4j.jaxb;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import javax.annotation.concurrent.ThreadSafe;
import org.fuin.objects4j.core.LocaleHelper;

import java.util.Locale;

/**
 * Converts a {@link Locale} into a String and back.
 */
@ThreadSafe
public final class LocaleXmlAdapter extends XmlAdapter<String, Locale> {

    @Override
    public final String marshal(final Locale value) throws Exception {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    @Override
    public final Locale unmarshal(final String value) throws Exception {
        return LocaleHelper.asLocale(value);
    }

}
