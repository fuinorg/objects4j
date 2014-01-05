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

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBException;

import org.fuin.units4j.WeldJUnit4Runner;
import org.junit.Test;
import org.junit.runner.RunWith;

// CHECKSTYLE:OFF
@RunWith(WeldJUnit4Runner.class)
public class PasswordFactoryTest extends SimpleValueObjectFactoryTest {

    private static final String XML = XML_PREFIX + "<data password=\"abcd1234\"/>";

    @Inject
    private SimpleValueObjectFactory<String, Password> testee;

    @Test
    public final void testFactoryInjectable() {
        assertThat(testee).isNotNull();
    }

    @Test
    public final void testCreate() {
        assertThat(testee.create("abcd1234")).isEqualTo(new Password("abcd1234"));
    }

    @Test
    public final void testIsValid() {
        assertThat(testee.isValid(null)).isTrue();
        assertThat(testee.isValid("abcd1234")).isTrue();
        assertThat(testee.isValid("123456789.123456789.")).isTrue();
        assertThat(testee.isValid("abcd123")).isFalse();
        assertThat(testee.isValid("")).isFalse();
        assertThat(testee.isValid("123456789.123456789.1")).isFalse();
    }

    @Test
    public final void testGetSimpleValueObjectClass() {
        assertThat(testee.getSimpleValueObjectClass()).isSameAs(Password.class);
    }

    @Test
    public final void testMarshal() throws JAXBException {

        final Data data = new Data();
        data.password = new Password("abcd1234");
        assertThat(marshal(data)).isEqualTo(XML);

    }

    @Test
    public final void testMarshalUnmarshal() throws JAXBException {

        final Data data = unmarshal(XML);
        assertThat(data.password).isNotNull();
        assertThat(data.password).isEqualTo(new Password("abcd1234"));

    }

    @Test
    public final void testUnmarshalError() {

        final String invalidPasswordInXmlData = XML_PREFIX + "<data password=\"abcd123\"/>";
        try {
            unmarshal(invalidPasswordInXmlData);
            fail("Expected an exception");
        } catch (final RuntimeException ex) {
            assertCauseCauseMessage(ex, "The argument 'password' is not valid: 'abcd123'");
        }

    }

    @Test
    public final void testValidation() {

        final Data data = new Data();
        data.password = new Password("abcd1234");
        // Set intentionally an illegal value
        // This may happen when deserialized
        setPrivateField(data.password, "str", "abc123");
        final Set<ConstraintViolation<Object>> violations = validate(data);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("is not a valid password");

    }

}
// CHECKSTYLE:OFF
