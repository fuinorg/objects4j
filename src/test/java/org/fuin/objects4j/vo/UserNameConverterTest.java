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

import jakarta.validation.ConstraintViolation;
import jakarta.xml.bind.JAXBException;
import org.assertj.core.api.Assertions;
import org.fuin.utils4j.jaxb.UnmarshallerBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fuin.objects4j.vo.JsonbHelper.fromJson;
import static org.fuin.objects4j.vo.JsonbHelper.toJson;
import static org.fuin.units4j.Units4JUtils.*;
import static org.fuin.utils4j.jaxb.JaxbUtils.XML_PREFIX;
import static org.fuin.utils4j.jaxb.JaxbUtils.marshal;
import static org.fuin.utils4j.jaxb.JaxbUtils.unmarshal;

//CHECKSTYLE:OFF
public class UserNameConverterTest {

    private static final String USER_NAME = "michael-1_a";

    private static final String XML = XML_PREFIX + "<data userName=\"" + USER_NAME + "\"/>";

    private static final String JSON = "{\"userName\":\"" + USER_NAME + "\"}";

    private ValueObjectConverter<String, UserName> testee;

    @BeforeEach
    public void setup() {
        testee = new UserNameConverter();
    }

    @AfterEach
    public void teardown() {
        testee = null;
    }

    @Test
    public final void testFactoryInjectable() {
        assertThat(testee).isNotNull();
    }

    @Test
    public final void testToVO() {
        assertThat(testee.toVO(USER_NAME)).isEqualTo(new UserName(USER_NAME));
    }

    @Test
    public final void testIsValid() {
        assertThat(testee.isValid(null)).isTrue();
        assertThat(testee.isValid(USER_NAME)).isTrue();
        assertThat(testee.isValid("")).isFalse();
        assertThat(testee.isValid("a12345678901234567890")).isFalse();
    }

    @Test
    public final void testGetSimpleValueObjectClass() {
        assertThat(testee.getValueObjectClass()).isSameAs(UserName.class);
    }

    @Test
    public final void testMarshal() throws JAXBException {

        final Data data = new Data();
        data.userName = new UserName(USER_NAME);
        assertThat(marshal(data, Data.class)).isEqualTo(XML);

    }

    @Test
    public final void testMarshalUnmarshal() throws JAXBException {

        final Data data = unmarshal(new UnmarshallerBuilder().addClassesToBeBound(Data.class).withHandler(event -> false).build(), XML);
        assertThat(data.userName).isEqualTo(new UserName(USER_NAME));

    }

    @Test
    public final void testUnmarshalError() {

        final String invalidUsernameInXmlData = XML_PREFIX + "<data userName=\"x\"/>";
        try {
            unmarshal(new UnmarshallerBuilder().addClassesToBeBound(Data.class).withHandler(event -> false).build(), invalidUsernameInXmlData);
            Assertions.fail("Expected an exception");
        } catch (final RuntimeException ex) {
            assertCauseCauseCauseMessage(ex, "The argument 'userName' is not valid: 'x'");
        }

    }

    @Test
    public final void testValidation() {

        final Data data = new Data();
        data.userName = new UserName(USER_NAME);
        // Set intentionally an illegal value
        // This may happen when deserialized
        setPrivateField(data.userName, "str", "x");
        final Set<ConstraintViolation<Object>> violations = validate(data);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("is not a well-formed user name");

    }

    @Test
    public final void testMarshalJsonb() {

        final Data data = new Data();
        data.userName = new UserName(USER_NAME);
        assertThat(toJson(data, new UserNameConverter())).isEqualTo(JSON);

    }

    @Test
    public final void testMarshalUnmarshalJsonb() {

        final Data data = fromJson(JSON, Data.class, new UserNameConverter());
        assertThat(data.userName).isEqualTo(new UserName(USER_NAME));

    }

    @Test
    public final void testUnmarshalErrorJsonb() {

        final String invalidJsonData = "{\"userName\":\"x\"}";
        try {
            fromJson(invalidJsonData, Data.class, new UserNameConverter());
            Assertions.fail("Expected an exception");
        } catch (final RuntimeException ex) {
            assertCauseCauseMessage(ex, "The argument 'userName' is not valid: 'x'");
        }

    }

}
// CHECKSTYLE:OFF
