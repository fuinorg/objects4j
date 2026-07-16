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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// CHECKSTYLE:OFF
public final class KeyValueELTest {

    @AfterEach
    void tearDown() {
        // Make sure no per-thread ELProcessor leaks between tests on this (shared) thread.
        KeyValueEL.clear();
    }

    @Test
    void testReplaceNullMessage() {
        assertThat(KeyValueEL.replace(null)).isNull();
    }

    @Test
    void testReplaceEmptyMessage() {
        assertThat(KeyValueEL.replace("")).isEqualTo("");
    }

    @Test
    void testReplaceNullKeyValue() {
        assertThat(KeyValueEL.replace("${one}", (KeyValue[]) null)).isEqualTo("${one}");
    }

    @Test
    void testReplaceNoVars() {
        assertThat(KeyValueEL.replace("one two three")).isEqualTo("one two three");
    }

    @Test
    void testReplaceSingleVar() {
        assertThat(KeyValueEL.replace("${one}", new KeyValue("one", "1"))).isEqualTo("1");
    }

    @Test
    void testReplaceLiteralTextPreserved() {
        assertThat(KeyValueEL.replace(" ${one} and ${two} ", new KeyValue("one", "1"), new KeyValue("two", "2")))
                .isEqualTo(" 1 and 2 ");
    }

    @Test
    void testReplaceElExpression() {
        // Full EL expression (not possible with the plain KeyValue.replace substitution).
        assertThat(KeyValueEL.replace("${name.toUpperCase()}", new KeyValue("name", "abc"))).isEqualTo("ABC");
    }

    @Test
    void testReplaceWithoutKeyValuesIsNotEvaluated() {
        // Without any key value there is nothing to substitute, so the message is returned as-is instead of
        // being evaluated against beans a previous call may have left on this thread.
        assertThat(KeyValueEL.replace("${unknown}")).isEqualTo("${unknown}");
    }

    @Test
    void testReplaceUnknownVariableReturnsMessageUnreplaced() {
        // Unlike KeyValue.replace (which leaves unknown ${x} untouched), strict EL fails on unknown
        // identifiers. The message is often built inside a "toString()", so the error is logged and the
        // message is returned unreplaced instead of being thrown.
        assertThat(KeyValueEL.replace("${unknown}", new KeyValue("known", "1"))).isEqualTo("${unknown}");
    }

    @Test
    void testReplaceUnknownVariableKeepsKnownOnesUnreplacedAsWell() {
        // The EL engine evaluates the message as a whole, so a single unknown variable makes the known ones
        // stay unreplaced too.
        assertThat(KeyValueEL.replace("${one} ${unknown}", new KeyValue("one", "1"))).isEqualTo("${one} ${unknown}");
    }

    @Test
    void testClearRemovesThreadLocalBeans() {
        // A bean defined by one call stays visible on the same thread (this is exactly the leak clear()
        // prevents): the second call only defines "two", but "one" still resolves.
        assertThat(KeyValueEL.replace("${one}", new KeyValue("one", "1"))).isEqualTo("1");
        assertThat(KeyValueEL.replace("${one} ${two}", new KeyValue("two", "2"))).isEqualTo("1 2");

        // After clear() the processor (and its beans) are gone, so "one" no longer resolves.
        KeyValueEL.clear();
        assertThat(KeyValueEL.replace("${one} ${two}", new KeyValue("two", "2"))).isEqualTo("${one} ${two}");
    }

}
