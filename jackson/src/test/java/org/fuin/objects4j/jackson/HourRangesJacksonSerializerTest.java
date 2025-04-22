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
import org.fuin.objects4j.core.HourRanges;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Test for {@link HourRanges} serialization.
 */
public class HourRangesJacksonSerializerTest {

    private static final String JSON = "{\"hourRanges\":\"09:00-12:00+13:00-17:00\"}";

    @Test
    public final void testMarshal() throws JsonProcessingException {

        final Data data = new Data();
        data.hourRanges = new HourRanges("09:00-12:00+13:00-17:00");
        assertThat(JacksonHelper.toJson(data)).isEqualTo(JSON);

    }

}
