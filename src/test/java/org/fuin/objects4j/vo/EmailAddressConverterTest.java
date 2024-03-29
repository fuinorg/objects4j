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

import jakarta.xml.bind.JAXBException;
import org.assertj.core.api.Assertions;
import org.fuin.utils4j.jaxb.UnmarshallerBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fuin.objects4j.vo.JsonbHelper.fromJson;
import static org.fuin.objects4j.vo.JsonbHelper.toJson;
import static org.fuin.units4j.Units4JUtils.assertCauseCauseCauseMessage;
import static org.fuin.units4j.Units4JUtils.assertCauseCauseMessage;
import static org.fuin.utils4j.jaxb.JaxbUtils.*;

// CHECKSTYLE:OFF
public class EmailAddressConverterTest {

    private static final String XML = XML_PREFIX + "<data email=\"a@b.c\"/>";

    private static final String JSON = "{\"email\":\"a@b.c\"}";

    private ValueObjectConverter<String, EmailAddress> testee;

    @BeforeEach
    public void setup() {
        testee = new EmailAddressConverter();
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
    public final void testtoVO() {
        assertThat(testee.toVO("a@b.c")).isEqualTo(new EmailAddress("a@b.c"));
    }

    @Test
    public final void testIsValid() {
        assertThat(testee.isValid(null)).isTrue();
        assertThat(testee.isValid("a@b.c")).isTrue();
        assertThat(testee.isValid("")).isFalse();
    }

    @Test
    public final void testGetSimpleValueObjectClass() {
        assertThat(testee.getValueObjectClass()).isSameAs(EmailAddress.class);
    }

    @Test
    public final void testMarshal() throws JAXBException {

        final Data data = new Data();
        data.email = new EmailAddress("a@b.c");
        assertThat(marshal(data, Data.class)).isEqualTo(XML);

    }

    @Test
    public final void testMarshalUnmarshal() throws JAXBException {

        final Data data = unmarshal(new UnmarshallerBuilder().addClassesToBeBound(Data.class).withHandler(event -> false).build(), XML);
        assertThat(data.email).isEqualTo(new EmailAddress("a@b.c"));

    }

    @Test
    public final void testUnmarshalError() {

        final String invalidEmailInXmlData = XML_PREFIX + "<data email=\"abc@\"/>";
        try {
            unmarshal(new UnmarshallerBuilder().addClassesToBeBound(Data.class).withHandler(event -> false).build(), invalidEmailInXmlData);
            Assertions.fail("Expected an exception");
        } catch (final RuntimeException ex) {
            assertCauseCauseCauseMessage(ex, "The argument 'emailAddress' is not valid: 'abc@'");
        }

    }

    @Test
    public final void testMarshalJsonb() {

        final Data data = new Data();
        data.email = new EmailAddress("a@b.c");
        assertThat(toJson(data, new EmailAddressConverter())).isEqualTo(JSON);

    }

    @Test
    public final void testMarshalUnmarshalJsonb() {

        final Data data = fromJson(JSON, Data.class, new EmailAddressConverter());
        assertThat(data.email).isEqualTo(new EmailAddress("a@b.c"));

    }

    @Test
    public final void testUnmarshalErrorJsonb() {

        final String invalidJsonData = "{\"email\":\"abc@\"}";
        try {
            fromJson(invalidJsonData, Data.class, new EmailAddressConverter());
            Assertions.fail("Expected an exception");
        } catch (final RuntimeException ex) {
            assertCauseCauseMessage(ex, "The argument 'emailAddress' is not valid: 'abc@'");
        }

    }

}
// CHECKSTYLE:ON
