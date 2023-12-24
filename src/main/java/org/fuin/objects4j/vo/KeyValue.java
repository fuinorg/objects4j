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

import jakarta.validation.constraints.NotNull;
import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.Immutable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Container for a key and a value.
 */
@Immutable
public final class KeyValue implements ValueObject {

    @TrimmedNotEmpty
    private String key;

    private Object value;

    /**
     * Protected default constructor for deserialization.
     */
    protected KeyValue() {
        super();
    }

    /**
     * Constructor with key and value.
     * 
     * @param key
     *            Key.
     * @param value
     *            Value.
     */
    public KeyValue(@NotNull @TrimmedNotEmpty final String key, final Object value) {
        super();
        Contract.requireArgNotNull("key", key);
        TrimmedNotEmptyValidator.requireArgValid("key", key);
        this.key = key.trim();
        this.value = value;
    }

    /**
     * Returns the key.
     * 
     * @return Key.
     */
    public final String getKey() {
        return key;
    }

    /**
     * Returns the value.
     * 
     * @return Value.
     */
    public final Object getValue() {
        return value;
    }

    /**
     * Returns the value as string.
     * 
     * @return Value or "null".
     */
    public final String getValueString() {
        if (value == null) {
            return "null";
        }
        return value.toString();
    }

    /**
     * Replaces all variables in the format "${NAME}" with the corresponding value. NAME is the name of a key from the <code>keyValue</code>
     * array.
     * 
     * @param message
     *            Message to replace.
     * @param keyValue
     *            Array of key values or <code>null</code>.
     * 
     * @return Replaced message.
     */
    public static String replace(final String message, final KeyValue... keyValue) {
        if (keyValue == null) {
            return message;
        }
        final Map<String, String> map = new HashMap<String, String>();
        for (final KeyValue kv : keyValue) {
            if (kv.getValue() instanceof Collection) {
                final StringBuilder sb = new StringBuilder();
                final Collection<?> coll = (Collection<?>) kv.getValue();
                int count = 0;
                for (final Object entry : coll) {
                    if (count > 0) {
                        sb.append(", ");
                    }
                    sb.append(nullSafeAsString(entry));
                    count++;
                }
                map.put(kv.getKey(), sb.toString());
            } else {
                map.put(kv.getKey(), kv.getValueString());
            }
        }
        return replaceVars(message, map);
    }

    private static String nullSafeAsString(final Object obj) {
        if (obj == null) {
            return "null";
        }
        return obj.toString();
    }

    /**
     * Replaces all variables inside a string with values from a map.
     * 
     * @param str
     *            Text with variables (Format: ${key} ) - May be <code>null</code> or empty.
     * @param vars
     *            Map with key/values (both of type <code>String</code> - Cannot be <code>null</code>.
     * 
     * @return String with replaced variables. Unknown variables will remain unchanged.
     */
    public static String replaceVars(final String str, final Map<String, String> vars) {

        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return str;
        }

        if (vars == null) {
            return str;
        }

        final StringBuilder sb = new StringBuilder();

        int end = -1;
        int from = 0;
        int start = -1;
        while ((start = str.indexOf("${", from)) > -1) {
            sb.append(str.substring(end + 1, start));
            end = str.indexOf('}', start + 1);
            if (end == -1) {
                // No closing bracket found...
                sb.append(str.substring(start));
                from = str.length();
            } else {
                final String key = str.substring(start + 2, end);
                final String value = vars.get(key);
                if (value == null) {
                    sb.append("${");
                    sb.append(key);
                    sb.append("}");
                } else {
                    sb.append(value);
                }
                from = end + 1;
            }
        }

        sb.append(str.substring(from));

        return sb.toString();
    }

}
