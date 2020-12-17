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
package org.fuin.objects4j.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

import org.junit.Test;

//TESTCODE:BEGIN
public final class ConstraintViolationExceptionTest {

    @Test
    public final void testCreateMessage() {
        final String message = "A very important error message";
        final ConstraintViolationException ex = new ConstraintViolationException(message);
        assertThat(ex.getMessage()).isEqualTo(message);
        assertThat(ex.getConstraintViolations()).isNull();
    }

    @Test
    public final void testCreateMessageViolations() {
        final String message = "A very important error message";
        final Set<ConstraintViolation<Object>> violations = Contract.getValidator().validate(new TestBean());
        final ConstraintViolationException ex = new ConstraintViolationException(message, violations);
        assertThat(ex.getMessage()).isEqualTo(message);
        assertThat(ex.getConstraintViolations()).hasSize(2);
    }

    private static class TestBean {

        @NotNull
        private Integer a;

        @NotEmpty
        private String b;

    }

}
// TESTCODE:END
