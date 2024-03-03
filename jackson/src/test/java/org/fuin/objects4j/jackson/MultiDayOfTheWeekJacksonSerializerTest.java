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
import org.fuin.objects4j.core.MultiDayOfTheWeek;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Test for the {@link MultiDayOfTheWeekJacksonSerializer} class.
 */
public class MultiDayOfTheWeekJacksonSerializerTest {

    private static final String JSON = "{\"multiDayOfTheWeek\":\"MON/TUE\"}";

    @Test
    public final void testMarshal() throws JsonProcessingException {

        final Data data = new Data();
        data.multiDayOfTheWeek = new MultiDayOfTheWeek("Mon/Tue");
        assertThat(JacksonHelper.toJson(data)).isEqualTo(JSON);

    }

}