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
import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.core.DayOpeningHours;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fuin.utils4j.jaxb.JaxbUtils.XML_PREFIX;

public class DayOpeningHoursXmlAdapterTest {

    private static final String XML = XML_PREFIX + "<data dayOpeningHours=\"MON 00:00-24:00\"/>";

    @Test
    public final void testMarshal() throws JAXBException {

        final Data data = new Data();
        data.dayOpeningHours = new DayOpeningHours("MON 00:00-24:00");
        assertThat(JaxbHelper.marshalData(data)).isEqualTo(XML);

    }

    @Test
    public final void testMarshalUnmarshal() throws JAXBException {

        final Data data = JaxbHelper.unmarshalData(XML);
        assertThat(data.dayOpeningHours).isEqualTo(new DayOpeningHours("MON 00:00-24:00"));

    }

    @Test
    public final void testUnmarshalError() {

        final String invalidXmlData = XML_PREFIX + "<data dayOpeningHours=\"17-18\"/>";
        assertThatThrownBy(() -> JaxbHelper.unmarshalData(invalidXmlData))
                .hasRootCauseInstanceOf(ConstraintViolationException.class)
                .hasRootCauseMessage("The argument 'dayOpeningHours' does not represent a valid hour range like 'Mon 09:00-12:00+13:00-17:00': '17-18'");

    }

}
