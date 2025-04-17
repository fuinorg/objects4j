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

public final class UserNameTest {

    @Test
    void testSerialize() {
        final String userName = "michael-1_a";
        final UserName original = new UserName(userName);
        final UserName copy = deserialize(serialize(original));
        assertThat(original).isEqualTo(copy);
    }

    @Test
    void testCreateValidLowerCase() {
        final String userName = "michael-1_a";
        assertThat(new UserName(userName).toString()).isEqualTo(userName);
        assertThat(new UserName(userName).length()).isEqualTo(userName.length());
    }

    @Test
    void testCreateValidUpperCase() {
        final String userName = "MICHAEL-1_a";
        assertThat(new UserName(userName).toString()).isEqualTo(userName.toLowerCase());
        assertThat(new UserName(userName).length()).isEqualTo(userName.length());
    }

    @Test
    void testEqualsLowerUpperCase() {
        assertThat(new UserName("abc-1_B")).isEqualTo(new UserName("AbC-1_b"));
    }

    @Test
    void testCreateEmpty() {
        assertThatThrownBy(() -> {
            new UserName("");
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testCreateTooShort() {
        assertThatThrownBy(() -> {
            new UserName("ab");
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testCreateTooLong() {
        assertThatThrownBy(() -> {
            new UserName("a12345678901234567890");
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testCreateStartsWithNumber() {
        assertThatThrownBy(() -> {
            new UserName("1abc");
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testCreateStartsWithUnderscore() {
        assertThatThrownBy(() -> {
            new UserName("_abc");
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testCreateStartsWithHyphen() {
        assertThatThrownBy(() -> {
            new UserName("-abc");
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testCreateContainsIllegalDoubleCross() {
        assertThatThrownBy(() -> {
            new UserName("abc#1");
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testCreateContainsIllegalAtAndDot() {
        assertThatThrownBy(() -> {
            new UserName("abc@def.com");
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testValueOf() {
        assertThat(UserName.valueOf("peter").toString()).isEqualTo("peter");
        assertThatThrownBy(() -> {
            UserName.valueOf("abc@def.com");
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testValueIsValid() {
        assertThat(UserName.isValid("peter")).isTrue();
        assertThat(UserName.isValid("abc@def.com")).isFalse();
    }

}
