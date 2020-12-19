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
package org.fuin.objects4j.vo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//TESTCODE:BEGIN
public final class HourStrValidatorTest {

    private HourStrValidator testee;

    @Before
    public final void setUp() {
        testee = new HourStrValidator();
    }

    @After
    public final void tearDown() {
        testee = null;
    }

    @Test
    public final void testIsValidTRUE() {

        assertThat(testee.isValid(null, null)).isTrue();
        assertThat(testee.isValid("00:00", null)).isTrue();
        assertThat(testee.isValid("00:01", null)).isTrue();
        assertThat(testee.isValid("01:00", null)).isTrue();
        assertThat(testee.isValid("11:59", null)).isTrue();
        assertThat(testee.isValid("12:00", null)).isTrue();
        assertThat(testee.isValid("12:01", null)).isTrue();
        assertThat(testee.isValid("23:59", null)).isTrue();
        assertThat(testee.isValid("24:00", null)).isTrue();

    }

    @Test
    public final void testIsValidFALSE() {

        assertThat(testee.isValid("", null)).isFalse();
        assertThat(testee.isValid("1", null)).isFalse();
        assertThat(testee.isValid("12", null)).isFalse();
        assertThat(testee.isValid("12:", null)).isFalse();
        assertThat(testee.isValid("12:0", null)).isFalse();
        assertThat(testee.isValid("12:000", null)).isFalse();
        assertThat(testee.isValid("123:000", null)).isFalse();
        assertThat(testee.isValid("23.59", null)).isFalse();
        assertThat(testee.isValid("24:01", null)).isFalse();

    }

}
// TESTCODE:END
