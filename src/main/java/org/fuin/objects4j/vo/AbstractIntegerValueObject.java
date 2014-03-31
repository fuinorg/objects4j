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
 * {@link #asBaseType()} method.
 */
public abstract class AbstractIntegerValueObject implements ValueObjectWithBaseType<Integer>,
        Comparable<AbstractIntegerValueObject>, Serializable {

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
        final AbstractIntegerValueObject other = (AbstractIntegerValueObject) obj;
        return asBaseType().equals(other.asBaseType());
    }

    @Override
    public final int compareTo(final AbstractIntegerValueObject other) {
        return asBaseType().compareTo(other.asBaseType());
    }

    @Override
    public final Class<Integer> getBaseType() {
        return Integer.class;
    }

}
