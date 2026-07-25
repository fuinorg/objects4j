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
package org.fuin.objects4j.jaxb;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.ThreadSafe;
import org.fuin.objects4j.common.ValueObjectWithBaseType;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

/**
 * Converts a value object whose base type is not a string into XML and back.
 * <p>
 * XML has no notion of a number, so the base value is still written as text - what this adds over
 * {@link ValueObjectStringXmlAdapter} is that the value object does not have to be
 * {@code AsStringCapable}: the text is derived from its base value, and reading it back goes through the
 * base type first. That keeps the XML form of a {@code BigDecimal} or {@code Long} value object equal to
 * the form of the base value itself.
 *
 * @param <BASE_TYPE> Type the value object wraps.
 * @param <TYPE> Value object to convert.
 */
@ThreadSafe
public abstract class ValueObjectXmlAdapter<BASE_TYPE, TYPE extends ValueObjectWithBaseType<BASE_TYPE>>
        extends XmlAdapter<String, TYPE> {

    private final Function<String, BASE_TYPE> baseParser;

    private final Function<BASE_TYPE, TYPE> factory;

    /**
     * Constructor with mandatory data.
     *
     * @param baseParser Parses the text into the base value - for example {@code BigDecimal::new}.
     * @param factory Creates the value object from its base value - usually its constructor.
     */
    public ValueObjectXmlAdapter(final Function<String, BASE_TYPE> baseParser,
                                 final Function<BASE_TYPE, TYPE> factory) {
        super();
        Contract.requireArgNotNull("baseParser", baseParser);
        Contract.requireArgNotNull("factory", factory);
        this.baseParser = baseParser;
        this.factory = factory;
    }

    @Override
    @Nullable
    public final TYPE unmarshal(final @Nullable String value) {
        if (value == null) {
            return null;
        }
        return factory.apply(baseParser.apply(value));
    }

    @Override
    @Nullable
    public final String marshal(final @Nullable TYPE value) {
        if (value == null) {
            return null;
        }
        return String.valueOf(value.asBaseType());
    }

}
