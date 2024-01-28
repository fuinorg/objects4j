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
package org.fuin.objects4j.jsonb;

import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.core.HourRange;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fuin.objects4j.jsonb.JsonbHelper.fromJson;
import static org.fuin.objects4j.jsonb.JsonbHelper.toJson;
import static org.fuin.utils4j.jaxb.JaxbUtils.XML_PREFIX;

/**
 * Test for the {@link HourRangeConverter} class.
 */
class HourRangeConverterTest {

    private static final String XML = XML_PREFIX + "<data hourRange=\"00:00-24:00\"/>";

    private static final String JSON = "{\"hourRange\":\"00:00-24:00\"}";

    @Test
    void testMarshalJsonb() {

        final Data data = new Data();
        data.hourRange = new HourRange("00:00-24:00");
        assertThat(toJson(data, new HourRangeConverter())).isEqualTo(JSON);

    }

    @Test
    void testMarshalUnmarshalJsonb() {

        final Data data = fromJson(JSON, Data.class, new HourRangeConverter());
        assertThat(data.hourRange).isEqualTo(new HourRange("00:00-24:00"));

    }

    @Test
    void testUnmarshalErrorJsonb() {

        final String invalidJsonData = "{\"hourRange\":\"17-18\"}";
        assertThatThrownBy(() -> fromJson(invalidJsonData, Data.class, new HourRangeConverter()))
                .hasRootCauseInstanceOf(ConstraintViolationException.class)
                .hasRootCauseMessage("The argument 'hourRange' does not represent a valid hour range like '00:00-24:00' or '06:00-21:00': '17-18'");

    }

}

