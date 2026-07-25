package org.fuin.objects4j.jaxb;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fuin.utils4j.jaxb.JaxbUtils.XML_PREFIX;

/**
 * Verifies the generic value object adapter as an XML <em>element</em> and as an <em>attribute</em>, which
 * is how it actually occurs - the isolated adapter test says nothing about the surrounding document.
 * {@link Data} is the container every test in this package shares, so the two types are covered by its
 * other tests as well.
 */
class ValueObjectXmlInsideObjectTest {

    private static final String XML = XML_PREFIX + """
            <data anyLong="123456789012345678">\
            <anyDecimal>19.00</anyDecimal>\
            </data>""";

    @Test
    void testMarshalAsElementAndAttribute() throws JAXBException {
        final Data data = new Data();
        data.anyDecimal = new AnyDecimal(new BigDecimal("19.00"));
        data.anyLong = new AnyLong(123456789012345678L);

        assertThat(JaxbHelper.marshalData(data)).isEqualTo(XML);
    }

    @Test
    void testUnmarshalFromElementAndAttribute() throws JAXBException {
        final Data data = JaxbHelper.unmarshalData(XML);
        assertThat(data.anyDecimal.asBaseType()).isEqualByComparingTo(new BigDecimal("19.00"));
        assertThat(data.anyLong.asBaseType()).isEqualTo(123456789012345678L);
    }

}
