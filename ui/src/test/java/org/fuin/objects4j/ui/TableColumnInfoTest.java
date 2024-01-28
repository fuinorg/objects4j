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

import my.test.C;
import my.test.D;
import my.test.MyClass;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

public final class TableColumnInfoTest {

    @Test
    void testConstrcution() throws Exception {

        final Field field = MyClass.class.getDeclaredField("lastName");
        final String text = "def";
        final String shortText = "ghi";
        final String tooltipText = "jkl";
        final int pos = 2;
        final FontSize width = new FontSize(12, FontSizeUnit.POINT);
        final String getter = "getAbc";

        final TableColumnInfo testee = new TableColumnInfo(field, text, shortText, tooltipText, pos, width, getter);

        Assertions.assertThat(testee.getField()).isSameAs(field);
        Assertions.assertThat(testee.getText()).isEqualTo(text);
        Assertions.assertThat(testee.getShortText()).isEqualTo(shortText);
        Assertions.assertThat(testee.getTooltip()).isEqualTo(tooltipText);
        Assertions.assertThat(testee.getPos()).isEqualTo(pos);
        Assertions.assertThat(testee.getWidth()).isSameAs(width);
        Assertions.assertThat(testee.getGetter()).isEqualTo(getter);

    }

    @Test
    void testCreateClassLocaleENGLISH() throws Exception {

        final List<TableColumnInfo> list = TableColumnInfo.create(MyClass.class, Locale.ENGLISH);

        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(4);

        final TableColumnInfo tcFirstName = new TableColumnInfo(MyClass.class.getDeclaredField("firstName"), "First name", null, 0,
                new FontSize(80, FontSizeUnit.PIXEL), "getFirstName");
        final TableColumnInfo tcLastName = new TableColumnInfo(MyClass.class.getDeclaredField("lastName"), "Last name", null, 1,
                new FontSize(100, FontSizeUnit.POINT), "getLastName");
        final TableColumnInfo tcBirthday = new TableColumnInfo(MyClass.class.getDeclaredField("birthday"), "MyBundle_en birthday",
                "MyBundle_en birthday.short", 2, new FontSize(40, FontSizeUnit.PIXEL), "getBirthday");
        final TableColumnInfo tcPermanent = new TableColumnInfo(MyClass.class.getDeclaredField("permanentEmployee"), "Permanent employee",
                null, 3, new FontSize(20, FontSizeUnit.PIXEL), "isPermanentEmployee");

        Assertions.assertThat(list).contains(tcFirstName, tcLastName, tcBirthday, tcPermanent);

        assertEquals(tcFirstName, list.get(list.indexOf(tcFirstName)), "tcFirstName");
        assertEquals(tcLastName, list.get(list.indexOf(tcLastName)), "tcLastName");
        assertEquals(tcBirthday, list.get(list.indexOf(tcBirthday)), "tcBirthday");
        assertEquals(tcPermanent, list.get(list.indexOf(tcPermanent)), "tcPermanent");

    }

    @Test
    void testCreateClassLocaleGERMAN() throws Exception {

        final List<TableColumnInfo> list = TableColumnInfo.create(MyClass.class, Locale.GERMAN);

        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(4);

        final TableColumnInfo tcFirstName = new TableColumnInfo(MyClass.class.getDeclaredField("firstName"), "First name", null, 0,
                new FontSize(80, FontSizeUnit.PIXEL), "getFirstName");
        final TableColumnInfo tcLastName = new TableColumnInfo(MyClass.class.getDeclaredField("lastName"), "Last name", null, 1,
                new FontSize(100, FontSizeUnit.POINT), "getLastName");
        final TableColumnInfo tcBirthday = new TableColumnInfo(MyClass.class.getDeclaredField("birthday"), "MyBundle_de birthday",
                "MyBundle_de birthday.short", 2, new FontSize(40, FontSizeUnit.PIXEL), "getBirthday");
        final TableColumnInfo tcPermanent = new TableColumnInfo(MyClass.class.getDeclaredField("permanentEmployee"), "Permanent employee",
                null, 3, new FontSize(20, FontSizeUnit.PIXEL), "isPermanentEmployee");

        Assertions.assertThat(list).contains(tcFirstName, tcLastName, tcBirthday, tcPermanent);

        assertEquals(tcFirstName, list.get(list.indexOf(tcFirstName)), "tcFirstName");
        assertEquals(tcLastName, list.get(list.indexOf(tcLastName)), "tcLastName");
        assertEquals(tcBirthday, list.get(list.indexOf(tcBirthday)), "tcBirthday");
        assertEquals(tcPermanent, list.get(list.indexOf(tcPermanent)), "tcPermanent");

    }

    @Test
    void testCreateFieldLocaleENGLISH() throws Exception {

        final Field field = MyClass.class.getDeclaredField("birthday");
        final TableColumnInfo testee = TableColumnInfo.create(field, Locale.ENGLISH);

        final TableColumnInfo tcBirthday = new TableColumnInfo(field, "MyBundle_en birthday", "MyBundle_en birthday.short", 2,
                new FontSize(40, FontSizeUnit.PIXEL), "getBirthday");

        assertEquals(tcBirthday, testee, "tcBirthday");

    }

    @Test
    void testCreateFieldLocaleGERMAN() throws Exception {

        final Field field = MyClass.class.getDeclaredField("birthday");
        final TableColumnInfo testee = TableColumnInfo.create(field, Locale.GERMAN);

        final TableColumnInfo tcBirthday = new TableColumnInfo(field, "MyBundle_de birthday", "MyBundle_de birthday.short", 2,
                new FontSize(40, FontSizeUnit.PIXEL), "getBirthday");

        assertEquals(tcBirthday, testee, "tcBirthday");

    }

    private void assertEquals(final TableColumnInfo expected, final TableColumnInfo actual, final String descr) {
        Assertions.assertThat(actual.getField()).describedAs(descr).isEqualTo(expected.getField());
        Assertions.assertThat(actual.getGetter()).describedAs(descr).isEqualTo(expected.getGetter());
        Assertions.assertThat(actual.getPos()).describedAs(descr).isEqualTo(expected.getPos());
        Assertions.assertThat(actual.getShortText()).describedAs(descr).isEqualTo(expected.getShortText());
        Assertions.assertThat(actual.getText()).describedAs(descr).isEqualTo(expected.getText());
        Assertions.assertThat(actual.getWidth()).describedAs(descr).isEqualTo(expected.getWidth());
    }

    @Test
    public void testEqualsHashCode() throws Exception {
        EqualsVerifier.forClass(TableColumnInfo.class)
                .withPrefabValues(Field.class, C.class.getDeclaredField("c"), D.class.getDeclaredField("d2"))
                .withPrefabValues(FontSize.class, new FontSize(40, FontSizeUnit.PIXEL), new FontSize(100, FontSizeUnit.POINT))
                .suppress(Warning.NULL_FIELDS, Warning.ALL_FIELDS_SHOULD_BE_USED, Warning.REFERENCE_EQUALITY).verify();
    }

}
