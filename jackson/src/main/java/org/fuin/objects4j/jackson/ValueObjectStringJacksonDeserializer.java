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
import jakarta.validation.constraints.NotNull;
import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.AsStringCapable;
import org.fuin.objects4j.common.ValueOfCapable;
import org.fuin.utils4j.TestOmitted;

import java.io.IOException;

/**
 * Converts a string into a type.
 *
 * @param <TYPE> Type to convert.
 */
@TestOmitted("Already tested with other classes")
public final class ValueObjectStringJacksonDeserializer<TYPE> extends StdDeserializer<TYPE> {

    private final ValueOfCapable<TYPE> vop;

    /**
     * Constructor with mandatory data.
     *
     * @param vop Provides a valueOf method.
     */
    public ValueObjectStringJacksonDeserializer(@NotNull final Class<TYPE> clasz,
                                                @NotNull final ValueOfCapable<TYPE> vop) {
        super(clasz);
        Contract.requireArgNotNull("vop", vop);
        this.vop = vop;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TYPE deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        final JsonToken currentToken = parser.getCurrentToken();
        if (currentToken.equals(JsonToken.VALUE_STRING)) {
            final String value = parser.getText().trim();
            return vop.valueOf(value);
        }
        if (currentToken.equals(JsonToken.VALUE_NULL)) {
            return null;
        }
        return (TYPE) context.handleUnexpectedToken(handledType(), parser);
    }

}
