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
package org.fuin.objects4j.core;

import org.fuin.objects4j.common.HasPublicStaticIsValidMethod;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Base class for UUID value objects that that overrides {@link Object#hashCode()} and {@link Object#equals(Object)} and it implements
 * comparable based on the {@link #asBaseType()} method.
 */
@HasPublicStaticIsValidMethod
public abstract class AbstractUuidValueObject implements ValueObjectWithBaseType<UUID>, Comparable<AbstractUuidValueObject>, Serializable {

    @Serial
    private static final long serialVersionUID = 1000L;

    @Override
    public final int hashCode() {
        return asBaseType().hashCode();
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractUuidValueObject other = (AbstractUuidValueObject) obj;
        return asBaseType().equals(other.asBaseType());
    }

    @Override
    public final int compareTo(final AbstractUuidValueObject other) {
        return asBaseType().compareTo(other.asBaseType());
    }

    @Override
    public final Class<UUID> getBaseType() {
        return UUID.class;
    }

    /**
     * Verifies that a given string is a valid UUID.
     * 
     * @param value
     *            Value to check. A {@literal null} value returns {@literal true}.
     * 
     * @return TRUE if it's a valid key, else FALSE.
     */
    public static boolean isValid(final String value) {
        if (value == null) {
            return true;
        }
        final String uuidPattern = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-" + "[0-9a-f]{4}-[0-9a-f]{12}$";
        return Pattern.matches(uuidPattern, value);
    }

}
