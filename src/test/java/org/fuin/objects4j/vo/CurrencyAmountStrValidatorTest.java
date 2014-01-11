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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// CHECKSTYLE:OFF
public final class CurrencyAmountStrValidatorTest {

    private CurrencyAmountStrValidator testee;

    @Before
    public final void setUp() {
        testee = new CurrencyAmountStrValidator();
    }

    @After
    public final void tearDown() {
        testee = null;
    }

    @Test
    public final void testIsValid() {

        assertThat(testee.isValid(null, null)).isTrue();
        assertThat(testee.isValid("0 EUR", null)).isTrue();
        assertThat(testee.isValid("-1 EUR", null)).isTrue();
        assertThat(testee.isValid("1234.56 EUR", null)).isTrue();
        assertThat(testee.isValid("-1234.56 EUR", null)).isTrue();
        assertThat(testee.isValid("-1234.56 EUR", null)).isTrue();

        assertThat(testee.isValid("", null)).isFalse();
        assertThat(testee.isValid("- EUR", null)).isFalse();
        assertThat(testee.isValid("0", null)).isFalse();
        assertThat(testee.isValid("0.0", null)).isFalse();
        assertThat(testee.isValid("EUR", null)).isFalse();
        assertThat(testee.isValid("1234,56 EUR", null)).isFalse();
        assertThat(testee.isValid("1,234.56 EUR", null)).isFalse();

    }

}
// CHECKSTYLE:ON
