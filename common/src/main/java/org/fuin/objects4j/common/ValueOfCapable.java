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
package org.fuin.objects4j.common;

import jakarta.annotation.Nullable;

/**
 * Functional interface used ton convert a string into a type.
 *
 * @param <T>
 *            Target type.
 */
public interface ValueOfCapable<T> {

    /**
     * Parses a string and returns the converted type.
     * 
     * @param value
     *            Value to convert. A {@literal null} value returns {@literal null}.
     * 
     * @return Converted value.
     */
    @Nullable
    public T valueOf(@Nullable String value);

}
