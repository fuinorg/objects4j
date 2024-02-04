/**
 * Copyright (C) 2013 Future Invent Informationsmanagement GmbH. All rights
 * reserved. <http://www.fuin.org/>
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
 * along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fuin.objects4j.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tngtech.archunit.junit.ArchIgnore;

/**
 * Utility class for this package.
 */
@ArchIgnore
class JacksonHelper {

    private JacksonHelper() {
        throw new UnsupportedOperationException("Instances of utility classes are not allowed");
    }

    /**
     * Converts an object to JSON.
     *
     * @param obj Object to serialize.
     * @return JSON.
     */
    public static <T> String toJson(final T obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts JSON to an object.
     *
     * @param json  JSON.
     * @param clasz Class of the expected instance.
     * @return New instance created using JSON input.
     */
    public static <T> T fromJson(final String json, final Class<T> clasz) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, clasz);
        } catch (final JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

}