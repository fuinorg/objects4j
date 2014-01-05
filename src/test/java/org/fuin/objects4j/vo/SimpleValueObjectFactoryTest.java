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

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Base class for testing concrete {@link SimpleValueObjectFactory}
 * implementations.
 */
// CHECKSTYLE:OFF
public abstract class SimpleValueObjectFactoryTest {

    /**
     * Marshals the given data.
     * 
     * @param data
     *            Data to serialize.
     * 
     * @return XML data.
     */
    protected final String marshal(final Data data) {
        try {
            final JAXBContext ctx = JAXBContext.newInstance(Data.class);
            final Marshaller marshaller = ctx.createMarshaller();
            final StringWriter writer = new StringWriter();
            marshaller.marshal(data, writer);
            return writer.toString();
        } catch (final JAXBException ex) {
            throw new RuntimeException("Error serializing test data", ex);
        }
    }

    /**
     * Unmarshals the given data.
     * 
     * @param xmlData
     *            XML data.
     * 
     * @return Data.
     */
    protected final Data unmarshal(final String xmlData) {
        try {
            final JAXBContext ctx = JAXBContext.newInstance(Data.class);
            final Unmarshaller unmarshaller = ctx.createUnmarshaller();
            return (Data) unmarshaller.unmarshal(new StringReader(xmlData));
        } catch (final JAXBException ex) {
            throw new RuntimeException("Error de-serializing test data", ex);
        }
    }

}
// CHECKSTYLE:ON
