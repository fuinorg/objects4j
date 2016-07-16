/**
 * Copyright (C) 2013 Future Invent Informationsmanagement GmbH. All rights
 * reserved. <http://www.fuin.org/>
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
 * along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fuin.objects4j.vo;

import javax.persistence.AttributeConverter;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.fuin.objects4j.common.Contract;

/**
 * Converts a type into a string and back.
 * 
 * @param <TYPE> Type to convert.
 */
public abstract class ValueObjectStringConverter<TYPE extends AsStringCapable> extends XmlAdapter<String, TYPE>
        implements AttributeConverter<TYPE, String> {

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

    @Override
    public final String convertToDatabaseColumn(final TYPE value) {
        return marshal(value);
    }

    @Override
    public final TYPE convertToEntityAttribute(final String value) {
        return unmarshal(value);
    }

}