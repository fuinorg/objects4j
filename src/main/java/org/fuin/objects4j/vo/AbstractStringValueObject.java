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

import java.io.Serializable;

/**
 * Base class for value objects that that overrides {@link Object#hashCode()}
 * and {@link Object#equals(Object)} and it implements comparable based on the
 * {@link Object#toString()} method.
 * 
 * @param <T>
 *            Concrete type.
 */
public abstract class AbstractStringValueObject<T> implements SimpleValueObject<String>, Comparable<T>, Serializable {

    private static final long serialVersionUID = -8703130956302331118L;

    @Override
    public final int hashCode() {
        return toString().hashCode();
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
        return toString().equals(obj.toString());
    }

    @Override
    public final int compareTo(final T other) {
        return this.toString().compareTo(other.toString());
    }

    /**
     * Returns the length.
     * 
     * @return Number of characters.
     */
    public final int length() {
        return toString().length();
    }

    @Override
    public final String asBaseType() {
    	return toString();
    }
    
    @Override
    public abstract String toString();

    
}
