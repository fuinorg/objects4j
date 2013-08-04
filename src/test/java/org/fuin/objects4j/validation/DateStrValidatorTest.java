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
package org.fuin.objects4j.validation;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//TESTCODE:BEGIN
public final class DateStrValidatorTest {

    private DateStrValidator testee;

    private DateStr constraintAnnotation;

    @Before
    public final void setUp() {
        testee = new DateStrValidator();
        constraintAnnotation = createMock(DateStr.class);
        expect(constraintAnnotation.value()).andReturn("yyyy-MM-dd");
        replay(constraintAnnotation);
        testee.initialize(constraintAnnotation);
    }

    @After
    public final void tearDown() {
        testee = null;
    }

    @Test
    public final void testIsValidTRUE() {
        assertThat(testee.isValid("2010-12-31", null)).isTrue();
        assertThat(testee.isValid(null, null)).isTrue();

    }

    @Test
    public final void testIsValidFALSE() {

        assertThat(testee.isValid("", null)).isFalse();
        assertThat(testee.isValid("2010-12-00", null)).isFalse();
        assertThat(testee.isValid("2010-12-", null)).isFalse();
        assertThat(testee.isValid("2010-", null)).isFalse();
        assertThat(testee.isValid("2010", null)).isFalse();
        assertThat(testee.isValid("31-12-2010", null)).isFalse();
        assertThat(testee.isValid("12-31-2010", null)).isFalse();
        assertThat(testee.isValid("whatever", null)).isFalse();

    }

}
// TESTCODE:END
