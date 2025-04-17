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
import org.fuin.objects4j.core.WeeklyOpeningHours;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.fuin.objects4j.jsonb.JsonbHelper.fromJson;
import static org.fuin.objects4j.jsonb.JsonbHelper.toJson;

/**
 * Test for the {@link WeeklyOpeningHoursJsonbAdapter} class.
 */
public class WeeklyOpeningHoursJsonbAdapterTest {

    private static final String HOURS = "MON-FRI 06:00-18:00,SAT/SUN 06:00-12:00";

    private static final String JSON = "{\"weeklyOpeningHours\":\"" + HOURS + "\"}";

    @Test
    void testMarshalJsonb() {

        final Data data = new Data();
        data.weeklyOpeningHours = new WeeklyOpeningHours(HOURS);
        assertThat(toJson(data)).isEqualTo(JSON);

    }

    @Test
    void testMarshalUnmarshalJsonb() {

        final Data data = fromJson(JSON, Data.class);
        assertThat(data.weeklyOpeningHours).isEqualTo(new WeeklyOpeningHours(HOURS));

    }

    @Test
    void testUnmarshalErrorJsonb() {

        final String invalidJsonData = "{\"weeklyOpeningHours\":\"17-18+19-20\"}";
        assertThatThrownBy(() -> fromJson(invalidJsonData, Data.class))
                .hasRootCauseInstanceOf(ConstraintViolationException.class)
                .hasRootCauseMessage("The argument 'weeklyOpeningHours' does not represent valid weekly opening hours like 'Mon-Fri 09:00-12:00+13:00-17:00,Sat/Sun 09:-12:00': '17-18+19-20'");

    }

}
