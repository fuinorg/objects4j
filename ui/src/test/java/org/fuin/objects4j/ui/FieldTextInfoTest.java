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

import my.test.A;
import my.test.AbstractA;
import my.test.AbstractB;
import my.test.B;
import my.test.C;
import my.test.D;
import my.test.E;
import my.test.F;
import my.test.G;
import my.test.H;
import my.test.I;
import my.test.MyClass;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

public final class FieldTextInfoTest {

    @Test
    void testConstruction() throws Exception {

        final Field field = MyClass.class.getDeclaredField("birthday");
        final String text = "def";
        final FieldTextInfo testee = new FieldTextInfo(field, text);

        Assertions.assertThat(testee.getField()).isSameAs(field);
        Assertions.assertThat(testee.getText()).isEqualTo(text);
        Assertions.assertThat(testee.getTextOrField()).isEqualTo(text);

    }

    @Test
    void testCreateClassLocale() throws Exception {

        final List<FieldTextInfo> list = new AnnotationAnalyzer().createFieldInfos(MyClass.class, Locale.ENGLISH, Label.class);

        final FieldTextInfo infoFirstName = new FieldTextInfo(MyClass.class.getDeclaredField("firstName"), "First name");
        final FieldTextInfo infoLastName = new FieldTextInfo(MyClass.class.getDeclaredField("lastName"), "Last name");
        final FieldTextInfo infoBirthday = new FieldTextInfo(MyClass.class.getDeclaredField("birthday"), "MyBundle_en birthday");
        final FieldTextInfo infoPermanent = new FieldTextInfo(MyClass.class.getDeclaredField("permanentEmployee"), "Permanent employee");

        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(4);
        Assertions.assertThat(list).contains(infoFirstName, infoLastName, infoBirthday, infoPermanent);
        assertEquals(infoFirstName, list.get(list.indexOf(infoFirstName)), "infoFirstName");
        assertEquals(infoLastName, list.get(list.indexOf(infoLastName)), "infoLastName");
        assertEquals(infoBirthday, list.get(list.indexOf(infoBirthday)), "infoBirthday");
        assertEquals(infoPermanent, list.get(list.indexOf(infoPermanent)), "infoPermanent");

    }

    @Test
    void testCreateClassStringLocale() throws Exception {

        final FieldTextInfo testee = new AnnotationAnalyzer().createFieldInfo(MyClass.class.getDeclaredField("birthday"), Locale.ENGLISH,
                Label.class);
        final FieldTextInfo infoBirthday = new FieldTextInfo(MyClass.class.getDeclaredField("birthday"), "MyBundle_en birthday");
        assertEquals(infoBirthday, testee, "infoBirthday");

    }

    @Test
    void testCreateFieldLocale() throws Exception {

        final Field field = MyClass.class.getDeclaredField("birthday");
        field.setAccessible(true);

        final FieldTextInfo testee = new AnnotationAnalyzer().createFieldInfo(field, Locale.ENGLISH, Label.class);
        final FieldTextInfo infoBirthday = new FieldTextInfo(field, "MyBundle_en birthday");
        assertEquals(infoBirthday, testee, "infoBirthday");

    }

