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

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.AttributeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.fuin.objects4j.common.ThreadSafe;

/**
 * Converts a {@link UUID} into a String and back.
 */
@ThreadSafe
@ApplicationScoped
public final class UUIDConverter extends XmlAdapter<String, UUID> implements
        AttributeConverter<UUID, String> {

    @Override
    public final String marshal(final UUID value) throws Exception {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    @Override
    public final UUID unmarshal(final String value) throws Exception {
        return UUID.fromString(value);
    }

    @Override
    public final String convertToDatabaseColumn(final UUID value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    @Override
    public final UUID convertToEntityAttribute(final String value) {
        return UUID.fromString(value);
    }

}
