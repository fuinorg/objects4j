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

import org.fuin.objects4j.common.AsStringCapable;
import org.fuin.objects4j.common.ValueObjectWithBaseType;

import java.io.Serial;
import java.io.Serializable;

/**
 * Base class for value objects that that overrides {@link Object#hashCode()} and {@link Object#equals(Object)} and implements comparable
 * all based on the {@link #asBaseType()} method.
 */
public abstract class AbstractStringValueObject
        implements ValueObjectWithBaseType<String>, Comparable<AbstractStringValueObject>, Serializable, AsStringCapable {

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
        final AbstractStringValueObject other = (AbstractStringValueObject) obj;
        return asBaseType().equals(other.asBaseType());
    }

    @Override
    public final int compareTo(final AbstractStringValueObject other) {
        return this.asBaseType().compareTo(other.asBaseType());
    }

    /**
     * Returns the length.
     * 
     * @return Number of characters.
     */
    public final int length() {
        return asBaseType().length();
    }

    @Override
    public final Class<String> getBaseType() {
        return String.class;
    }

    @Override
    public final String asString() {
        return asBaseType();
    }

}
