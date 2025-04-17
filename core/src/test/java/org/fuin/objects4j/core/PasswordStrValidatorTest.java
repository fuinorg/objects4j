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

public final class PasswordStrValidatorTest {

    private PasswordStrValidator testee;

    @BeforeEach
    public final void setUp() {
        testee = new PasswordStrValidator();
    }

    @AfterEach
    public final void tearDown() {
        testee = null;
    }

    @Test
    void testIsValidTRUE() {

        assertThat(testee.isValid("12345678", null)).isTrue();
        assertThat(testee.isValid("12345678901234567890", null)).isTrue();
        assertThat(testee.isValid(null, null)).isTrue();

    }

    @Test
    void testIsValidEmpty() {
        assertThat(testee.isValid("", null)).isFalse();
    }

    @Test
    void testIsValidTooShort() {
        assertThat(testee.isValid("1234567", null)).isFalse();
    }

    @Test
    void testIsValidTooLong() {
        assertThat(testee.isValid("123456789012345678901", null)).isFalse();
    }

}
