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
package org.fuin.objects4j.jpa;

import jakarta.persistence.AttributeConverter;
import jakarta.validation.constraints.NotNull;
import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.core.AsStringCapable;
import org.fuin.objects4j.core.ValueOfCapable;

/**
 * Converts a type into a string and back.
 *
 * @param <TYPE>
 *            Type to convert.
 */
public abstract class ValueObjectStringAttributeConverter<TYPE extends AsStringCapable> implements AttributeConverter<TYPE, String> {

    private final ValueOfCapable<TYPE> vop;

    /**
     * Constructor with mandatory data.
     *
     * @param vop
     *            Provides a valueOf method.
     */
    public ValueObjectStringAttributeConverter(@NotNull final ValueOfCapable<TYPE> vop) {
        super();
        Contract.requireArgNotNull("vop", vop);
        this.vop = vop;
    }

    @Override
    public final String convertToDatabaseColumn(final TYPE value) {
        if (value == null) {
            return null;
        }
        return value.asString();
    }

    @Override
    public final TYPE convertToEntityAttribute(final String value) {
        return vop.valueOf(value);
    }

}
