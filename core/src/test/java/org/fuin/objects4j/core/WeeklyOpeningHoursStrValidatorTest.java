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
package org.fuin.objects4j.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class WeeklyOpeningHoursStrValidatorTest {

    private WeeklyOpeningHoursStrValidator testee;

    @BeforeEach
    public final void setUp() {
        testee = new WeeklyOpeningHoursStrValidator();
    }

    @AfterEach
    public final void tearDown() {
        testee = null;
    }

    @Test
    void testIsValidTRUE() {

        assertThat(testee.isValid(null, null)).isTrue();
        assertThat(testee.isValid("Mon 00:00-24:00", null)).isTrue();
        assertThat(testee.isValid("Mon 07:00-09:00+10:00-12:00", null)).isTrue();
        assertThat(testee.isValid("Mon/Tue 07:00-09:00+10:00-12:00+13:00-15:00", null)).isTrue();
        assertThat(testee.isValid("Mon-Tue 07:00-09:00", null)).isTrue();
        assertThat(testee.isValid("Mon-Sun 06:00-18:00", null)).isTrue();
        assertThat(testee.isValid("Mon-Fri 06:00-18:00,Sat/Sun 06:00-12:00", null)).isTrue();

    }

    @Test
    void testIsValidFALSE() {

        assertThat(testee.isValid("", null)).isFalse();
        assertThat(testee.isValid(" ", null)).isFalse();
        assertThat(testee.isValid(", ", null)).isFalse();
        assertThat(testee.isValid("Mon 00:00-24:00,Mon 01:00-02:00", null)).isFalse();
        assertThat(testee.isValid("Mon-Fri 00:00-24:00,Wed 00:00-24:00", null)).isFalse();

    }

}
