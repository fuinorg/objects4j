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
package org.fuin.objects4j.validation;

import static org.fest.assertions.Assertions.assertThat;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//TESTCODE:BEGIN
public final class UUIDStrValidatorTest {

    private UUIDStrValidator testee;

    @Before
    public final void setUp() {
        testee = new UUIDStrValidator();
    }

    @After
    public final void tearDown() {
        testee = null;
    }

    @Test
    public final void testValidConstantLowerCase() {
        assertThat(testee.isValid("089ba5d3-1027-421d-add7-7e65f95b6ed6", null)).isTrue();
    }

    @Test
    public final void testValidConstantUpperCase() {
        assertThat(testee.isValid("089ba5d3-1027-421d-add7-7e65f95b6ed6", null)).isTrue();
    }

    @Test
    public final void testValidNullValue() {
        assertThat(testee.isValid(null, null)).isTrue();
    }

    @Test
    public final void testValidRandom() {
        assertThat(testee.isValid(UUID.randomUUID().toString(), null)).isTrue();
    }

    @Test
    public final void testInvalidEmpty() {
        assertThat(testee.isValid("", null)).isFalse();
    }

    @Test
    public final void testInvalidSingleNumber() {
        assertThat(testee.isValid("1", null)).isFalse();
    }

    @Test
    public final void testInvalidSpecialCharacters() {
        assertThat(testee.isValid("089ba5d3-10ä7-421d-add7-7e65f95b6ed6", null)).isFalse();
    }

}
// TESTCODE:END
