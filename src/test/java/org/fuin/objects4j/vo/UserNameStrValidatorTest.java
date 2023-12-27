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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// CHECKSTYLE:OFF
public final class UserNameStrValidatorTest {

    private UserNameStrValidator testee;

    @BeforeEach
    public final void setUp() {
        testee = new UserNameStrValidator();
    }

    @AfterEach
    public final void tearDown() {
        testee = null;
    }

    @Test
    public final void testIsValidTRUE() {

        assertThat(testee.isValid("ab1", null)).isTrue();
        assertThat(testee.isValid("michael-1_a", null)).isTrue();
        assertThat(testee.isValid("michael8901234567890", null)).isTrue();
        assertThat(testee.isValid(null, null)).isTrue();

    }

    @Test
    public final void testIsValidUpperCase() {
        assertThat(testee.isValid("MICHAEL-1_a", null)).isFalse();
    }

    @Test
    public final void testIsValidEmpty() {
        assertThat(testee.isValid("", null)).isFalse();
    }

    @Test
    public final void testIsValidTooShort() {
        assertThat(testee.isValid("ab", null)).isFalse();
    }

    @Test
    public final void testIsValidTooLong() {
        assertThat(testee.isValid("a12345678901234567890", null)).isFalse();
    }

    @Test
    public final void testIsValidStartsWithNumber() {
        assertThat(testee.isValid("1ab", null)).isFalse();
    }

    @Test
    public final void testCreateStartsWithUnderscore() {
        assertThat(testee.isValid("_abc", null)).isFalse();
    }

    @Test
    public final void testCreateStartsWithHyphen() {
        assertThat(testee.isValid("-abc", null)).isFalse();
    }

    @Test
    public final void testCreateContainsIllegalDoubleCross() {
        assertThat(testee.isValid("abc#1", null)).isFalse();
    }

    @Test
    public final void testCreateContainsIllegalAtAndDot() {
        assertThat(testee.isValid("abc@def.com", null)).isFalse();
    }

}
// CHECKSTYLE:ON
