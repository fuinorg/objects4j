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

import java.lang.reflect.Field;
import java.util.Locale;

/**
 * Information about a text field.
 */
@Immutable
public class TextFieldInfo {

    private final Field field;

    private final int width;

    /**
     * Constructor with all data.
     * 
     * @param field
     *            The field.
     * @param width
     *            Number of characters to be shown.
     */
    @Requires("(field != null) && (width != null)")
    public TextFieldInfo(final Field field, final int width) {
        super();

        Contract.requireArgNotNull("field", field);
        Contract.requireArgNotNull("width", width);

        this.field = field;
        this.width = width;
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
     * Returns the width of the text field.
     * 
     * @return Number of characters.
     */
    public final int getWidth() {
        return width;
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
        final TextFieldInfo other = (TextFieldInfo) obj;
        return field.equals(other.field);
    }

    /**
     * Return the text field information for a given field.
     * 
     * @param field
     *            Field to check for <code>@TextField</code> annotation.
     * @param locale
     *            Locale to use.
     * 
     * @return Information or <code>null</code>.
     */
    @Requires("(field != null) && (locale!=null)")
    public static TextFieldInfo create(final Field field, final Locale locale) {

        final TextField textField = field.getAnnotation(TextField.class);
        if (textField == null) {
            return null;
        }
        return new TextFieldInfo(field, textField.width());

    }

}
