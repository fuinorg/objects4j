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

import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fuin.objects4j.vo.JsonbHelper.fromJson;
import static org.fuin.objects4j.vo.JsonbHelper.toJson;
import static org.fuin.utils4j.jaxb.JaxbUtils.*;

// CHECKSTYLE:OFF
public class CurrencyConverterTest {

    private static final String XML = XML_PREFIX + "<data c=\"EUR\"/>";

    private static final String JSON = "{\"c\":\"EUR\"}";

    private CurrencyConverter testee;

    @BeforeEach
    public void setup() {
        testee = new CurrencyConverter();
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
        assertThat(testee.toVO("EUR")).isEqualTo(Currency.getInstance("EUR"));
    }

    @Test
    public final void testFromVO() {
        assertThat(testee.fromVO(Currency.getInstance("EUR"))).isEqualTo("EUR");
    }

    @Test
    public final void testIsValid() {
        assertThat(testee.isValid(null)).isTrue();
        assertThat(testee.isValid("EUR")).isTrue();
        assertThat(testee.isValid("")).isFalse();
        assertThat(testee.isValid("ABC")).isFalse();
    }

    @Test
    public final void testMarshalJaxb() throws JAXBException {

        final Data data = new Data();
        data.currency = Currency.getInstance("EUR");
        assertThat(marshal(data, Data.class)).isEqualTo(XML);

    }

    @Test
    public final void testMarshalUnmarshalJaxb() throws JAXBException {

        final Data data = unmarshal(new UnmarshallerBuilder().addClassesToBeBound(Data.class).withHandler(event -> false).build(), XML);
        assertThat(data.currency).isEqualTo(Currency.getInstance("EUR"));

    }

    @Test
    public final void testUnmarshalErrorJaxb() {

        final String invalidXmlData = XML_PREFIX + "<data c=\"ABCD\"/>";
        try {
            unmarshal(new UnmarshallerBuilder().addClassesToBeBound(Data.class).withHandler(event -> false).build(), invalidXmlData);
            Assertions.fail("Expected an exception");
        } catch (final RuntimeException ex) {
            assertThat(ex.getMessage()).contains("Error unmarshalling");
        }

    }

    @Test
    public final void testMarshalJsonb() {

        final Data data = new Data();
        data.currency = Currency.getInstance("EUR");
        assertThat(toJson(data, new CurrencyConverter())).isEqualTo(JSON);

    }

    @Test
    public final void testMarshalUnmarshalJsonb() {

        final Data data = fromJson(JSON, Data.class, new CurrencyConverter());
        assertThat(data.currency).isEqualTo(Currency.getInstance("EUR"));

    }

    @Test
    public final void testUnmarshalErrorJsonb() {

        final String invalidJsonData = "{\"c\":\"ABCD\"}";
        try {
            fromJson(invalidJsonData, Data.class, new CurrencyConverter());
            Assertions.fail("Expected an exception");
        } catch (final RuntimeException ex) {
            assertThat(ex.getMessage()).contains("Unable to deserialize property 'c'");
        }

    }

}
// CHECKSTYLE:ON