    @Test
    void testNoValues() throws Exception {

        final List<FieldTextInfo> list = new AnnotationAnalyzer().createFieldInfos(A.class, Locale.ENGLISH, Label.class);
        final FieldTextInfo infoX = new FieldTextInfo(AbstractA.class.getDeclaredField("x"), null);
        final FieldTextInfo infoY = new FieldTextInfo(A.class.getDeclaredField("y"), null);

        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list).contains(infoX, infoY);
        assertEquals(infoX, list.get(list.indexOf(infoX)), "infoX");
        assertEquals(infoY, list.get(list.indexOf(infoY)), "infoY");

    }

    @Test
    void testNoValuesWithProperties() throws Exception {

        final List<FieldTextInfo> list = new AnnotationAnalyzer().createFieldInfos(B.class, Locale.ENGLISH, Label.class);
        final FieldTextInfo infoX = new FieldTextInfo(AbstractB.class.getDeclaredField("x"), "AbstractB_en x.Label");
        final FieldTextInfo infoY = new FieldTextInfo(B.class.getDeclaredField("y"), "B_en y.Label");

        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list).contains(infoX, infoY);
        Assertions.assertThat(list.get(list.indexOf(infoX)).getText()).isEqualTo(infoX.getText());
        Assertions.assertThat(list.get(list.indexOf(infoY)).getText()).isEqualTo(infoY.getText());

    }

    @Test
    void testDefaultValue() throws Exception {

        final List<FieldTextInfo> list = new AnnotationAnalyzer().createFieldInfos(C.class, Locale.ENGLISH, Label.class);
        final FieldTextInfo info = new FieldTextInfo(C.class.getDeclaredField("c"), "Ccc");
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list).contains(info);
        assertEquals(info, list.get(list.indexOf(info)), "info");

    }

    @Test
    void testDefaultValueWithProperties() throws Exception {

        final List<FieldTextInfo> list = new AnnotationAnalyzer().createFieldInfos(D.class, Locale.ENGLISH, Label.class);
        final FieldTextInfo info = new FieldTextInfo(D.class.getDeclaredField("d2"), "DEFAULT D.d2");
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list).contains(info);
        Assertions.assertThat(list.get(list.indexOf(info)).getText()).isEqualTo(info.getText());

    }

    @Test
    void testDefaultValueAndKey() throws Exception {

        final List<FieldTextInfo> list = new AnnotationAnalyzer().createFieldInfos(E.class, Locale.ENGLISH, Label.class);
        final FieldTextInfo info = new FieldTextInfo(E.class.getDeclaredField("e"), "Eee");
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list).contains(info);
        assertEquals(info, list.get(list.indexOf(info)), "info");

    }

    @Test
    void testDefaultValueAndKeyWithProperties() throws Exception {

        final List<FieldTextInfo> list = new AnnotationAnalyzer().createFieldInfos(F.class, Locale.ENGLISH, Label.class);
        final FieldTextInfo info = new FieldTextInfo(F.class.getDeclaredField("f"), "Fff");
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list).contains(info);
        assertEquals(info, list.get(list.indexOf(info)), "info");

    }

    @Test
    void testShortTextAndKey() throws Exception {

        final List<FieldTextInfo> list = new AnnotationAnalyzer().createFieldInfos(G.class, Locale.ENGLISH, ShortLabel.class);
        final FieldTextInfo info = new FieldTextInfo(G.class.getDeclaredField("g"), "Ggg");
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list).contains(info);
        assertEquals(info, list.get(list.indexOf(info)), "info");

    }

    @Test
    void testShortTextAndKeyWithProperties() throws Exception {

        final List<FieldTextInfo> list = new AnnotationAnalyzer().createFieldInfos(H.class, Locale.ENGLISH, ShortLabel.class);
        final FieldTextInfo info = new FieldTextInfo(H.class.getDeclaredField("h"), "H_en hh_short");
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list).contains(info);
        assertEquals(info, list.get(list.indexOf(info)), "info");

    }

    @Test
    void testShortWithProperties() throws Exception {

        final List<FieldTextInfo> list = new AnnotationAnalyzer().createFieldInfos(I.class, Locale.ENGLISH, ShortLabel.class);
        final FieldTextInfo info = new FieldTextInfo(I.class.getDeclaredField("i"), "I_en i.ShortLabel");
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list).contains(info);
        assertEquals(info, list.get(list.indexOf(info)), "info");

    }

    private void assertEquals(final FieldTextInfo expected, final FieldTextInfo actual, final String descr) {
        Assertions.assertThat(actual.getField()).describedAs(descr).isEqualTo(expected.getField());
        Assertions.assertThat(actual.getText()).describedAs(descr).isEqualTo(expected.getText());
        Assertions.assertThat(actual.getTextOrField()).isEqualTo(expected.getTextOrField());
    }

    @Test
    public void testEqualsHashCode() throws Exception {
        EqualsVerifier.forClass(FieldTextInfo.class)
                .withPrefabValues(Field.class, C.class.getDeclaredField("c"), D.class.getDeclaredField("d2"))
                .suppress(Warning.NULL_FIELDS, Warning.ALL_FIELDS_SHOULD_BE_USED, Warning.REFERENCE_EQUALITY).verify();
    }

}
