/**
 * Copyright (C) 2013 Future Invent Informationsmanagement GmbH. All rights
 * reserved. <http://www.fuin.org/>
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fuin.objects4j.jaxb;

import jakarta.xml.bind.JAXBException;
import org.fuin.objects4j.core.CurrencyAmount;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fuin.utils4j.jaxb.JaxbUtils.XML_PREFIX;

public class CurrencyAmountXmlAdapterTest {

    private static final String XML = XML_PREFIX + "<data ca=\"1234.56 EUR\"/>";

    @Test
    public final void testMarshalJaxb() throws JAXBException {

        final Data data = new Data();
        data.currencyAmount = new CurrencyAmount("1234.56", "EUR");
        assertThat(JaxbHelper.marshalData(data)).isEqualTo(XML);

    }

    @Test
    public final void testMarshalUnmarshalJaxb() throws JAXBException {

        final Data data = JaxbHelper.unmarshalData(XML);
        assertThat(data.currencyAmount).isEqualTo(new CurrencyAmount("1234.56", "EUR"));

    }

    @Test
    public final void testUnmarshalErrorJaxb() {

        final String invalidXmlData = XML_PREFIX + "<data ca=\"1234.56\"/>";
        assertThatThrownBy(() -> JaxbHelper.unmarshalData(invalidXmlData))
                .hasRootCauseInstanceOf(IllegalArgumentException.class)
                .hasRootCauseMessage("No space character found in '1234.56'");

    }

}
