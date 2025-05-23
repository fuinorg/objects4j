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

import nl.jqno.equalsverifier.EqualsVerifier;
import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.utils4j.Utils4J;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// CHECKSTYLE:OFF
public final class KeyValueTest {

    @Test
    void testCreateValid() {

        assertThat(new KeyValue("key", null).getKey()).isEqualTo("key");
        assertThat(new KeyValue("key", null).getValue()).isNull();
        assertThat(new KeyValue("key", "value").getValue()).isEqualTo("value");

    }

    @Test
    void testCreateInvalidKey() {

        try {
            new KeyValue(null, null);
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'key' cannot be null");
        }

        try {
            new KeyValue("", null);
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'key' cannot be empty");
        }

    }

    @Test
    void testEqualHashCode() {
        EqualsVerifier.forClass(KeyValue.class).withIgnoredFields("value").verify();
    }

    @Test
    void testSerializeDeserialize() {
        final KeyValue original = new KeyValue("one", 1);
        final KeyValue copy = Utils4J.deserialize(Utils4J.serialize(original));
        assertThat(copy).isEqualTo(original);
        assertThat(copy.getKey()).isEqualTo(original.getKey());
        assertThat(copy.getValue()).isEqualTo(original.getValue());
        assertThat(copy.getValueString()).isEqualTo("1");
    }

    @Test
    void testReplaceVarsNull() {
        assertThat(KeyValue.replace(null)).isNull();
    }

    @Test
    void testReplaceVarsEmpty() {
        assertThat(KeyValue.replace("")).isEqualTo("");
    }

    @Test
    void testReplaceVarsNoVars() {
        assertThat(KeyValue.replace("one two three")).isEqualTo("one two three");
    }

    @Test
    void testReplaceVarsSingleVar1() {
        assertThat(KeyValue.replace("${one}", new KeyValue("one", "1"))).isEqualTo("1");
    }

    @Test
    void testReplaceVarsSingleVar2() {
        assertThat(KeyValue.replace(" ${one}", new KeyValue("one", "1"))).isEqualTo(" 1");
    }

    @Test
    void testReplaceVarsSingleVar3() {
        assertThat(KeyValue.replace("${one} ", new KeyValue("one", "1"))).isEqualTo("1 ");
    }

    @Test
    void testReplaceVarsSingleVar4() {
        assertThat(KeyValue.replace(" ${one} ", new KeyValue("one", "1"))).isEqualTo(" 1 ");
    }

    @Test
    void testReplaceVarsMultipleVars1() {
        assertThat(KeyValue.replace(" ${one} ${two} ${3} ", new KeyValue("one", "1"), new KeyValue("two", "2"), new KeyValue("3", "three")))
                .isEqualTo(" 1 2 three ");
    }

    @Test
    void testReplaceVarsMultipleVars2() {
        assertThat(KeyValue.replace("${one} ${two} ${3} ", new KeyValue("one", "1"), new KeyValue("two", "2"), new KeyValue("3", "three")))
                .isEqualTo("1 2 three ");
    }

    @Test
    void testReplaceVarsMultipleVars3() {
        assertThat(KeyValue.replace("${one}${two}${3}", new KeyValue("one", "1"), new KeyValue("two", "2"), new KeyValue("3", "three")))
                .isEqualTo("12three");
    }

    @Test
    void testReplaceVarsMultipleVars4() {
        assertThat(KeyValue.replace(" ${one} ${two} ${3}", new KeyValue("one", "1"), new KeyValue("two", "2"), new KeyValue("3", "three")))
                .isEqualTo(" 1 2 three");
    }

    @Test
    void testReplaceVarsUnknown1() {
        assertThat(KeyValue.replace("${xyz}", new KeyValue("one", "1"))).isEqualTo("${xyz}");
    }

    @Test
    void testReplaceVarsUnknown2() {
        assertThat(KeyValue.replace("${one}${xyz}", new KeyValue("one", "1"))).isEqualTo("1${xyz}");
    }

    @Test
    void testReplaceVarsUnknown3() {
        assertThat(KeyValue.replace("${xyz}${two}", new KeyValue("two", "2"))).isEqualTo("${xyz}2");
    }

    @Test
    void testReplaceVarsUnknown4() {
        assertThat(KeyValue.replace("${one}${xyz}${two}", new KeyValue("one", "1"), new KeyValue("two", "2"))).isEqualTo("1${xyz}2");
    }

    @Test
    void testReplaceVarsNoClosingBracket() {
        assertThat(KeyValue.replace("${one}${two", new KeyValue("one", "1"), new KeyValue("two", "2"))).isEqualTo("1${two");
    }

    @Test
    void testReplaceVarsNullVar() {
        assertThat(KeyValue.replace("${one} ${two} three", new KeyValue("one", "1"), new KeyValue("two", null))).isEqualTo("1 null three");
    }

    @Test
    void testReplaceVarsNullVarCollection() {

        final List<String> values = new ArrayList<>();
        values.add("a");
        values.add(null);
        values.add("c");

        assertThat(KeyValue.replace("${one} (${two}) three", new KeyValue("one", "1"), new KeyValue("two", values)))
                .isEqualTo("1 (a, null, c) three");
    }

}
// CHECKSTYLE:ON
