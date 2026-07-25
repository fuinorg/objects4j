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
import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.ThreadSafe;
import org.fuin.objects4j.common.ValueObjectWithBaseType;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

/**
 * Converts a value object into the base type it wraps and back, whatever that type is - a number stays a
 * JSON number and a string a JSON string.
 * <p>
 * Unlike {@link ValueObjectStringJsonbAdapter}, which always adapts to a string, JSON-B is handed the
 * base value and writes it in its natural JSON form. Abstract for the same reason as its string sibling:
 * JSON-B binds an adapter by its reified type arguments, which a subclass supplies.
 *
 * @param <BASE_TYPE> Type the value object wraps.
 * @param <TYPE> Value object to convert.
 */
@ThreadSafe
public abstract class ValueObjectJsonbAdapter<BASE_TYPE, TYPE extends ValueObjectWithBaseType<BASE_TYPE>>
        implements JsonbAdapter<TYPE, BASE_TYPE> {

    private final Function<BASE_TYPE, TYPE> factory;

    /**
     * Constructor with mandatory data.
     *
     * @param factory Creates the value object from its base value - usually its constructor.
     */
    public ValueObjectJsonbAdapter(final Function<BASE_TYPE, TYPE> factory) {
        super();
        Contract.requireArgNotNull("factory", factory);
        this.factory = factory;
    }

    @Override
    @Nullable
    public final BASE_TYPE adaptToJson(final @Nullable TYPE obj) throws Exception {
        if (obj == null) {
            return null;
        }
        return obj.asBaseType();
    }

    @Override
    @Nullable
    public final TYPE adaptFromJson(final @Nullable BASE_TYPE value) throws Exception {
        if (value == null) {
            return null;
        }
        return factory.apply(value);
    }

}
