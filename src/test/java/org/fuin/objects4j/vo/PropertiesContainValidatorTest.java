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

import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import java.util.Properties;
import java.util.StringTokenizer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//TESTCODE:BEGIN
public final class PropertiesContainValidatorTest {

    private PropertiesContainValidator testee;

    private PropertiesContain constraintAnnotation;

    @Before
    public final void setUp() {
        testee = new PropertiesContainValidator();
        final String[] expected = new String[] { "b", "c" };
        constraintAnnotation = createMock(PropertiesContain.class);
        expect(constraintAnnotation.value()).andReturn(expected);
        replay(constraintAnnotation);
        testee.initialize(constraintAnnotation);
    }

    @After
    public final void tearDown() {
        testee = null;
        constraintAnnotation = null;
    }

    @Test
    public final void testIsValidTRUE() {

        assertThat(testee.isValid(createProperties("b=1,c=2"), null)).isTrue();
        assertThat(testee.isValid(createProperties("a=1,b=2,c=3,d=3"), null)).isTrue();
        assertThat(testee.isValid(createProperties("b=2,c=3,d=3"), null)).isTrue();
        assertThat(testee.isValid(null, null)).isTrue();

    }

    @Test
    public final void testIsValidFALSE() {

        assertThat(testee.isValid(createProperties("c=1,d=2"), null)).isFalse();
        assertThat(testee.isValid(createProperties("c=1"), null)).isFalse();
        assertThat(testee.isValid(createProperties(""), null)).isFalse();

    }

    private Properties createProperties(final String keyValues) {
        final Properties props = new Properties();
        final StringTokenizer tok = new StringTokenizer(keyValues, ",");
        while (tok.hasMoreTokens()) {
            final String keyValue = tok.nextToken();
            final StringTokenizer tokEq = new StringTokenizer(keyValue, "=");
            props.put(tokEq.nextToken(), tokEq.nextToken());
        }
        return props;
    }

}
// TESTCODE:END
