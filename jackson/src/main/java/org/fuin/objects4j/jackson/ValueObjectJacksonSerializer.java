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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.fuin.objects4j.common.ThreadSafe;
import org.fuin.objects4j.common.ValueObjectWithBaseType;

import java.io.IOException;

/**
 * Writes a value object as the base type it wraps, whatever that type is - a number stays a JSON number
 * and a string a JSON string.
 * <p>
 * Unlike {@link ValueObjectStringJacksonSerializer}, which always writes a string, the base value is
 * handed to the mapper rather than converted here. Anything configured for the base type therefore
 * applies to the value object too: a {@code BigDecimal} honours
 * {@code SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN}, and a {@code Long} registered with a
 * {@code ToStringSerializer} is written as a string so a JavaScript client cannot round it.
 *
 * @param <BASE_TYPE> Type the value object wraps.
 * @param <TYPE> Value object to convert.
 */
@ThreadSafe
public final class ValueObjectJacksonSerializer<BASE_TYPE, TYPE extends ValueObjectWithBaseType<BASE_TYPE>>
        extends StdSerializer<TYPE> {

    /**
     * Constructor with mandatory data.
     *
     * @param clasz Type handled by this serializer.
     */
    public ValueObjectJacksonSerializer(final Class<TYPE> clasz) {
        super(clasz);
    }

    @Override
    public void serialize(final TYPE value, final JsonGenerator gen, final SerializerProvider provider)
            throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeObject(value.asBaseType());
        }
    }

}
