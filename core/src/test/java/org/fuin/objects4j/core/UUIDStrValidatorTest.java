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

import org.assertj.core.api.Assertions;
import org.fuin.objects4j.common.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

//CHECKSTYLE:OFF
public final class UUIDStrValidatorTest {

    private UUIDStrValidator testee;

    @BeforeEach
    public final void setUp() {
        testee = new UUIDStrValidator();
    }

    @AfterEach
    public final void tearDown() {
        testee = null;
    }

    @Test
    void testValidConstantLowerCase() {

        final String value = "089ba5d3-1027-421d-add7-7e65f95b6ed6";
        assertThat(testee.isValid(value, null)).isTrue();
        assertThat(UUIDStrValidator.parseArg("a", value)).isEqualTo(UUID.fromString(value));

    }

    @Test
    void testValidConstantUpperCase() {
        final String value = "089ba5d3-1027-421d-add7-7e65f95b6ed6";
        assertThat(testee.isValid(value, null)).isTrue();
        assertThat(UUIDStrValidator.parseArg("a", value)).isEqualTo(UUID.fromString(value));
    }

    @Test
    void testValidNullValue() {

        assertThat(testee.isValid(null, null)).isTrue();

        // UUIDStrValidator.parseArg(..) does not allow NULL

    }

    @Test
    void testValidRandom() {
        final String value = UUID.randomUUID().toString();
        assertThat(testee.isValid(value, null)).isTrue();
        assertThat(UUIDStrValidator.parseArg("a", value)).isEqualTo(UUID.fromString(value));
    }

    @Test
    void testInvalidEmpty() {

        assertThat(testee.isValid("", null)).isFalse();

        try {
            UUIDStrValidator.parseArg("a", "");
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'a' is not valid: ''");
        }

    }

    @Test
    void testInvalidSingleNumber() {

        assertThat(testee.isValid("1", null)).isFalse();

        try {
            UUIDStrValidator.parseArg("a", "1");
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'a' is not valid: '1'");
        }

    }

    @Test
    void testInvalidSpecialCharacters() {

        final String value = "089ba5d3-10ä7-421d-add7-7e65f95b6ed6";
        assertThat(testee.isValid(value, null)).isFalse();

        try {
            UUIDStrValidator.parseArg("a", value);
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'a' is not valid: '" + value + "'");
        }

    }

    @Test
    void testParseArg() {

    }

}
// CHECKSTYLE:ON
