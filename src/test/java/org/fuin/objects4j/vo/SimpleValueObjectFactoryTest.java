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

import static org.fest.assertions.Assertions.assertThat;

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

/**
 * Base class for testing concrete {@link ValueObjectConverter}
 * implementations.
 */
// CHECKSTYLE:OFF
public abstract class SimpleValueObjectFactoryTest {

    protected static final String XML_PREFIX = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";

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
            unmarshaller.setEventHandler(new ValidationEventHandler() {
                @Override
                public boolean handleEvent(final ValidationEvent event) {
                    if (event.getSeverity() > 0) {
                        if (event.getLinkedException() == null) {
                            throw new RuntimeException("Error unmarshalling the data: "
                                    + event.getMessage());
                        }
                        throw new RuntimeException("Error unmarshalling the data", event
                                .getLinkedException());
                    }
                    return true;
                }
            });
            return (Data) unmarshaller.unmarshal(new StringReader(xmlData));
        } catch (final JAXBException ex) {
            throw new RuntimeException("Error de-serializing test data", ex);
        }
    }

    /**
     * Sets a private field in an object by using reflection.
     * 
     * @param obj
     *            Object with the attribute to set.
     * @param name
     *            Name of the attribute to set.
     * @param value
     *            Value to set for the attribute.
     */
    protected final void setPrivateField(final Object obj, final String name, final Object value) {
        try {
            final Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (final Exception ex) {
            throw new RuntimeException("Couldn't set field '" + name + "' in class '"
                    + obj.getClass() + "'", ex);
        }
    }

    /**
     * Validates the given object.
     * 
     * @param obj
     *            Object to validate.
     * 
     * @return Constraint violations.
     */
    protected final Set<ConstraintViolation<Object>> validate(final Object obj) {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        return validator.validate(obj, Default.class);
    }

    protected final void assertCauseCauseMessage(final Throwable ex, final String expectedMessage) {
        assertThat(ex.getCause()).isNotNull();
        assertThat(ex.getCause().getCause().getMessage()).isEqualTo(expectedMessage);
    }

}
// CHECKSTYLE:ON
