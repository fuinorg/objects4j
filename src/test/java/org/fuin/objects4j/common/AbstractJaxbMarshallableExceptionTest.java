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

import static org.assertj.core.api.Assertions.assertThat;
import static org.fuin.utils4j.JaxbUtils.marshal;
import static org.fuin.utils4j.JaxbUtils.unmarshal;
import static org.fuin.utils4j.Utils4J.deserialize;
import static org.fuin.utils4j.Utils4J.serialize;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;

/**
 * Tests for {@link AbstractJaxbMarshallableException}.
 */
// CHECKSTYLE:OFF Disabled for test
public class AbstractJaxbMarshallableExceptionTest {

    @Test
    public void testSerializeDeserialize() {

        // PREPARE
        final TestException original = new TestException("My message");

        // TEST
        final byte[] data = serialize(original);
        final TestException copy = deserialize(data);

        // VERIFY
        assertThat(copy.getMessage()).isEqualTo(original.getMessage());

    }

    @Test
    public final void testMarshalUnmarshalXML() throws Exception {

        // PREPARE
        final RuntimeException ex = new RuntimeException("Test");
        final TestException original = new TestException("My message", ex);

        // TEST
        final String xml = marshal(original, TestException.class);

        // VERIFY
        final Diff documentDiff = DiffBuilder.compare("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + "<test-exception>"
                + "<msg>My message</msg>" + "</test-exception>").withTest(xml).ignoreWhitespace().build();

        assertThat(documentDiff.hasDifferences()).describedAs(documentDiff.toString()).isFalse();

        // TEST
        final TestException copy = unmarshal(xml, TestException.class);

        // VERIFY
        assertThat(copy.getMessage()).isEqualTo(original.getMessage());
        assertThat(copy.getStackTrace()).isEmpty();

    }

    @XmlRootElement(name = "test-exception")
    @XmlAccessorType(XmlAccessType.NONE)
    public static class TestException extends AbstractJaxbMarshallableException {

        private static final long serialVersionUID = 1L;

        /**
         * JAX-B constructor.
         */
        protected TestException() {
            super();
        }

        /**
         * Constructor with message and cause.
         * 
         * @param message
         *            Message.
         * @param cause
         *            Cause.
         */
        public TestException(final String message, final Throwable cause) {
            super(message, cause);
        }

        /**
         * Constructor with message.
         * 
         * @param message
         *            Message.
         */
        public TestException(final String message) {
            super(message);
        }

    }

}
// CHECKSTYLE:ON
