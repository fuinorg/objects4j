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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.fuin.objects4j.vo.JsonbHelper.fromJson;
import static org.fuin.objects4j.vo.JsonbHelper.toJson;
import static org.fuin.units4j.Units4JUtils.assertCauseCauseCauseMessage;
import static org.fuin.units4j.Units4JUtils.assertCauseCauseMessage;
import static org.fuin.utils4j.jaxb.JaxbUtils.*;

// CHECKSTYLE:OFF
public class HourRangesConverterTest {

    private static final String XML = XML_PREFIX + "<data hourRanges=\"09:00-12:00+13:00-17:00\"/>";

    private static final String JSON = "{\"hourRanges\":\"09:00-12:00+13:00-17:00\"}";

    private HourRangesConverter testee;

    @BeforeEach
    public void setup() {
        testee = new HourRangesConverter();
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
        data.hourRanges = new HourRanges("09:00-12:00+13:00-17:00");
        assertThat(marshal(data, Data.class)).isEqualTo(XML);

    }

    @Test
    public final void testMarshalUnmarshal() throws JAXBException {

        final Data data = unmarshal(new UnmarshallerBuilder().addClassesToBeBound(Data.class).withHandler(event -> false).build(), XML);
        assertThat(data.hourRanges).isEqualTo(new HourRanges("09:00-12:00+13:00-17:00"));

    }

    @Test
    public final void testUnmarshalError() {

        final String invalidEmailInXmlData = XML_PREFIX + "<data hourRanges=\"17-18+19-20\"/>";
        try {
            unmarshal(new UnmarshallerBuilder().addClassesToBeBound(Data.class).withHandler(event -> false).build(), invalidEmailInXmlData);
            Assertions.fail("Expected an exception");
        } catch (final RuntimeException ex) {
            assertCauseCauseCauseMessage(ex,
                    "The argument 'ranges' does not represent a valid hour range like '09:00-12:00+13:00-17:00': '17-18+19-20'");
        }

    }

    @Test
    public final void testMarshalJsonb() {

        final Data data = new Data();
        data.hourRanges = new HourRanges("09:00-12:00+13:00-17:00");
        assertThat(toJson(data, new HourRangesConverter())).isEqualTo(JSON);

    }

    @Test
    public final void testMarshalUnmarshalJsonb() {

        final Data data = fromJson(JSON, Data.class, new HourRangesConverter());
        assertThat(data.hourRanges).isEqualTo(new HourRanges("09:00-12:00+13:00-17:00"));

    }

    @Test
    public final void testUnmarshalErrorJsonb() {

        final String invalidJsonData = "{\"hourRanges\":\"17-18+19-20\"}";
        try {
            fromJson(invalidJsonData, Data.class, new HourRangesConverter());
            Assertions.fail("Expected an exception");
        } catch (final RuntimeException ex) {
            assertCauseCauseMessage(ex,
                    "The argument 'ranges' does not represent a valid hour range like '09:00-12:00+13:00-17:00': '17-18+19-20'");
        }

    }

}
// CHECKSTYLE:ON
