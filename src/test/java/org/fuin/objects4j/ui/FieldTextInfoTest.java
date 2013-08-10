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

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

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

import org.junit.Test;

//TESTCODE:BEGIN
public final class FieldTextInfoTest {

    @Test
    public final void testConstruction() throws Exception {

        final Field field = MyClass.class.getDeclaredField("birthday");
        final String text = "def";
        final FieldTextInfo testee = new FieldTextInfo(field, text);

        assertThat(testee.getField()).isSameAs(field);
        assertThat(testee.getText()).isEqualTo(text);
        assertThat(testee.getTextOrField()).isEqualTo(text);

    }

    @Test
    public final void testCreateClassLocale() throws Exception {

        final List<FieldTextInfo> list = new AnnotationAnalyzer().createFieldInfos(MyClass.class,
                Locale.ENGLISH, Label.class);

        final FieldTextInfo infoFirstName = new FieldTextInfo(
                MyClass.class.getDeclaredField("firstName"), "First name");
        final FieldTextInfo infoLastName = new FieldTextInfo(
                MyClass.class.getDeclaredField("lastName"), "Last name");
        final FieldTextInfo infoBirthday = new FieldTextInfo(
                MyClass.class.getDeclaredField("birthday"), "MyBundle_en birthday");
        final FieldTextInfo infoPermanent = new FieldTextInfo(
                MyClass.class.getDeclaredField("permanentEmployee"), "Permanent employee");

        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(4);
        assertThat(list).contains(infoFirstName, infoLastName, infoBirthday, infoPermanent);
        assertEquals(infoFirstName, list.get(list.indexOf(infoFirstName)), "infoFirstName");
        assertEquals(infoLastName, list.get(list.indexOf(infoLastName)), "infoLastName");
        assertEquals(infoBirthday, list.get(list.indexOf(infoBirthday)), "infoBirthday");
        assertEquals(infoPermanent, list.get(list.indexOf(infoPermanent)), "infoPermanent");

    }

    @Test
    public final void testCreateClassStringLocale() throws Exception {

        final FieldTextInfo testee = new AnnotationAnalyzer().createFieldInfo(
                MyClass.class.getDeclaredField("birthday"), Locale.ENGLISH, Label.class);
        final FieldTextInfo infoBirthday = new FieldTextInfo(
                MyClass.class.getDeclaredField("birthday"), "MyBundle_en birthday");
        assertEquals(infoBirthday, testee, "infoBirthday");

    }

    @Test
    public final void testCreateFieldLocale() throws Exception {

        final Field field = MyClass.class.getDeclaredField("birthday");
        field.setAccessible(true);

        final FieldTextInfo testee = new AnnotationAnalyzer().createFieldInfo(field,
                Locale.ENGLISH, Label.class);
        final FieldTextInfo infoBirthday = new FieldTextInfo(field, "MyBundle_en birthday");
        assertEquals(infoBirthday, testee, "infoBirthday");

    }

    @Test
    public final void testNoValues() throws Exception {

        final List<FieldTextInfo> list = new AnnotationAnalyzer().createFieldInfos(A.class,
                Locale.ENGLISH, Label.class);
        final FieldTextInfo infoX = new FieldTextInfo(AbstractA.class.getDeclaredField("x"), null);
        final FieldTextInfo infoY = new FieldTextInfo(A.class.getDeclaredField("y"), null);

        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(2);
        assertThat(list).contains(infoX, infoY);
        assertEquals(infoX, list.get(list.indexOf(infoX)), "infoX");
        assertEquals(infoY, list.get(list.indexOf(infoY)), "infoY");

    }

