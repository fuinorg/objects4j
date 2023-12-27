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

//TESTCODE:BEGIN
public final class PasswordSha512StrValidatorTest {

    private PasswordSha512StrValidator testee;

    @BeforeEach
    public final void setUp() {
        testee = new PasswordSha512StrValidator();
    }

    @AfterEach
    public final void tearDown() {
        testee = null;
    }

    @Test
    public final void testIsValidTRUE() {

        assertThat(testee.isValid("b4d4b13a02230f9672c92fd45aa44fd202bdba7de3fda640ac84ee5d54c"
                + "57fde293b4bbddce1b9a56d63d674b47c4dd7e6d89536a1e126ebf0cd662e76e8bec3", null)).isTrue();
        assertThat(testee.isValid("aa3b7bdd98ec44af1f395bbd5f7f27a5cd9569d794d032747323bf4b1521fb"
                + "e7725875a68b440abdf0559de5015baf873bb9c01cae63ecea93ad547a7397416e", null)).isTrue();

    }

    @Test
    public final void testIsValidEmpty() {
        assertThat(testee.isValid("", null)).isFalse();
    }

    @Test
    public final void testIsValidNull() {
        assertThat(testee.isValid(null, null)).isTrue();
    }

    @Test
    public final void testIsValidTooShort() {
        assertThat(testee.isValid("b4d4b13a02230f9672c92fd45aa44fd202bdba7de3fda640ac84ee5d54c5"
                + "7fde293b4bbddce1b9a56d63d674b47c4dd7e6d89536a1e126ebf0cd662e76e8be", null)).isFalse();
    }

    @Test
    public final void testIsValidTooLong() {
        assertThat(testee.isValid("b4d4b13a02230f9672c92fd45aa44fd202bdba7de3fda640ac84ee5d54c57fde"
                + "293b4bbddce1b9a56d63d674b47c4dd7e6d89536a1e126ebf0cd662e76e8bec31c", null)).isFalse();
    }

    @Test
    public final void testIsValidIllegalCharacters() {
        assertThat(testee.isValid("fa 85d89c851dd338a70dcf5zzaa2a92fee7836dd6aff122yy83e88e0996"
                + "293f16bc009c652826e0fc5c706695a03cddce372f139eff4d13959da6f1f5d3eabe", null)).isFalse();
    }

}
// TESTCODE:END
