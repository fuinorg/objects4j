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

public final class DayOpeningHoursStrValidatorTest {

    private DayOpeningHoursStrValidator testee;

    @BeforeEach
    public final void setUp() {
        testee = new DayOpeningHoursStrValidator();
    }

    @AfterEach
    public final void tearDown() {
        testee = null;
    }

    @Test
    void testIsValidTRUE() {

        assertThat(testee.isValid(null, null)).isTrue();
        assertThat(testee.isValid("Mon 00:00-24:00", null)).isTrue();
        assertThat(testee.isValid("Mon 09:00-12:00+13:00-17:00", null)).isTrue();

    }

    @Test
    void testIsValidFALSE() {

        assertThat(testee.isValid("", null)).isFalse();
        assertThat(testee.isValid("-", null)).isFalse();
        assertThat(testee.isValid("Monday 12:00-", null)).isFalse();
        assertThat(testee.isValid("Monday", null)).isFalse();
        assertThat(testee.isValid("Mon", null)).isFalse();
        assertThat(testee.isValid("Mon 12-13", null)).isFalse();
        assertThat(testee.isValid(" 12:00-13:00", null)).isFalse();
        assertThat(testee.isValid("Mon 12:00-13:00+18-19", null)).isFalse();
        assertThat(testee.isValid("Mon09:00-12:00", null)).isFalse();

    }

}
