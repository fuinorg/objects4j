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
package org.fuin.objects4j.jsonb;

import jakarta.json.bind.adapter.JsonbAdapter;
import javax.annotation.concurrent.ThreadSafe;
import org.fuin.objects4j.core.LocaleHelper;

import java.util.Locale;

/**
 * Converts a {@link Locale} into a String and back.
 */
@ThreadSafe
public final class LocaleJsonbAdapter implements JsonbAdapter<Locale, String> {

    @Override
    public String adaptToJson(final Locale obj) throws Exception {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    @Override
    public Locale adaptFromJson(final String str) throws Exception {
        if (str == null) {
            return null;
        }
        return LocaleHelper.asLocale(str);
    }

}
