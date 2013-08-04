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

import java.lang.reflect.Field;
import java.util.Locale;

import my.test.MyClass;
import my.test.MyPasswordContainer;

import org.junit.Test;

//TESTCODE:BEGIN
public class RenderFieldInfoTest {

    @Test
    public final void testConstruction() throws Exception {

        final Field field = MyClass.class.getDeclaredField("lastName");
        final FieldTextInfo labelFieldInfo = new FieldTextInfo(field, null);
        final TextFieldInfo textFieldInfo = new TextFieldInfo(field, 0);
        final FontSize width = new FontSize(12, FontSizeUnit.PIXEL);
        final String getter = "getAbc";
        final TableColumnInfo tableColumnInfo = new TableColumnInfo(field, null, null, 0, width,
                getter);

        final RenderFieldInfo testee = new RenderFieldInfo(field, labelFieldInfo, textFieldInfo,
                tableColumnInfo);

        assertThat(testee.getField()).isSameAs(field);
        assertThat(testee.getLabelFieldInfo()).isSameAs(labelFieldInfo);
        assertThat(testee.getTextFieldInfo()).isSameAs(textFieldInfo);
        assertThat(testee.getTableColumnInfo()).isSameAs(tableColumnInfo);

    }

    @Test
    public final void testCreateFieldLocale() throws Exception {

        final Field field = MyClass.class.getDeclaredField("lastName");
        final RenderFieldInfo testee = RenderFieldInfo.create(field, Locale.ENGLISH);

        assertThat(testee.getField()).isNotNull();
        assertThat(testee.getField()).isSameAs(field);

        assertThat(testee.getLabelFieldInfo()).isNotNull();
        assertThat(testee.getLabelFieldInfo().getField()).isSameAs(field);

        assertThat(testee.getTableColumnInfo()).isNotNull();
        assertThat(testee.getTableColumnInfo().getField()).isSameAs(field);

        assertThat(testee.getTextFieldInfo()).isNotNull();
        assertThat(testee.getTextFieldInfo().getField()).isSameAs(field);

    }

    @Test
    public final void testIsPasswordField() throws Exception {

        assertThat(
                RenderFieldInfo.create(MyPasswordContainer.class.getDeclaredField("password"),
                        Locale.ENGLISH).isPasswordField()).isTrue();

        assertThat(
                RenderFieldInfo.create(MyPasswordContainer.class.getDeclaredField("user"),
                        Locale.ENGLISH).isPasswordField()).isFalse();

    }

    @Test
    public final void testIsRequired() throws Exception {

        assertThat(
                RenderFieldInfo.create(MyPasswordContainer.class.getDeclaredField("password"),
                        Locale.ENGLISH).isRequired()).isTrue();

        assertThat(
                RenderFieldInfo.create(MyPasswordContainer.class.getDeclaredField("comment"),
                        Locale.ENGLISH).isRequired()).isTrue();

    }

    @Test
    public final void testGetLabelText() throws Exception {

        assertThat(
                RenderFieldInfo.create(MyPasswordContainer.class.getDeclaredField("user"),
                        Locale.ENGLISH).getLabelText()).isEqualTo("User name");

        assertThat(
                RenderFieldInfo.create(MyPasswordContainer.class.getDeclaredField("password"),
                        Locale.ENGLISH).getLabelText()).isEqualTo("password");

    }

    @Test
    public final void testGetMinLength() throws Exception {

        assertThat(
                RenderFieldInfo.create(MyPasswordContainer.class.getDeclaredField("user"),
                        Locale.ENGLISH).getMinLength()).isEqualTo(3);

        assertThat(
                RenderFieldInfo.create(MyPasswordContainer.class.getDeclaredField("password"),
                        Locale.ENGLISH).getMinLength()).isEqualTo(8);

        assertThat(
                RenderFieldInfo.create(MyPasswordContainer.class.getDeclaredField("comment"),
                        Locale.ENGLISH).getMinLength()).isEqualTo(0);

    }

    @Test
    public final void testGetMaxLength() throws Exception {

        assertThat(
                RenderFieldInfo.create(MyPasswordContainer.class.getDeclaredField("user"),
                        Locale.ENGLISH).getMaxLength()).isEqualTo(20);

        assertThat(
                RenderFieldInfo.create(MyPasswordContainer.class.getDeclaredField("password"),
                        Locale.ENGLISH).getMaxLength()).isEqualTo(20);

        assertThat(
                RenderFieldInfo.create(MyPasswordContainer.class.getDeclaredField("comment"),
                        Locale.ENGLISH).getMaxLength()).isEqualTo(200);

    }

    public final void testFindClassAnnotationArray() {
        // Implicitly tested with "testGetMinLength()" & "testGetMaxLength()"
    }

    public final void testFindClass() {
        // Implicitly tested with "testGetMinLength()" & "testGetMaxLength()"
    }

}
// TESTCODE:END
