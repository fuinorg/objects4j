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

import static org.fest.assertions.Assertions.assertThat;

import org.fuin.objects4j.common.AbstractBaseTest;
import org.fuin.objects4j.common.ContractViolationException;
import org.junit.Test;

// CHECKSTYLE:OFF
public final class KeyValueTest extends AbstractBaseTest {

    @Test
    public final void testCreateValid() {

        assertThat(new KeyValue("key", null).getKey()).isEqualTo("key");
        assertThat(new KeyValue("key", null).getValue()).isNull();
        assertThat(new KeyValue("key", "value").getValue()).isEqualTo("value");

    }

    @Test
    public final void testCreateInvalidKey() {

        try {
            new KeyValue(null, null);
        } catch (final ContractViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'key' cannot be null");
        }

        try {
            new KeyValue("", null);
        } catch (final ContractViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'key' cannot be empty");
        }

    }

    @Test
    public final void testReplaceVarsNull() {
        assertThat(KeyValue.replace(null)).isNull();
    }

    @Test
    public final void testReplaceVarsEmpty() {
        assertThat(KeyValue.replace("")).isEqualTo("");
    }

    @Test
    public final void testReplaceVarsNoVars() {
        assertThat(KeyValue.replace("one two three")).isEqualTo("one two three");
    }

    @Test
    public final void testReplaceVarsSingleVar1() {
        assertThat(KeyValue.replace("${one}", new KeyValue("one", "1"))).isEqualTo("1");
    }

    @Test
    public final void testReplaceVarsSingleVar2() {
        assertThat(KeyValue.replace(" ${one}", new KeyValue("one", "1"))).isEqualTo(" 1");
    }

    @Test
    public final void testReplaceVarsSingleVar3() {
        assertThat(KeyValue.replace("${one} ", new KeyValue("one", "1"))).isEqualTo("1 ");
    }

    @Test
    public final void testReplaceVarsSingleVar4() {
        assertThat(KeyValue.replace(" ${one} ", new KeyValue("one", "1"))).isEqualTo(" 1 ");
    }

    @Test
    public final void testReplaceVarsMultipleVars1() {
        assertThat(
                KeyValue.replace(" ${one} ${two} ${3} ", new KeyValue("one", "1"), new KeyValue(
                        "two", "2"), new KeyValue("3", "three"))).isEqualTo(" 1 2 three ");
    }

    @Test
    public final void testReplaceVarsMultipleVars2() {
        assertThat(
                KeyValue.replace("${one} ${two} ${3} ", new KeyValue("one", "1"), new KeyValue(
                        "two", "2"), new KeyValue("3", "three"))).isEqualTo("1 2 three ");
    }

    @Test
    public final void testReplaceVarsMultipleVars3() {
        assertThat(
                KeyValue.replace("${one}${two}${3}", new KeyValue("one", "1"), new KeyValue("two",
                        "2"), new KeyValue("3", "three"))).isEqualTo("12three");
    }

    @Test
    public final void testReplaceVarsMultipleVars4() {
        assertThat(
                KeyValue.replace(" ${one} ${two} ${3}", new KeyValue("one", "1"), new KeyValue(
                        "two", "2"), new KeyValue("3", "three"))).isEqualTo(" 1 2 three");
    }

    @Test
    public final void testReplaceVarsUnknown1() {
        assertThat(KeyValue.replace("${xyz}", new KeyValue("one", "1"))).isEqualTo("${xyz}");
    }

    @Test
    public final void testReplaceVarsUnknown2() {
        assertThat(KeyValue.replace("${one}${xyz}", new KeyValue("one", "1"))).isEqualTo("1${xyz}");
    }

    @Test
    public final void testReplaceVarsUnknown3() {
        assertThat(KeyValue.replace("${xyz}${two}", new KeyValue("two", "2"))).isEqualTo("${xyz}2");
    }

    @Test
    public final void testReplaceVarsUnknown4() {
        assertThat(
                KeyValue.replace("${one}${xyz}${two}", new KeyValue("one", "1"), new KeyValue(
                        "two", "2"))).isEqualTo("1${xyz}2");
    }

    @Test
    public final void testReplaceVarsNoClosingBracket() {
        assertThat(
                KeyValue.replace("${one}${two", new KeyValue("one", "1"), new KeyValue("two", "2")))
                .isEqualTo("1${two");
    }
}
// CHECKSTYLE:ON
