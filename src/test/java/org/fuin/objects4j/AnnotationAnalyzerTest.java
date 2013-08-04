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
package org.fuin.objects4j;

import org.junit.Test;
import static org.fest.assertions.Assertions.assertThat;

/**
 * test for {@link AnnotationAnalyzer}.
 */
// CHECKSTYLE:OFF
public class AnnotationAnalyzerTest {

    @Test
    public final void testConstruction() {

        // PREPARE
        final AnnotationAnalyzer<Label> testee = new AnnotationAnalyzer<Label>(Label.class);

        // TEST & VERIFY
        assertThat(testee.getAnnotationClasz()).isSameAs(Label.class);

    }

    // Other methods are indirectly tested by other tests
    
}
//CHECKSTYLE:ON
