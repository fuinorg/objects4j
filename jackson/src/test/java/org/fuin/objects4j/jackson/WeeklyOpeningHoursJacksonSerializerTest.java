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
package org.fuin.objects4j.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.core.WeeklyOpeningHours;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Test for {@link WeeklyOpeningHours} serialization.
 */
public class WeeklyOpeningHoursJacksonSerializerTest {

    private static final String HOURS = "MON-FRI 06:00-18:00,SAT/SUN 06:00-12:00";

    private static final String JSON = "{\"weeklyOpeningHours\":\"" + HOURS + "\"}";

    @Test
    public final void testMarshal() throws JsonProcessingException {

        final Data data = new Data();
        data.weeklyOpeningHours = new WeeklyOpeningHours(HOURS);
        assertThat(JacksonHelper.toJson(data)).isEqualTo(JSON);

    }

}
