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
 * Creates a {@link Password}.
 */
@ThreadSafe
@Converter(autoApply = true)
public class PasswordConverter
        extends AbstractValueObjectConverter<String, Password>
        implements AttributeConverter<Password, String> {

    @Override
    public final Class<String> getBaseTypeClass() {
        return String.class;
    }

    @Override
    public final Class<Password> getValueObjectClass() {
        return Password.class;
    }

    @Override
    public final boolean isValid(final String value) {
        return PasswordStrValidator.isValid(value);
    }

    @Override
    public final String fromVO(final Password value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    @Override
    public final Password toVO(final String value) {
        if (value == null) {
            return null;
        }
        return new Password(value);
    }

}