    @Test
    public final void testNoValuesWithProperties() throws Exception {

        final List<FieldTextInfo> list = new AnnotationAnalyzer().createFieldInfos(B.class,
                Locale.ENGLISH, Label.class);
        final FieldTextInfo infoX = new FieldTextInfo(AbstractB.class.getDeclaredField("x"),
                "AbstractB_en x.Label");
        final FieldTextInfo infoY = new FieldTextInfo(B.class.getDeclaredField("y"), "B_en y.Label");

        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(2);
        assertThat(list).contains(infoX, infoY);
        assertThat(list.get(list.indexOf(infoX)).getText()).isEqualTo(infoX.getText());
        assertThat(list.get(list.indexOf(infoY)).getText()).isEqualTo(infoY.getText());

    }

    @Test
    public final void testDefaultValue() throws Exception {

        final List<FieldTextInfo> list = new AnnotationAnalyzer().createFieldInfos(C.class,
                Locale.ENGLISH, Label.class);
        final FieldTextInfo info = new FieldTextInfo(C.class.getDeclaredField("c"), "Ccc");
        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list).contains(info);
        assertEquals(info, list.get(list.indexOf(info)), "info");

    }

    @Test
    public final void testDefaultValueWithProperties() throws Exception {

        final List<FieldTextInfo> list = new AnnotationAnalyzer().createFieldInfos(D.class,
                Locale.ENGLISH, Label.class);
        final FieldTextInfo info = new FieldTextInfo(D.class.getDeclaredField("d2"), "DEFAULT D.d2");
        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(2);
        assertThat(list).contains(info);
        assertThat(list.get(list.indexOf(info)).getText()).isEqualTo(info.getText());

    }

    @Test
    public final void testDefaultValueAndKey() throws Exception {

        final List<FieldTextInfo> list = new AnnotationAnalyzer().createFieldInfos(E.class,
                Locale.ENGLISH, Label.class);
        final FieldTextInfo info = new FieldTextInfo(E.class.getDeclaredField("e"), "Eee");
        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list).contains(info);
        assertEquals(info, list.get(list.indexOf(info)), "info");

    }

    @Test
    public final void testDefaultValueAndKeyWithProperties() throws Exception {

        final List<FieldTextInfo> list = new AnnotationAnalyzer().createFieldInfos(F.class,
                Locale.ENGLISH, Label.class);
        final FieldTextInfo info = new FieldTextInfo(F.class.getDeclaredField("f"), "Fff");
        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list).contains(info);
        assertEquals(info, list.get(list.indexOf(info)), "info");

    }

    @Test
    public final void testShortTextAndKey() throws Exception {

        final List<FieldTextInfo> list = new AnnotationAnalyzer().createFieldInfos(G.class,
                Locale.ENGLISH, ShortLabel.class);
        final FieldTextInfo info = new FieldTextInfo(G.class.getDeclaredField("g"), "Ggg");
        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list).contains(info);
        assertEquals(info, list.get(list.indexOf(info)), "info");

    }

    @Test
    public final void testShortTextAndKeyWithProperties() throws Exception {

        final List<FieldTextInfo> list = new AnnotationAnalyzer().createFieldInfos(H.class,
                Locale.ENGLISH, ShortLabel.class);
        final FieldTextInfo info = new FieldTextInfo(H.class.getDeclaredField("h"), "H_en hh_short");
        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list).contains(info);
        assertEquals(info, list.get(list.indexOf(info)), "info");

    }

    @Test
    public final void testShortWithProperties() throws Exception {

        final List<FieldTextInfo> list = new AnnotationAnalyzer().createFieldInfos(I.class,
                Locale.ENGLISH, ShortLabel.class);
        final FieldTextInfo info = new FieldTextInfo(I.class.getDeclaredField("i"),
                "I_en i.ShortLabel");
        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list).contains(info);
        assertEquals(info, list.get(list.indexOf(info)), "info");

    }

    private void assertEquals(final FieldTextInfo expected, final FieldTextInfo actual,
            final String descr) {
        assertThat(actual.getField()).describedAs(descr).isEqualTo(expected.getField());
        assertThat(actual.getText()).describedAs(descr).isEqualTo(expected.getText());
        assertThat(actual.getTextOrField()).isEqualTo(expected.getTextOrField());
    }

}
// TESTCODE:END
