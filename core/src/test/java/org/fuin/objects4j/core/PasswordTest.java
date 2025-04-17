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

import org.fuin.objects4j.common.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fuin.utils4j.Utils4J.deserialize;
import static org.fuin.utils4j.Utils4J.serialize;

public final class PasswordTest {

    @Test
    void testSerialize() {
        final String password = "very-secret";
        final Password original = new Password(password);
        final Password copy = deserialize(serialize(original));
        assertThat(original).isEqualTo(copy);
    }

    @Test
    void testCreateValid() {
        final String password = "very-secret";
        assertThat(new Password(password).toString()).isEqualTo(password);
        assertThat(new Password(password).length()).isEqualTo(password.length());
    }

    @Test
    void testCreateEmpty() {
        assertThatThrownBy(() -> {
            new Password("");
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testCreateTooShort() {
        assertThatThrownBy(() -> {
            new Password("abc");
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testCreateTooLong() {
        assertThatThrownBy(() -> {
            new Password("123456789012345678901");
        }).isInstanceOf(ConstraintViolationException.class);
    }

}
