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

import static org.assertj.core.api.Assertions.assertThat;
import static org.fuin.objects4j.vo.JsonbHelper.fromJson;
import static org.fuin.objects4j.vo.JsonbHelper.toJson;
import static org.fuin.units4j.Units4JUtils.assertCauseCauseMessage;
import static org.fuin.units4j.Units4JUtils.setPrivateField;
import static org.fuin.units4j.Units4JUtils.validate;
import static org.fuin.utils4j.JaxbUtils.XML_PREFIX;
import static org.fuin.utils4j.JaxbUtils.marshal;
import static org.fuin.utils4j.JaxbUtils.unmarshal;
import static org.junit.Assert.fail;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// CHECKSTYLE:OFF
public class PasswordConverterTest {

    private static final String XML = XML_PREFIX + "<data password=\"abcd1234\"/>";

    private static final String JSON = "{\"password\":\"abcd1234\"}";

    private ValueObjectConverter<String, Password> testee;

    @Before
    public void setup() {
        testee = new PasswordConverter();
    }

    @After
    public void teardown() {
        testee = null;
    }

    @Test
    public final void testFactoryInjectable() {
        assertThat(testee).isNotNull();
    }

    @Test
    public final void testToVO() {
        assertThat(testee.toVO("abcd1234")).isEqualTo(new Password("abcd1234"));
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
        assertThat(testee.getValueObjectClass()).isSameAs(Password.class);
    }

    @Test
    public final void testMarshal() throws JAXBException {

        final Data data = new Data();
        data.password = new Password("abcd1234");
        assertThat(marshal(data, Data.class)).isEqualTo(XML);

    }

    @Test
    public final void testMarshalUnmarshal() throws JAXBException {

        final Data data = unmarshal(XML, Data.class);
        assertThat(data.password).isNotNull();
        assertThat(data.password).isEqualTo(new Password("abcd1234"));

    }

    @Test
    public final void testUnmarshalError() {

        final String invalidPasswordInXmlData = XML_PREFIX + "<data password=\"abcd123\"/>";
        try {
            unmarshal(invalidPasswordInXmlData, Data.class);
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

    @Test
    public final void testMarshalJsonb() {

        final Data data = new Data();
        data.password = new Password("abcd1234");
        assertThat(toJson(data, new PasswordConverter())).isEqualTo(JSON);

    }

    @Test
    public final void testMarshalUnmarshalJsonb() {

        final Data data = fromJson(JSON, Data.class, new PasswordConverter());
        assertThat(data.password).isEqualTo(new Password("abcd1234"));

    }

    @Test
    public final void testUnmarshalErrorJsonb() {

        final String invalidJsonData = "{\"password\":\"abcd123\"}";
        try {
            fromJson(invalidJsonData, Data.class, new PasswordConverter());
            fail("Expected an exception");
        } catch (final RuntimeException ex) {
            assertCauseCauseMessage(ex, "The argument 'password' is not valid: 'abcd123'");
        }

    }

}
// CHECKSTYLE:OFF
