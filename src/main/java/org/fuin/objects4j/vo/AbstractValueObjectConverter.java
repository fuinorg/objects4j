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
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Base class for vale object converters.
 * 
 * @param <BASE_TYPE>
 *            Type to convert the value object from/to.
 * @param <VO_TYPE>
 *            Concrete value object type.
 */
// CHECKSTYLE:OFF:LineLength
public abstract class AbstractValueObjectConverter<BASE_TYPE, VO_TYPE extends ValueObjectWithBaseType<BASE_TYPE>>
        extends XmlAdapter<BASE_TYPE, VO_TYPE>
        implements AttributeConverter<VO_TYPE, BASE_TYPE>, ValueObjectConverter<BASE_TYPE, VO_TYPE>, JsonbAdapter<VO_TYPE, BASE_TYPE> {
    // CHECKSTYLE:ON:LineLength

    // JAX-B

    @Override
    public final BASE_TYPE marshal(final VO_TYPE value) throws Exception {
        return fromVO(value);
    }

    @Override
    public final VO_TYPE unmarshal(final BASE_TYPE value) throws Exception {
        return toVO(value);
    }

    // JPA

    @Override
    public final BASE_TYPE convertToDatabaseColumn(final VO_TYPE value) {
        return fromVO(value);
    }

    @Override
    public final VO_TYPE convertToEntityAttribute(final BASE_TYPE value) {
        return toVO(value);
    }

    // JSONB Adapter

    @Override
    public final BASE_TYPE adaptToJson(final VO_TYPE obj) throws Exception {
        return fromVO(obj);
    }

    @Override
    public final VO_TYPE adaptFromJson(final BASE_TYPE str) throws Exception {
        return toVO(str);
    }

}
