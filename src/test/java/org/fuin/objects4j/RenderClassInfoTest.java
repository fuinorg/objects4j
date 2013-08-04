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

import static org.fest.assertions.Assertions.assertThat;

import java.util.Locale;

import my.test.MyClass;

import org.junit.Test;

//TESTCODE:BEGIN
public class RenderClassInfoTest {

    @Test
    public final void testConstruction() {

        final RenderClassInfo<MyClass> testee = new RenderClassInfo<MyClass>(MyClass.class,
                Locale.ENGLISH);

        assertThat(testee).isNotNull();
        assertThat(testee.getLabelClassInfo()).isNotNull();
        assertThat(testee.getRenderFields()).isNotNull();
        assertThat(testee.getRenderFields().size()).isEqualTo(5);

    }

}
// TESTCODE:END
