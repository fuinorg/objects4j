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

import java.util.Locale;

import my.test.MyClass;

import org.junit.Test;

//TESTCODE:BEGIN
public final class ClassTextInfoTest {

    @Test
    public final void testConstruction() {

        final Class<?> clasz = ClassTextInfo.class;
        final String text = "def";

        final ClassTextInfo labelInfo = new ClassTextInfo(clasz, text);
        assertThat(labelInfo.getClasz()).isSameAs(clasz);
        assertThat(labelInfo.getText()).isEqualTo(text);
    }

    @Test
    public final void testCreateClassLocaleENGLISH() {

        final ClassTextInfo testee = new AnnotationAnalyzer().createClassInfo(MyClass.class,
                Locale.ENGLISH, Label.class);
        assertThat(testee.getClasz()).isSameAs(MyClass.class);
        assertThat(testee.getText()).isEqualTo("MyClass_en title");

    }

    @Test
    public final void testCreateClassLocaleGERMAN() {

        final ClassTextInfo testee = new AnnotationAnalyzer().createClassInfo(MyClass.class,
                Locale.GERMAN, Label.class);
        assertThat(testee.getClasz()).isSameAs(MyClass.class);
        assertThat(testee.getText()).isEqualTo("MyClass_de title");

    }

}
// TESTCODE:END
