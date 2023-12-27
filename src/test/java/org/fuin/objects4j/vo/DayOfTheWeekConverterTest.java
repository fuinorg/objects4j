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
public class DayOfTheWeekConverterTest {

    private static final String XML = XML_PREFIX + "<data dayOfTheWeek=\"FRI\"/>";

    private static final String JSON = "{\"dayOfTheWeek\":\"FRI\"}";

    private DayOfTheWeekConverter testee;

    @BeforeEach
    public void setup() {
        testee = new DayOfTheWeekConverter();
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
    public final void testMarshal() throws JAXBException {

        final Data data = new Data();
        data.dayOfTheWeek = DayOfTheWeek.FRI;
        assertThat(marshal(data, Data.class)).isEqualTo(XML);

    }

    @Test
    public final void testMarshalUnmarshal() throws JAXBException {

        final Data data = unmarshal(new UnmarshallerBuilder().addClassesToBeBound(Data.class).withHandler(event -> false).build(), XML);
        assertThat(data.dayOfTheWeek).isEqualTo(DayOfTheWeek.FRI);

    }

    @Test
    public final void testUnmarshalError() {

        final String invalidEmailInXmlData = XML_PREFIX + "<data dayOfTheWeek=\"Monday\"/>";
        try {
            unmarshal(new UnmarshallerBuilder().addClassesToBeBound(Data.class).withHandler(event -> false).build(), invalidEmailInXmlData);
            Assertions.fail("Expected an exception");
        } catch (final RuntimeException ex) {
            assertCauseCauseCauseMessage(ex, "Unknown day of week: 'Monday'");
        }

    }

    @Test
    public final void testMarshalJsonb() {

        final Data data = new Data();
        data.dayOfTheWeek = DayOfTheWeek.FRI;
        assertThat(toJson(data, new DayOfTheWeekConverter())).isEqualTo(JSON);

    }

    @Test
    public final void testMarshalUnmarshalJsonb() {

        final Data data = fromJson(JSON, Data.class, new DayOfTheWeekConverter());
        assertThat(data.dayOfTheWeek).isEqualTo(DayOfTheWeek.FRI);

    }

    @Test
    public final void testUnmarshalErrorJsonb() {

        final String invalidJsonData = "{\"dayOfTheWeek\":\"Monday\"}";
        try {
            fromJson(invalidJsonData, Data.class, new DayOfTheWeekConverter());
            Assertions.fail("Expected an exception");
        } catch (final RuntimeException ex) {
            assertCauseCauseMessage(ex, "Unknown day of week: 'Monday'");
        }

    }

}
// CHECKSTYLE:ON
