/**
 * Copyright (C) 2015 Michael Schnell. All rights reserved. 
 * http://www.fuin.org/
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
 * along with this library. If not, see http://www.gnu.org/licenses/.
 */
package org.fuin.objects4j.ui;

import java.lang.reflect.Field;
import java.util.Locale;

import javax.enterprise.inject.Vetoed;
import javax.validation.constraints.NotNull;

import org.fuin.objects4j.common.Contract;
import javax.annotation.concurrent.Immutable;

/**
 * Information about a text field.
 */
@Immutable
@Vetoed
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
    public TextFieldInfo(@NotNull final Field field, final int width) {
        super();

        Contract.requireArgNotNull("field", field);
        Contract.requireArgNotNull("width", width);

        this.field = field;
        this.width = width;
    }

    /**
     * Returns the field.
     * 
     * @return Field - Never <code>null</code>
     */
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
    public static TextFieldInfo create(@NotNull final Field field, @NotNull final Locale locale) {

        final TextField textField = field.getAnnotation(TextField.class);
        if (textField == null) {
            return null;
        }
        return new TextFieldInfo(field, textField.width());

    }

}
