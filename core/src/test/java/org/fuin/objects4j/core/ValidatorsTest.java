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

import jakarta.validation.Validator;
import jakarta.validation.constraints.Size;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// CHECKSTYLE:OFF
public final class ValidatorsTest {

    @AfterEach
    void tearDown() {
        // Make sure no per-thread validator leaks between tests on this (shared) thread.
        Validators.clear();
    }

    @Test
    void testGetReturnsSameValidatorOnSameThread() {
        assertThat(Validators.get()).isNotNull();
        assertThat(Validators.get()).isSameAs(Validators.get());
    }

    @Test
    void testClearReleasesThreadLocalValidator() {
        final Validator first = Validators.get();

        Validators.clear();

        assertThat(Validators.get()).isNotSameAs(first);
    }

    @Test
    void testValidateValueAppliesTheConstraintsOfTheProperty() {
        // This is how a generated value object verifies its value.
        assertThat(Validators.get().validateValue(MyValue.class, "value", "abc")).isEmpty();
        assertThat(Validators.get().validateValue(MyValue.class, "value", "ab")).isNotEmpty();
        assertThat(Validators.get().validateValue(MyValue.class, "value", "abcdefg")).isNotEmpty();
    }

    @Test
    void testValidateValueConsidersNullValid() {
        assertThat(Validators.get().validateValue(MyValue.class, "value", null)).isEmpty();
    }

    private static final class MyValue {

        @Size(min = 3, max = 6)
        private String value;

    }

}
