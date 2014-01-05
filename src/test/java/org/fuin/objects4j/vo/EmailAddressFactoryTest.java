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
import static org.junit.Assert.fail;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;

import org.fuin.units4j.WeldJUnit4Runner;
import org.junit.Test;
import org.junit.runner.RunWith;

// CHECKSTYLE:OFF
@RunWith(WeldJUnit4Runner.class)
public class EmailAddressFactoryTest extends SimpleValueObjectFactoryTest {

    private static final String XML = XML_PREFIX + "<data email=\"a@b.c\"/>";

    @Inject
    private SimpleValueObjectFactory<String, EmailAddress> testee;

    @Test
    public final void testFactoryInjectable() {
        assertThat(testee).isNotNull();
    }

    @Test
    public final void testCreate() {
        assertThat(testee.create("a@b.c")).isEqualTo(new EmailAddress("a@b.c"));
    }

    @Test
    public final void testIsValid() {
        assertThat(testee.isValid(null)).isTrue();
        assertThat(testee.isValid("a@b.c")).isTrue();
        assertThat(testee.isValid("")).isFalse();
    }

    @Test
    public final void testGetSimpleValueObjectClass() {
        assertThat(testee.getSimpleValueObjectClass()).isSameAs(EmailAddress.class);
    }

    @Test
    public final void testMarshal() throws JAXBException {

        final Data data = new Data();
        data.email = new EmailAddress("a@b.c");
        assertThat(marshal(data)).isEqualTo(XML);

    }

    @Test
    public final void testMarshalUnmarshal() throws JAXBException {

        final Data data = unmarshal(XML);
        assertThat(data.email).isEqualTo(new EmailAddress("a@b.c"));

    }

    @Test
    public final void testUnmarshalError() {

        final String invalidEmailInXmlData = XML_PREFIX + "<data email=\"abc@\"/>";
        try {
            unmarshal(invalidEmailInXmlData);
            fail("Expected an exception");
        } catch (final RuntimeException ex) {
            assertCauseCauseMessage(ex, "The argument 'emailAddress' is not valid: 'abc@'");
        }

    }

}
// CHECKSTYLE:ON
