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

import javax.annotation.concurrent.ThreadSafe;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Creates a {@link UserName}.
 */
@ThreadSafe
@Converter(autoApply = true)
public final class UserNameConverter extends AbstractValueObjectConverter<String, UserName>
        implements AttributeConverter<UserName, String> {

    @Override
    public Class<String> getBaseTypeClass() {
        return String.class;
    }

    @Override
    public final Class<UserName> getValueObjectClass() {
        return UserName.class;
    }

    @Override
    public final boolean isValid(final String value) {
        return UserNameStrValidator.isValid(value);
    }

    @Override
    public final UserName toVO(final String value) {
        if (value == null) {
            return null;
        }
        return new UserName(value);
    }

    @Override
    public final String fromVO(final UserName value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

}
