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
import static org.junit.Assert.fail;

import java.util.UUID;

import org.fuin.objects4j.common.ConstraintViolationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//CHECKSTYLE:OFF
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

        final String value = "089ba5d3-1027-421d-add7-7e65f95b6ed6";
        assertThat(testee.isValid(value, null)).isTrue();
        assertThat(UUIDStrValidator.parseArg("a", value)).isEqualTo(UUID.fromString(value));

    }

    @Test
    public final void testValidConstantUpperCase() {
        final String value = "089ba5d3-1027-421d-add7-7e65f95b6ed6";
        assertThat(testee.isValid(value, null)).isTrue();
        assertThat(UUIDStrValidator.parseArg("a", value)).isEqualTo(UUID.fromString(value));
    }

    @Test
    public final void testValidNullValue() {

        assertThat(testee.isValid(null, null)).isTrue();

        // UUIDStrValidator.parseArg(..) does not allow NULL

    }

    @Test
    public final void testValidRandom() {
        final String value = UUID.randomUUID().toString();
        assertThat(testee.isValid(value, null)).isTrue();
        assertThat(UUIDStrValidator.parseArg("a", value)).isEqualTo(UUID.fromString(value));
    }

    @Test
    public final void testInvalidEmpty() {

        assertThat(testee.isValid("", null)).isFalse();

        try {
            UUIDStrValidator.parseArg("a", "");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'a' is not valid: ''");
        }

    }

    @Test
    public final void testInvalidSingleNumber() {

        assertThat(testee.isValid("1", null)).isFalse();

        try {
            UUIDStrValidator.parseArg("a", "1");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'a' is not valid: '1'");
        }

    }

    @Test
    public final void testInvalidSpecialCharacters() {

        final String value = "089ba5d3-10ä7-421d-add7-7e65f95b6ed6";
        assertThat(testee.isValid(value, null)).isFalse();

        try {
            UUIDStrValidator.parseArg("a", value);
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'a' is not valid: '" + value + "'");
        }

    }

    @Test
    public final void testParseArg() {

    }

}
// CHECKSTYLE:ON
