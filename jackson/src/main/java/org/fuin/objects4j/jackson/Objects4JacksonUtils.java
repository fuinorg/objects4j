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
package org.fuin.objects4j.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utilities for the Jackson serialization module.
 */
public final class Objects4JacksonUtils {

    /**
     * Private utility constructor.
     */
    private Objects4JacksonUtils() {
        throw new UnsupportedOperationException("Creating instances of a utility class is not allowed.");
    }

    /**
     * Creates all available Jakcson serializers necessary for the ESC implementation.
     *
     * @return New array with serializers.
     */
    public static JsonSerializer<?>[] joinJacksonSerializers(final JsonSerializer<?>[] serializersA,
                                                             final JsonSerializer<?>... serializersB) {
        return joinJacksonSerializerArrays(serializersA, serializersB);
    }

    /**
     * Creates all available JSON-B serializers necessary for the ESC implementation.
     *
     * @return New array with serializers.
     */
    public static JsonSerializer<?>[] joinJacksonSerializerArrays(final JsonSerializer<?>[]... serializerArrays) {
        final List<JsonSerializer<?>> all = joinArrays(serializerArrays);
        return all.toArray(new JsonSerializer<?>[0]);
    }

    /**
     * Creates all available JSON-B deserializers necessary for the ESC implementation.
     *
     * @return New array with deserializers.
     */
    public static JsonDeserializer<?>[] joinJacksonDeserializers(final JsonDeserializer<?>[] deserializersA,
                                                                 final JsonDeserializer<?>... deserializersB) {
        return joinJacksonDeserializerArrays(deserializersA, deserializersB);
    }

    /**
     * Creates all available JSON-B deserializers necessary for the ESC implementation.
     *
     * @return New array with deserializers.
     */
    public static JsonDeserializer<?>[] joinJacksonDeserializerArrays(final JsonDeserializer<?>[]... deserializerArrays) {
        final List<JsonDeserializer<?>> all = joinArrays(deserializerArrays);
        return all.toArray(new JsonDeserializer<?>[0]);
    }

    /**
     * Creates all available JSON-B serializers necessary for the ESC implementation.
     *
     * @return New array with serializers.
     */
    @SafeVarargs
    static <T> List<T> joinArrays(final T[]... arrays) {
        final List<T> all = new ArrayList<>();
        for (final T[] array : arrays) {
            all.addAll(Arrays.asList(array));
        }
        return all;
    }

    /**
     * Deserializes a node to the given type.
     *
     * @param jp    Parser used for reading JSON content
     * @param ctxt  Context that can be used to access information about this deserialization activity.
     * @param fqnClassName Fullyqualified name of the expected class.
     * @param node  Node with object.
     * @param <T>   Expected type.
     * @return Deserialized instance.
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(JsonParser jp, DeserializationContext ctxt, String fqnClassName, JsonNode node) {
        try {
            final Class<T> clasz = (Class<T>) Class.forName(fqnClassName);
            return deserialize(jp, ctxt, clasz, node);
        } catch (final ClassNotFoundException ex) {
            throw new IllegalStateException("The class '" + fqnClassName + "' needed to deserialize the node is unknwon: " +  node, ex);
        }
    }

    /**
     * Deserializes a node to the given type.
     *
     * @param jp    Parser used for reading JSON content
     * @param ctxt  Context that can be used to access information about this deserialization activity.
     * @param clasz Expected class.
     * @param node  Node with object.
     * @param <T>   Expected type.
     * @return Deserialized instance.
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(JsonParser jp, DeserializationContext ctxt, Class<?> clasz, JsonNode node) {
        try {
            final JavaType javaType = ctxt.getTypeFactory().constructType(clasz);
            final JsonDeserializer<?> deserializer = ctxt.findRootValueDeserializer(javaType);
            final JsonParser parser = node.traverse(jp.getCodec());
            parser.nextToken();
            return (T) deserializer.deserialize(parser, ctxt);
        } catch (final Exception ex) {
            throw new IllegalStateException("Failed to deserialize '" +  clasz.getName() + "' from: " + node, ex);
        }
    }

}
