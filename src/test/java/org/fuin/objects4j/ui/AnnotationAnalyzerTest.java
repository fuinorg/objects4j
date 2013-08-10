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
package org.fuin.objects4j.ui;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

/**
 * Test for {@link AnnotationAnalyzer}.
 */
// CHECKSTYLE:OFF
public class AnnotationAnalyzerTest {

    // Other methods are indirectly tested by other tests

    @Test
    public void testGetMethodSignature() {

        // TEST & VERIFY
        assertThat(AnnotationAnalyzer.getMethodSignature(void.class, "doIt", null)).isEqualTo(
                "void doIt()");
        assertThat(AnnotationAnalyzer.getMethodSignature(void.class, "doIt", new Class[] {}))
                .isEqualTo("void doIt()");
        assertThat(
                AnnotationAnalyzer
                        .getMethodSignature(void.class, "doIt", new Class[] { int.class }))
                .isEqualTo("void doIt(int)");
        assertThat(
                AnnotationAnalyzer.getMethodSignature(void.class, "doIt", new Class[] { int.class,
                        Boolean.class })).isEqualTo("void doIt(int, Boolean)");
        assertThat(
                AnnotationAnalyzer.getMethodSignature(String.class, "getX",
                        new Class[] { Integer.class })).isEqualTo("String getX(Integer)");

    }

}
// CHECKSTYLE:ON
