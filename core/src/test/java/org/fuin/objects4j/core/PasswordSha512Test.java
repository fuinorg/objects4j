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

public final class PasswordSha512Test {

    private static final String VALID_VALUE = "b4d4b13a02230f9672c92fd45aa44fd202bdba7de3fda640ac84ee5d54c57fde293b4bbddce1b9a56d63d674b47c4dd7e6d89536a1e126ebf0cd662e76e8bec3";

    @Test
    void testSerialize() {
        final PasswordSha512 original = new PasswordSha512(new Password("very-secret"));
        final PasswordSha512 copy = deserialize(serialize(original));
        assertThat(original).isEqualTo(copy);
    }

    @Test
    void testCreateWithPasswordValid() {
        assertThat(new PasswordSha512(new Password("very-secret"))).isEqualTo(new PasswordSha512(new Password("very-secret")));
        assertThat(new PasswordSha512(new Password("very-secret"))).isNotEqualTo(new PasswordSha512(new Password("very-secret2")));
    }

    @Test
    void testCreateWithStringValid() {
        final String verySecret = new PasswordSha512(new Password("very-secret")).toString();
        final String verySecret2 = new PasswordSha512(new Password("very-secret2")).toString();

        assertThat(new PasswordSha512(verySecret)).isEqualTo(new PasswordSha512(verySecret));
        assertThat(new PasswordSha512(verySecret)).isNotEqualTo(new PasswordSha512(verySecret2));
    }

    @Test
    void testCreateEmpty() {
        assertThatThrownBy(() -> {
            new PasswordSha512("");
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testCreateTooShort() {
        assertThatThrownBy(() -> {
            new PasswordSha512("b4d4b13a02230f9672c92fd45aa44fd202bdba7de3fda640ac84ee5d54c"
                    + "57fde293b4bbddce1b9a56d63d674b47c4dd7e6d89536a1e126ebf0cd662e76e8be");
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testCreateTooLong() {
        assertThatThrownBy(() -> {
            new PasswordSha512("b4d4b13a02230f9672c92fd45aa44fd202bdba7de3fda640ac84ee5d54c"
                    + "57fde293b4bbddce1b9a56d63d674b47c4dd7e6d89536a1e126ebf0cd662e76e8bec3b4");
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testValueOf() {
        assertThat(PasswordSha512.valueOf(VALID_VALUE).toString()).isEqualTo(VALID_VALUE);
    }

    @Test
    void testValueIsValid() {
        assertThat(PasswordSha512.isValid(VALID_VALUE)).isTrue();
        assertThat(PasswordSha512.isValid("X")).isFalse();

    }

}
