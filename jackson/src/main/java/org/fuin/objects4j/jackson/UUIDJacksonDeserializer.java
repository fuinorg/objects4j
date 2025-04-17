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

import javax.annotation.concurrent.ThreadSafe;
import java.io.IOException;
import java.util.UUID;

/**
 * Converts a String into a {@link UUID}.
 */
@ThreadSafe
public final class UUIDJacksonDeserializer extends StdDeserializer<UUID> {

    /**
     * Default constructor.
     */
    public UUIDJacksonDeserializer() {
        super(UUID.class);
    }

    @Override
    public UUID deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        final JsonToken currentToken = parser.getCurrentToken();
        if (currentToken.equals(JsonToken.VALUE_STRING)) {
            final String value = parser.getText().trim();
            return UUID.fromString(value);
        }
        if (currentToken.equals(JsonToken.VALUE_NULL)) {
            return null;
        }
        return (UUID) context.handleUnexpectedToken(handledType(), parser);
    }

}
