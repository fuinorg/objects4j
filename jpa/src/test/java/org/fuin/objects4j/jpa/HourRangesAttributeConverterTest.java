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

import org.fuin.objects4j.core.HourRanges;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class HourRangesAttributeConverterTest {

    @Test
    public void testNull() {
        final HourRangesAttributeConverter testee = new HourRangesAttributeConverter();
        assertThat(testee.convertToDatabaseColumn(null)).isNull();
        assertThat((Object) testee.convertToEntityAttribute(null)).isNull();
    }

    @Test
    public void testRoundTrip() {
        final HourRangesAttributeConverter testee = new HourRangesAttributeConverter();
        final HourRanges value = new HourRanges("13:00-14:00");
        assertThat((Object) testee.convertToEntityAttribute(testee.convertToDatabaseColumn(value))).isEqualTo(value);
    }

}
