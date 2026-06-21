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
package org.fuin.objects4j.jpa;

import org.fuin.objects4j.core.MultiDayOfTheWeek;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class MultiDayOfTheWeekAttributeConverterTest {

    @Test
    public void testNull() {
        final MultiDayOfTheWeekAttributeConverter testee = new MultiDayOfTheWeekAttributeConverter();
        assertThat(testee.convertToDatabaseColumn(null)).isNull();
        assertThat((Object) testee.convertToEntityAttribute(null)).isNull();
    }

    @Test
    public void testRoundTrip() {
        final MultiDayOfTheWeekAttributeConverter testee = new MultiDayOfTheWeekAttributeConverter();
        final MultiDayOfTheWeek value = new MultiDayOfTheWeek("Mon/Tue");
        assertThat((Object) testee.convertToEntityAttribute(testee.convertToDatabaseColumn(value))).isEqualTo(value);
    }

}
