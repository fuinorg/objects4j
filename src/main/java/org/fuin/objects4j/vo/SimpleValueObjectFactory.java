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

/**
 * Creates a simple value object from it's base type representation.
 * 
 * @param <BASE_TYPE>
 *            Restricted base type.
 * @param <VO_TYPE>
 *            Concrete simple value object type.
 */
public interface SimpleValueObjectFactory<BASE_TYPE, VO_TYPE> {

    /**
     * Returns the concrete class of the simple value object.
     * 
     * @return Type of the value object.
     */
    public Class<VO_TYPE> getSimpleValueObjectClass();

    /**
     * Verifies that the given value can be converted into a simple value object
     * using the factory.
     * 
     * @param value
     *            Value to check.
     * 
     * @return <code>true</code> if the value can be converted, else
     *         <code>false</code>.
     */
    public boolean isValid(BASE_TYPE value);

    /**
     * Creates a new instance of the simple value object from a base type.
     * 
     * @param value
     *            Representation of the value object as base type.
     * 
     * @return New value object instance.
     */
    public VO_TYPE create(BASE_TYPE value);

}
