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

import my.test.MyClass;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public final class ClassTextInfoTest {

    @Test
    void testConstruction() {

        final Class<?> clasz = ClassTextInfo.class;
        final String text = "def";

        final ClassTextInfo labelInfo = new ClassTextInfo(clasz, text);
        Assertions.assertThat(labelInfo.getClasz()).isSameAs(clasz);
        Assertions.assertThat(labelInfo.getText()).isEqualTo(text);
    }

    @Test
    void testCreateClassLocaleENGLISH() {

        final AnnotationAnalyzer annotationAnalyzer = new AnnotationAnalyzer();

        ClassTextInfo testee = annotationAnalyzer.createClassInfo(MyClass.class, Locale.ENGLISH, Label.class);
        Assertions.assertThat(testee.getClasz()).isSameAs(MyClass.class);
        Assertions.assertThat(testee.getText()).isEqualTo("MyClass_en label");

        testee = annotationAnalyzer.createClassInfo(MyClass.class, Locale.ENGLISH, ShortLabel.class);
        Assertions.assertThat(testee.getClasz()).isSameAs(MyClass.class);
        Assertions.assertThat(testee.getText()).isEqualTo("MyClass_en shortLabel");

    }

    @Test
    void testCreateClassLocaleGERMAN() {

        final AnnotationAnalyzer annotationAnalyzer = new AnnotationAnalyzer();

        ClassTextInfo testee = annotationAnalyzer.createClassInfo(MyClass.class, Locale.GERMAN, Label.class);
        Assertions.assertThat(testee.getClasz()).isSameAs(MyClass.class);
        Assertions.assertThat(testee.getText()).isEqualTo("MyClass_de label");

        testee = annotationAnalyzer.createClassInfo(MyClass.class, Locale.GERMAN, ShortLabel.class);
        Assertions.assertThat(testee.getClasz()).isSameAs(MyClass.class);
        Assertions.assertThat(testee.getText()).isEqualTo("MyClass_de shortLabel");

    }

    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(ClassTextInfo.class).suppress(Warning.NULL_FIELDS, Warning.ALL_FIELDS_SHOULD_BE_USED).verify();
    }

}
