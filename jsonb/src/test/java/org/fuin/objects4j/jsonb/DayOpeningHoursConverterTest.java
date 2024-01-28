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
import org.fuin.objects4j.core.DayOpeningHours;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fuin.objects4j.jsonb.JsonbHelper.fromJson;
import static org.fuin.objects4j.jsonb.JsonbHelper.toJson;

/**
 * Test for the {@link DayOpeningHoursConverter} class.
 */
class DayOpeningHoursConverterTest {

    private static final String JSON = "{\"dayOpeningHours\":\"MON 00:00-24:00\"}";

    @Test
    void testMarshalJsonb() {

        final Data data = new Data();
        data.dayOpeningHours = new DayOpeningHours("MON 00:00-24:00");
        assertThat(toJson(data, new DayOpeningHoursConverter())).isEqualTo(JSON);

    }

    @Test
    void testMarshalUnmarshalJsonb() {

        final Data data = fromJson(JSON, Data.class, new DayOpeningHoursConverter());
        assertThat(data.dayOpeningHours).isEqualTo(new DayOpeningHours("MON 00:00-24:00"));

    }

    @Test
    void testUnmarshalErrorJsonb() {

        final String invalidJsonData = "{\"dayOpeningHours\":\"17-18\"}";
        assertThatThrownBy(() -> fromJson(invalidJsonData, Data.class, new DayOpeningHoursConverter()))
                .hasRootCauseInstanceOf(ConstraintViolationException.class)
                .hasRootCauseMessage("The argument 'dayOpeningHours' does not represent a valid hour range like 'Mon 09:00-12:00+13:00-17:00': '17-18'");

    }

}

