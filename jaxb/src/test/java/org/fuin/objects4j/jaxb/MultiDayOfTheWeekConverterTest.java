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
import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.core.MultiDayOfTheWeek;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.fuin.utils4j.jaxb.JaxbUtils.XML_PREFIX;

public class MultiDayOfTheWeekConverterTest {

    private static final String XML = XML_PREFIX + "<data multiDayOfTheWeek=\"MON/TUE\"/>";

    @Test
    public final void testMarshal() throws JAXBException {

        final Data data = new Data();
        data.multiDayOfTheWeek = new MultiDayOfTheWeek("Mon/Tue");
        assertThat(JaxbHelper.marshalData(data)).isEqualTo(XML);

    }

    @Test
    public final void testMarshalUnmarshal() throws JAXBException {

        final Data data = JaxbHelper.unmarshalData(XML);
        assertThat(data.multiDayOfTheWeek).isEqualTo(new MultiDayOfTheWeek("Mon/Tue"));

    }

    @Test
    public final void testUnmarshalError() {

        final String invalidXmlData = XML_PREFIX + "<data multiDayOfTheWeek=\"Mon+Tue\"/>";
        assertThatThrownBy(() -> JaxbHelper.unmarshalData(invalidXmlData))
                .hasRootCauseInstanceOf(ConstraintViolationException.class)
                .hasRootCauseMessage("The argument 'multipleDayOfTheWeek' does not represent valid days of the week like 'Mon/Tue/Wed-Fri': 'Mon+Tue'");

    }

}
