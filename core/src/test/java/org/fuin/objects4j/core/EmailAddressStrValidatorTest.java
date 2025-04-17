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

import static org.assertj.core.api.Assertions.assertThat;

public final class EmailAddressStrValidatorTest {

    private EmailAddressStrValidator testee;

    @BeforeEach
    public final void setUp() {
        testee = new EmailAddressStrValidator();
    }

    @AfterEach
    public final void tearDown() {
        testee = null;
    }

    @Test
    void testIsValidTRUE() {

        assertThat(testee.isValid("a@b.c", null)).isTrue();
        assertThat(testee.isValid("abc@def", null)).isTrue();
        assertThat(testee.isValid("abc@def.ghi", null)).isTrue();
        assertThat(testee.isValid(null, null)).isTrue();

    }

    @Test
    void testIsValidFALSE() {

        assertThat(testee.isValid("", null)).isFalse();
        assertThat(testee.isValid("abc@", null)).isFalse();
        assertThat(testee.isValid("abc@.", null)).isFalse();
        assertThat(testee.isValid("abc@.def", null)).isFalse();

    }

    @Test
    void testRequireArgValid() {

        try {
            EmailAddress.requireArgValid("a", "");
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'a' is not valid: ''");
        }

        try {
            EmailAddress.requireArgValid("a", "abc@");
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'a' is not valid: 'abc@'");
        }

    }

}
