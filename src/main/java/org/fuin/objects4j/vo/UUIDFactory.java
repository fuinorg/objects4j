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
 * Creates a {@link UUID}.
 */
@ThreadSafe
@ApplicationScoped
public final class UUIDFactory extends XmlAdapter<String, UUID> implements
        AttributeConverter<UUID, String>, ValueObjectConverter<String, UUID> {

    @Override
    public Class<String> getBaseTypeClass() {
        return String.class;
    }

    @Override
    public final Class<UUID> getValueObjectClass() {
        return UUID.class;
    }

    @Override
    public final boolean isValid(final String value) {
        return UUIDStrValidator.isValid(value);
    }

    @Override
    public final UUID toVO(final String value) {
        return UUID.fromString(value);
    }

    @Override
    public final String fromVO(final UUID value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    @Override
    public final UUID unmarshal(final String value) throws Exception {
        return toVO(value);
    }

    @Override
    public final String marshal(final UUID value) throws Exception {
        return fromVO(value);
    }

    @Override
    public final String convertToDatabaseColumn(final UUID value) {
        return fromVO(value);
    }

    @Override
    public final UUID convertToEntityAttribute(final String value) {
        return toVO(value);
    }

}
