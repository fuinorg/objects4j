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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.Ensures;
import org.fuin.objects4j.common.Immutable;
import org.fuin.objects4j.common.Requires;

/**
 * Table column information for a field of a class.
 */
@Immutable
public final class TableColumnInfo implements Comparable<TableColumnInfo> {

    private final Field field;

    private final String text;

    private final String shortText;

    private final FontSize width;

    private final int pos;

    private final String getter;

    /**
     * Constructor with all data.
     * 
     * @param field
     *            The field.
     * @param text
     *            Text.
     * @param shortText
     *            Abbreviation of the text.
     * @param pos
     *            Position of the column (starting with zero).
     * @param width
     *            Witdh of the column.
     * @param getter
     *            Name of the getter for the field.
     */
    @Requires("(field != null) && (width != null) && (getter!=null)")
    public TableColumnInfo(final Field field, final String text, final String shortText,
            final int pos, final FontSize width, final String getter) {
        super();

        Contract.requireArgNotNull("field", field);
        Contract.requireArgNotNull("width", width);
        Contract.requireArgNotNull("getter", getter);

        this.field = field;
        this.text = text;
        this.shortText = shortText;
        this.pos = pos;
        this.width = width;
        this.getter = getter;
    }

    /**
     * Returns the field.
     * 
     * @return Field.
     */
    @Ensures("\result != null")
    public final Field getField() {
        return field;
    }

    /**
     * Returns the text of the label.
     * 
     * @return Long text.
     */
    public final String getText() {
        return text;
    }

    /**
     * Returns the abbreviation of the text.
     * 
     * @return Short text.
     */
    public final String getShortText() {
        return shortText;
    }

    /**
     * Returns the column width.
     * 
     * @return Width of the column.
     */
    public final FontSize getWidth() {
        return width;
    }

    /**
     * The position of the column.
     * 
     * @return Position of the column (starting with zero).
     */
    public final int getPos() {
        return pos;
    }

    /**
     * The name of the getter for the table column field.
     * 
     * @return Getter name.
     */
    @Ensures("\result != null")
    public final String getGetter() {
        return getter;
    }

    @Override
    public final int compareTo(final TableColumnInfo theOther) {
        if (pos > theOther.pos) {
            return 1;
        } else if (pos < theOther.pos) {
            return -1;
        }
        return 0;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + field.hashCode();
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TableColumnInfo other = (TableColumnInfo) obj;
        return field.equals(other.field);
    }

    /**
     * Return a list of table column informations for the given class.
     * 
     * @param clasz
     *            Class to check for <code>@TableColumn</code> and
     *            <code>@Label</code> annotations.
     * @param locale
     *            Locale to use.
     * 
     * @return List of table columns sorted by the position.
     */
    @Requires("(clasz != null) && (locale!=null)")
    public static List<TableColumnInfo> create(final Class<?> clasz, final Locale locale) {

        Contract.requireArgNotNull("clasz", clasz);
        Contract.requireArgNotNull("locale", locale);

        final List<TableColumnInfo> list = new ArrayList<TableColumnInfo>();
        final Field[] fields = clasz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            final TableColumnInfo tableColumnInfo = create(fields[i], locale);
            if (tableColumnInfo != null) {
                list.add(tableColumnInfo);
            }
        }
        Collections.sort(list);
        return list;
    }

    /**
     * Return the table column information for a given field.
     * 
     * @param field
     *            Field to check for <code>@TableColumn</code> and
     *            <code>@Label</code> annotations.
     * @param locale
     *            Locale to use.
     * 
     * @return Information or <code>null</code>.
     */
    @Requires("(field != null) && (locale!=null)")
    public static TableColumnInfo create(final Field field, final Locale locale) {

        Contract.requireArgNotNull("field", field);
        Contract.requireArgNotNull("locale", locale);

        final TableColumn tableColumn = field.getAnnotation(TableColumn.class);
        if (tableColumn == null) {
            return null;
        }
        final AnnotationAnalyzer analyzer = new AnnotationAnalyzer();
        final FieldTextInfo labelInfo = analyzer.createFieldInfo(field, locale, Label.class);
        final FieldTextInfo shortLabelInfo = analyzer.createFieldInfo(field, locale,
                ShortLabel.class);
        final int pos = tableColumn.pos();
        final FontSize fontSize = new FontSize(tableColumn.width(), tableColumn.unit());
        final String getter = getGetter(tableColumn, field.getName());

        final String labelText;
        if (labelInfo == null) {
            labelText = null;
        } else {
            labelText = labelInfo.getTextOrField();
        }

        final String shortLabelText;
        if (shortLabelInfo == null) {
            shortLabelText = null;
        } else {
            shortLabelText = shortLabelInfo.getText();
        }

        return new TableColumnInfo(field, labelText, shortLabelText, pos, fontSize, getter);

    }

    /**
     * Returns the getter for the given field. If
     * <code>tableColumn.getter()</code> is empty <code>getXxx()</code> is
     * returned ("Xxx" is <code>fieldName</code> with first character upper
     * case).
     * 
     * @param tableColumn
     *            Table column information for the field.
     * @param fieldName
     *            Name of the field.
     * 
     * @return Getter for the field.
     */
    private static String getGetter(final TableColumn tableColumn, final String fieldName) {
        if (tableColumn.getter().equals("")) {
            return "get" + firstCharUpper(fieldName);
        }
        return tableColumn.getter();
    }

    /**
     * Make the first character of the given string upper case.
     * 
     * @param str
     *            Text.
     * 
     * @return Text with first character upper case.
     */
    private static String firstCharUpper(final String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return str;
        }
        if (str.length() == 1) {
            return "" + Character.toUpperCase(str.charAt(0));
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

}
