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
import static org.fuin.utils4j.JaxbUtils.XML_PREFIX;
import static org.fuin.utils4j.JaxbUtils.marshal;
import static org.fuin.utils4j.JaxbUtils.unmarshal;
import static org.junit.Assert.fail;

import jakarta.xml.bind.JAXBException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// CHECKSTYLE:OFF
public class HourRangeConverterTest {

    private static final String XML = XML_PREFIX + "<data hourRange=\"00:00-24:00\"/>";

    private static final String JSON = "{\"hourRange\":\"00:00-24:00\"}";

    private HourRangeConverter testee;

    @Before
    public void setup() {
        testee = new HourRangeConverter();
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
    public final void testMarshal() throws JAXBException {

        final Data data = new Data();
        data.hourRange = new HourRange("00:00-24:00");
        assertThat(marshal(data, Data.class)).isEqualTo(XML);

    }

    @Test
    public final void testMarshalUnmarshal() throws JAXBException {

        final Data data = unmarshal(XML, Data.class);
        assertThat(data.hourRange).isEqualTo(new HourRange("00:00-24:00"));

    }

    @Test
    public final void testUnmarshalError() {

        final String invalidEmailInXmlData = XML_PREFIX + "<data hourRange=\"17-18\"/>";
        try {
            unmarshal(invalidEmailInXmlData, Data.class);
            fail("Expected an exception");
        } catch (final RuntimeException ex) {
            assertCauseCauseMessage(ex, "The argument 'hourRange' does not represent a valid hour range like '00:00-24:00' or '06:00-21:00': '17-18'");
        }

    }

    @Test
    public final void testMarshalJsonb() {

        final Data data = new Data();
        data.hourRange = new HourRange("00:00-24:00");
        assertThat(toJson(data, new HourRangeConverter())).isEqualTo(JSON);

    }

    @Test
    public final void testMarshalUnmarshalJsonb() {

        final Data data = fromJson(JSON, Data.class, new HourRangeConverter());
        assertThat(data.hourRange).isEqualTo(new HourRange("00:00-24:00"));

    }

    @Test
    public final void testUnmarshalErrorJsonb() {

        final String invalidJsonData = "{\"hourRange\":\"17-18\"}";
        try {
            fromJson(invalidJsonData, Data.class, new HourRangeConverter());
            fail("Expected an exception");
        } catch (final RuntimeException ex) {
            assertCauseCauseMessage(ex, "The argument 'hourRange' does not represent a valid hour range like '00:00-24:00' or '06:00-21:00': '17-18'");
        }

    }

}
// CHECKSTYLE:ON
