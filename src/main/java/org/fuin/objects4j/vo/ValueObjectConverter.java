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
 * Converts a value object into it's base type and back.
 * 
 * @param <BASE_TYPE>
 *            Type to convert the value object from/to.
 * @param <VO_TYPE>
 *            Concrete value object type.
 */
public interface ValueObjectConverter<BASE_TYPE, VO_TYPE extends ValueObjectWithBaseType<BASE_TYPE>> {

    /**
     * Returns the class of the type to convert from/to.
     * 
     * @return Type of the other object.
     */
    public Class<BASE_TYPE> getBaseTypeClass();

    /**
     * Returns the concrete class of the value object.
     * 
     * @return Type of the value object.
     */
    public Class<VO_TYPE> getValueObjectClass();

    /**
     * Verifies that the given value can be converted into a value object using
     * the factory. A <code>null</code> parameter will return <code>true</code>.
     * 
     * @param value
     *            Value to check.
     * 
     * @return <code>true</code> if the value can be converted, else
     *         <code>false</code>.
     */
    public boolean isValid(BASE_TYPE value);

    /**
     * Converts the base type into an value object. A <code>null</code>
     * parameter will return <code>null</code>.
     * 
     * @param value
     *            Representation of the value object as base type.
     * 
     * @return Value object.
     */
    public VO_TYPE toVO(BASE_TYPE value);

    /**
     * Converts the value object into an base type. A <code>null</code>
     * parameter will return <code>null</code>.
     * 
     * @param value
     *            Value object.
     * 
     * @return Base type.
     */
    public BASE_TYPE fromVO(VO_TYPE value);

}
