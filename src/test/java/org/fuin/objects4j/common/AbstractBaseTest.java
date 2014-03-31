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
package org.fuin.objects4j.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Base class for unit tests with entity manager.
 */
public abstract class AbstractBaseTest {

    /**
     * Serializes the given object.
     * 
     * @param obj
     *            Object to serialize.
     * 
     * @return Serialized object.
     */
    protected final byte[] serialize(final Object obj) {
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                final ObjectOutputStream out = new ObjectOutputStream(baos);
                out.writeObject(obj);
                return baos.toByteArray();
            } finally {
                baos.close();
            }
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Deserializes a byte array to an object.
     * 
     * @param data
     *            Byte array to deserialize.
     * 
     * @return Object created from data.
     * 
     * @param <T>
     *            Type of returned data.
     */
    @SuppressWarnings("unchecked")
    protected final <T> T deserialize(final byte[] data) {
        try {
            final ByteArrayInputStream bais = new ByteArrayInputStream(data);
            try {
                final ObjectInputStream in = new ObjectInputStream(bais);
                return (T) in.readObject();
            } finally {
                bais.close();
            }
        } catch (final ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
