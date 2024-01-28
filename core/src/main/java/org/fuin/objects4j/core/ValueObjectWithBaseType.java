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

import jakarta.validation.constraints.NotNull;

/**
 * Value object that may be expressed in a more general type with relaxed restrictions. Often basic Java types like String or numeric values
 * (Long, Integer, ...) are used for this.
 * 
 * @param <BASE_TYPE>
 *            Base type that may represent this type of value object.
 */
public interface ValueObjectWithBaseType<BASE_TYPE> extends ValueObject {

    /**
     * Returns the base type that may be used to represent this value object. Often this is something like String or other basic Java types.
     * 
     * @return A type that is less restricted than the VO itself.
     */
    @NotNull
    public Class<BASE_TYPE> getBaseType();

    /**
     * Returns the object in it's base type representation.
     * 
     * @return Converted value object.
     */
    @NotNull
    public BASE_TYPE asBaseType();

}
