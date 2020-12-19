/**
 * Copyright (C) 2015 Michael Schnell. All rights reserved. 
 * http://www.fuin.org/
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
 * along with this library. If not, see http://www.gnu.org/licenses/.
 */
package org.fuin.objects4j.vo;

import jakarta.json.bind.adapter.JsonbAdapter;
import jakarta.persistence.AttributeConverter;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import org.fuin.objects4j.common.Contract;

/**
 * Converts a type into a string and back.
 * 
 * @param <TYPE>
 *            Type to convert.
 */
public abstract class ValueObjectStringConverter<TYPE extends AsStringCapable> extends XmlAdapter<String, TYPE>
        implements AttributeConverter<TYPE, String>, JsonbAdapter<TYPE, String> {

    private final ValueOfCapable<TYPE> vop;

    /**
     * Constructor with mandatory data.
     * 
     * @param vop
     *            Provides a valueOf method.
     */
    public ValueObjectStringConverter(@NotNull final ValueOfCapable<TYPE> vop) {
        super();
        Contract.requireArgNotNull("vop", vop);
        this.vop = vop;
    }

    // JAX-B

    @Override
    public final TYPE unmarshal(final String value) {
        return vop.valueOf(value);
    }

    @Override
    public final String marshal(final TYPE value) {
        if (value == null) {
            return null;
        }
        return value.asString();
    }

    // JPA

    @Override
    public final String convertToDatabaseColumn(final TYPE value) {
        return marshal(value);
    }

    @Override
    public final TYPE convertToEntityAttribute(final String value) {
        return unmarshal(value);
    }

    // JSONB Adapter

    @Override
    public final String adaptToJson(final TYPE obj) throws Exception {
        if (obj == null) {
            return null;
        }
        return obj.asString();
    }

    @Override
    public final TYPE adaptFromJson(final String str) throws Exception {
        return vop.valueOf(str);
    }

}
