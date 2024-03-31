/**
 * Copyright (C) 2013 Future Invent Informationsmanagement GmbH. All rights
 * reserved. <http://www.fuin.org/>
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fuin.objects4j.jaxb;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.fuin.utils4j.TestOmitted;
import org.fuin.utils4j.jaxb.MarshallerBuilder;
import org.fuin.utils4j.jaxb.UnmarshallerBuilder;

import java.io.StringWriter;
import java.util.List;

import static org.fuin.utils4j.jaxb.JaxbUtils.unmarshal;

/**
 * Utility class for this package.
 */
@TestOmitted("Only a test class")
class JaxbHelper {

    private JaxbHelper() {
        throw new UnsupportedOperationException("Instances of utility classes are not allowed");
    }

    public static Data unmarshalData(String xml) {
        final List<XmlAdapter<?, ?>> adapters = JaxbUtils.getJaxbAdapters();
        adapters.add(new AnyStrXmlAdapter());
        final Unmarshaller unmarshaller = new UnmarshallerBuilder()
                .addClassesToBeBound(Data.class)
                .withHandler(event -> false)
                .addAdapters(adapters)
                .build();
        return unmarshal(unmarshaller, xml);
    }

    public static String marshalData(Data data) {
        final List<XmlAdapter<?, ?>> adapters = JaxbUtils.getJaxbAdapters();
        adapters.add(new AnyStrXmlAdapter());
        final Marshaller marshaller = new MarshallerBuilder()
                .addClassesToBeBound(Data.class)
                .withHandler(event -> false)
                .addAdapters(adapters)
                .build();
        try {
            final StringWriter writer = new StringWriter();
            marshaller.marshal(data, writer);
            return writer.toString();
        } catch (final JAXBException ex) {
            throw new RuntimeException(ex);
        }
    }

}