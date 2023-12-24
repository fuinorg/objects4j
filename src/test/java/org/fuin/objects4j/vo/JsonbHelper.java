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

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.adapter.JsonbAdapter;
import org.eclipse.yasson.FieldAccessStrategy;

/**
 * Utility class for this package.
 */
class JsonbHelper {

    private JsonbHelper() {
        throw new UnsupportedOperationException("Instances of utility classes are not allowed");
    }

    /**
     * Converts an object to JSON.
     * 
     * @param obj
     *            Object to serialize.
     * @param adapters
     *            Adapters to use.
     * 
     * @return JSON.
     */
    public static <T> String toJson(final T obj, final JsonbAdapter<?, ?>... adapters) {
        final JsonbConfig config = new JsonbConfig().withAdapters(adapters).withPropertyVisibilityStrategy(new FieldAccessStrategy());
        final Jsonb jsonb = JsonbBuilder.create(config);
        return jsonb.toJson(obj, obj.getClass());
    }

    /**
     * Converts JSON to an object.
     * 
     * @param json
     *            JSON.
     * @param clasz
     *            Class of the expected instance.
     * @param adapters
     *            Adapters to use.
     * 
     * @return New instance created using JSON input.
     */
    public static <T> T fromJson(final String json, final Class<T> clasz, final JsonbAdapter<?, ?>... adapters) {
        final JsonbConfig config = new JsonbConfig().withAdapters(adapters).withPropertyVisibilityStrategy(new FieldAccessStrategy());
        final Jsonb jsonb = JsonbBuilder.create(config);
        return jsonb.fromJson(json, clasz);
    }

}
