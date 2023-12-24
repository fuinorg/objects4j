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

import jakarta.validation.constraints.NotNull;
import org.fuin.objects4j.common.Contract;

import java.lang.reflect.Field;

/**
 * Stores some text associated with a field.
 */
public final class FieldTextInfo extends TextInfo {

    private final Field field;

    /**
     * Constructor with field, text and abbreviation.
     * 
     * @param field
     *            The field.
     * @param text
     *            Text.
     */
    public FieldTextInfo(@NotNull final Field field, final String text) {
        super(text);
        Contract.requireArgNotNull("field", field);
        this.field = field;
    }

    /**
     * Returns the field.
     * 
     * @return Field - Never <code>null</code>.
     */
    public final Field getField() {
        return field;
    }

    /**
     * Returns the text of the label or the name of the field if the text is <code>null</code>.
     * 
     * @return Long text or field name - Never <code>null</code>.
     */
    public final String getTextOrField() {
        final String text = getText();
        if (text == null) {
            return field.getName();
        }
        return text;
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
        final FieldTextInfo other = (FieldTextInfo) obj;
        return field.equals(other.field);
    }

    @Override
    public final String toString() {
        return field.getName();
    }

}
