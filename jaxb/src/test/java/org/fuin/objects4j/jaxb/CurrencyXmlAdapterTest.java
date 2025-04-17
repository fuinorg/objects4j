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
package org.fuin.objects4j.jaxb;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Test;

import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fuin.utils4j.jaxb.JaxbUtils.XML_PREFIX;

public class CurrencyXmlAdapterTest {

    private static final String XML = XML_PREFIX + "<data c=\"EUR\"/>";

    @Test
    public final void testMarshalJaxb() throws JAXBException {

        final Data data = new Data();
        data.currency = Currency.getInstance("EUR");
        assertThat(JaxbHelper.marshalData(data)).isEqualTo(XML);

    }

    @Test
    public final void testMarshalUnmarshalJaxb() throws JAXBException {

        final Data data = JaxbHelper.unmarshalData(XML);
        assertThat(data.currency).isEqualTo(Currency.getInstance("EUR"));

    }

    @Test
    public final void testUnmarshalErrorJaxb() {

        final String invalidXmlData = XML_PREFIX + "<data c=\"ABCD\"/>";
        assertThatThrownBy(() -> JaxbHelper.unmarshalData(invalidXmlData))
                .hasRootCauseInstanceOf(IllegalArgumentException.class)
                .hasRootCauseMessage(null);

    }

}
