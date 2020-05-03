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

/**
 * Tags any instance that is capable of delivering additional informations for serialization/deserialization. In contrast to XML where the
 * element name can be used to determine the type of the object, as with JSON there is often just an unknown type of object. This class
 * helps determining the full qualified name of the class and the tag name to use.
 * 
 * @param <DATA>
 *            Type of data returned.
 * 
 */
public interface MarshalInformation<DATA> {

    /**
     * Returns the class to be marshalled/unmarshalled.
     * 
     * @return Type of data.
     */
    public Class<DATA> getDataClass();

    /**
     * Returns the name of the tag that has an object of the given class.
     * 
     * @return Name to be used for the data element.
     */
    public String getDataElement();

    /**
     * Returns the data instance.
     * 
     * @return Data object.
     */
    public DATA getData();

}
