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
import static org.fuin.units4j.Units4JUtils.assertCauseCauseMessage;
import static org.fuin.utils4j.JaxbUtils.XML_PREFIX;
import static org.fuin.utils4j.JaxbUtils.marshal;
import static org.fuin.utils4j.JaxbUtils.unmarshal;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;

import org.fuin.units4j.WeldJUnit4Runner;
import org.junit.Test;
import org.junit.runner.RunWith;

// CHECKSTYLE:OFF
@RunWith(WeldJUnit4Runner.class)
public class CurrencyAmountConverterTest {

    private static final String XML = XML_PREFIX + "<data ca=\"1234.56 EUR\"/>";

    @Inject
    private ValueObjectConverter<String, CurrencyAmount> testee;

    @Test
    public final void testFactoryInjectable() {
        assertThat(testee).isNotNull();
    }

    @Test
    public final void testtoVO() {
        assertThat(testee.toVO("1234.56 EUR")).isEqualTo(
                new CurrencyAmount(new BigDecimal(1234.56).setScale(2, RoundingMode.HALF_UP),
                        Currency.getInstance("EUR")));
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
    public final void testMarshal() throws JAXBException {

        final Data data = new Data();
        data.currencyAmount = new CurrencyAmount("1234.56", "EUR");
        assertThat(marshal(data, Data.class)).isEqualTo(XML);

    }

    @Test
    public final void testMarshalUnmarshal() throws JAXBException {

        final Data data = unmarshal(XML, Data.class);
        assertThat(data.currencyAmount).isEqualTo(new CurrencyAmount("1234.56", "EUR"));

    }

    @Test
    public final void testUnmarshalError() {

        final String invalidEmailInXmlData = XML_PREFIX + "<data ca=\"1234.56\"/>";
        try {
            unmarshal(invalidEmailInXmlData, Data.class);
            fail("Expected an exception");
        } catch (final RuntimeException ex) {
            assertCauseCauseMessage(ex, "No space character found in '1234.56'");
        }

    }

}
// CHECKSTYLE:ON
