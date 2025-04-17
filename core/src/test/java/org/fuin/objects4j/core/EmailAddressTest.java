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

public final class EmailAddressTest {

    @Test
    void testSerialize() {
        final String emailAddress = "abc@def.com";
        final EmailAddress original = new EmailAddress(emailAddress);
        final EmailAddress copy = deserialize(serialize(original));
        assertThat(original).isEqualTo(copy);
    }

    @Test
    void testConstructValid() {
        final String emailAddress = "abc@def.com";
        assertThat(new EmailAddress(emailAddress).toString()).isEqualTo(emailAddress);
        assertThat(new EmailAddress(emailAddress).length()).isEqualTo(emailAddress.length());
    }

    @Test
    void testConstructEmpty() {
        assertThatThrownBy(() -> {
            new EmailAddress("");
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testConstructIllegal() {
        assertThatThrownBy(() -> {
            new EmailAddress("abc@");
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testEqualsLowerUpperCase() {
        assertThat(new EmailAddress("Abc@DeF.Com")).isEqualTo(new EmailAddress("aBc@deF.cOM"));
    }

    @Test
    void testValueOf() {
        assertThat(EmailAddress.valueOf("a@b.c").toString()).isEqualTo("a@b.c");
    }

    @Test
    void testValueIsValid() {
        assertThat(EmailAddress.isValid("a@b.c")).isTrue();
    }

}
