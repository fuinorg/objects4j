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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.fuin.objects4j.vo.JsonbHelper.fromJson;
import static org.fuin.objects4j.vo.JsonbHelper.toJson;
import static org.fuin.units4j.Units4JUtils.assertCauseCauseMessage;
import static org.fuin.utils4j.jaxb.JaxbUtils.XML_PREFIX;
import static org.fuin.utils4j.jaxb.JaxbUtils.marshal;
import static org.fuin.utils4j.jaxb.JaxbUtils.unmarshal;
import static org.junit.Assert.fail;

import javax.xml.bind.JAXBException;

import org.fuin.utils4j.jaxb.UnmarshallerBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// CHECKSTYLE:OFF
public class MultiDayOfTheWeekConverterTest {

    private static final String XML = XML_PREFIX + "<data multiDayOfTheWeek=\"MON/TUE\"/>";

    private static final String JSON = "{\"multiDayOfTheWeek\":\"MON/TUE\"}";

    private MultiDayOfTheWeekConverter testee;

    @Before
    public void setup() {
        testee = new MultiDayOfTheWeekConverter();
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
        data.multiDayOfTheWeek = new MultiDayOfTheWeek("Mon/Tue");
        assertThat(marshal(data, Data.class)).isEqualTo(XML);

    }

    @Test
    public final void testMarshalUnmarshal() throws JAXBException {

        final Data data = unmarshal(XML, Data.class);
        assertThat(data.multiDayOfTheWeek).isEqualTo(new MultiDayOfTheWeek("Mon/Tue"));

    }

    @Test
    public final void testUnmarshalError() {

        final String invalidEmailInXmlData = XML_PREFIX + "<data multiDayOfTheWeek=\"Mon+Tue\"/>";
        try {
            unmarshal(new UnmarshallerBuilder().addClassesToBeBound(Data.class)
                    .withHandler(event -> false)
                    .build(), invalidEmailInXmlData);
            fail("Expected an exception");
        } catch (final RuntimeException ex) {
            assertCauseCauseMessage(ex, "The argument 'multipleDayOfTheWeek' does not represent valid days of the week like 'Mon/Tue/Wed-Fri': 'Mon+Tue'");
        }

    }

    @Test
    public final void testMarshalJsonb() {

        final Data data = new Data();
        data.multiDayOfTheWeek = new MultiDayOfTheWeek("Mon/Tue");
        assertThat(toJson(data, new MultiDayOfTheWeekConverter())).isEqualTo(JSON);

    }

    @Test
    public final void testMarshalUnmarshalJsonb() {

        final Data data = fromJson(JSON, Data.class, new MultiDayOfTheWeekConverter());
        assertThat(data.multiDayOfTheWeek).isEqualTo(new MultiDayOfTheWeek("Mon/Tue"));

    }

    @Test
    public final void testUnmarshalErrorJsonb() {

        final String invalidJsonData = "{\"multiDayOfTheWeek\":\"Mon+Tue\"}";
        try {
            fromJson(invalidJsonData, Data.class, new MultiDayOfTheWeekConverter());
            fail("Expected an exception");
        } catch (final RuntimeException ex) {
            assertCauseCauseMessage(ex, "The argument 'multipleDayOfTheWeek' does not represent valid days of the week like 'Mon/Tue/Wed-Fri': 'Mon+Tue'");
        }

    }

}
// CHECKSTYLE:ON
