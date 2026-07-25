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
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.ThreadSafe;
import org.fuin.objects4j.common.ValueObjectWithBaseType;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.util.function.Function;

/**
 * Reads a value object from the base type it wraps, whatever that type is.
 * <p>
 * The base value is read by the mapper, so its usual coercions apply: a {@code Long} written as a string
 * reads back as a number, and a {@code BigDecimal} keeps its scale.
 *
 * @param <BASE_TYPE> Type the value object wraps.
 * @param <TYPE> Value object to convert.
 */
@ThreadSafe
public final class ValueObjectJacksonDeserializer<BASE_TYPE, TYPE extends ValueObjectWithBaseType<BASE_TYPE>>
        extends StdDeserializer<TYPE> {

    private final Class<BASE_TYPE> baseClass;

    private final Function<BASE_TYPE, TYPE> factory;

    /**
     * Constructor with mandatory data.
     *
     * @param clasz Type handled by this deserializer.
     * @param baseClass Type the value object wraps.
     * @param factory Creates the value object from its base value - usually its constructor.
     */
    public ValueObjectJacksonDeserializer(final Class<TYPE> clasz, final Class<BASE_TYPE> baseClass,
                                          final Function<BASE_TYPE, TYPE> factory) {
        super(clasz);
        Contract.requireArgNotNull("baseClass", baseClass);
        Contract.requireArgNotNull("factory", factory);
        this.baseClass = baseClass;
        this.factory = factory;
    }

    @Override
    @Nullable
    public TYPE deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        if (parser.getCurrentToken() == JsonToken.VALUE_NULL) {
            return null;
        }
        final BASE_TYPE value = parser.readValueAs(baseClass);
        if (value == null) {
            return null;
        }
        return factory.apply(value);
    }

}
