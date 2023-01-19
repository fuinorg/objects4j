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
import static org.fuin.utils4j.jaxb.JaxbUtils.XML_PREFIX;
import static org.fuin.utils4j.jaxb.JaxbUtils.marshal;
import static org.fuin.utils4j.jaxb.JaxbUtils.unmarshal;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import javax.xml.bind.JAXBException;

import org.fuin.utils4j.jaxb.UnmarshallerBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// CHECKSTYLE:OFF
public class CurrencyAmountConverterTest {

    private static final String XML = XML_PREFIX + "<data ca=\"1234.56 EUR\"/>";

    private static final String JSON = "{\"ca\":\"1234.56 EUR\"}";
    
    private ValueObjectConverter<String, CurrencyAmount> testee;

    @Before
    public void setup() {
        testee = new CurrencyAmountConverter();
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
    public final void testtoVO() {
        assertThat(testee.toVO("1234.56 EUR"))
                .isEqualTo(new CurrencyAmount(new BigDecimal(1234.56).setScale(2, RoundingMode.HALF_UP), Currency.getInstance("EUR")));
    }

    @Test
    public final void testIsValid() {
        assertThat(testee.isValid(null)).isTrue();
        assertThat(testee.isValid("1234.56 EUR")).isTrue();
        assertThat(testee.isValid("0 EUR")).isTrue();
        assertThat(testee.isValid("1234.56")).isFalse();
        assertThat(testee.isValid("EUR")).isFalse();
    }

    @Test
    public final void testGetSimpleValueObjectClass() {
        assertThat(testee.getValueObjectClass()).isSameAs(CurrencyAmount.class);
    }

    @Test
    public final void testMarshalJaxb() throws JAXBException {

        final Data data = new Data();
        data.currencyAmount = new CurrencyAmount("1234.56", "EUR");
        assertThat(marshal(data, Data.class)).isEqualTo(XML);

    }

    @Test
    public final void testMarshalUnmarshalJaxb() throws JAXBException {

        final Data data = unmarshal(XML, Data.class);
        assertThat(data.currencyAmount).isEqualTo(new CurrencyAmount("1234.56", "EUR"));

    }

    @Test
    public final void testUnmarshalErrorJaxb() {

        final String invalidXmlData = XML_PREFIX + "<data ca=\"1234.56\"/>";
        try {
            unmarshal(new UnmarshallerBuilder().addClassesToBeBound(Data.class)
                    .withHandler(event -> false)
                    .build(), invalidXmlData);
            fail("Expected an exception");
        } catch (final RuntimeException ex) {
            assertCauseCauseMessage(ex, "No space character found in '1234.56'");
        }

    }
    
    @Test
    public final void testMarshalJsonb() {

        final Data data = new Data();
        data.currencyAmount = new CurrencyAmount("1234.56", "EUR");
        assertThat(toJson(data, new CurrencyAmountConverter())).isEqualTo(JSON);

    }

    @Test
    public final void testMarshalUnmarshalJsonb() {

        final Data data = fromJson(JSON, Data.class, new CurrencyAmountConverter());
        assertThat(data.currencyAmount).isEqualTo(new CurrencyAmount("1234.56", "EUR"));

    }

    @Test
    public final void testUnmarshalErrorJsonb() {

        final String invalidJsonData = "{\"ca\":\"1234.56\"}";
        try {
            fromJson(invalidJsonData, Data.class, new CurrencyAmountConverter());
            fail("Expected an exception");
        } catch (final RuntimeException ex) {
            assertCauseCauseMessage(ex, "No space character found in '1234.56'");
        }

    }

}
// CHECKSTYLE:ON
