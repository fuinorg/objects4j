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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.fuin.objects4j.validation.PasswordStr;

/**
 * Container for all information that is available to render a field of a class.
 */
@Immutable
public final class RenderFieldInfo {

    private final Field field;

    private final FieldTextInfo labelFieldInfo;

    private final TextFieldInfo textFieldInfo;

    private final TableColumnInfo tableColumnInfo;

    /**
     * Constructor with all data.
     * 
     * @param field
     *            The field.
     * @param labelFieldInfo
     *            Label information.
     * @param textFieldInfo
     *            Text field information.
     * @param tableColumnInfo
     *            Table column information.
     */
    @Requires("field != null")
    public RenderFieldInfo(final Field field, final FieldTextInfo labelFieldInfo,
            final TextFieldInfo textFieldInfo, final TableColumnInfo tableColumnInfo) {
        super();
        Contract.requireArgNotNull("field", field);
        this.field = field;
        this.labelFieldInfo = labelFieldInfo;
        this.textFieldInfo = textFieldInfo;
        this.tableColumnInfo = tableColumnInfo;
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
     * Returns the label information.
     * 
     * @return Information or <code>null</code>.
     */
    public final FieldTextInfo getLabelFieldInfo() {
        return labelFieldInfo;
    }

    /**
     * Returns the text field information.
     * 
     * @return Information or <code>null</code>.
     */
    public final TextFieldInfo getTextFieldInfo() {
        return textFieldInfo;
    }

    /**
     * Returns the table column information.
     * 
     * @return Information or <code>null</code>.
     */
    public final TableColumnInfo getTableColumnInfo() {
        return tableColumnInfo;
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
        final RenderFieldInfo other = (RenderFieldInfo) obj;
        return field.equals(other.field);
    }

    /**
     * Returns if this is a password field.
     * 
     * @return If a {@link PasswordStr} annotation is present <code>true</code>,
     *         else <code>false</code>.
     */
    public final boolean isPasswordField() {
        return field.getAnnotation(PasswordStr.class) != null;
    }

    /**
     * Returns if this is a reuired field.
     * 
     * @return If a {@link NotNull} annotation is present <code>true</code>,
     *         else <code>false</code>.
     */
    public final boolean isRequired() {
        return true;
    }

    /**
     * Returns the label text.
     * 
     * @return Label text or name of the field.
     */
    @Ensures("\result != null")
    public final String getLabelText() {
        if (labelFieldInfo == null) {
            return field.getName();
        }
        return labelFieldInfo.getTextOrField();
    }

    /**
     * Returns the minimum length of the field.
     * 
     * @return Min length or <code>null</code> if undefined.
     */
    public final Long getMinLength() {
        final Min min = find(Min.class);
        if (min == null) {
            final Size size = find(Size.class);
            if (size == null) {
                return null;
            }
            return Long.valueOf(size.min());
        }
        return min.value();
    }

    /**
     * Returns the maximum length of the field.
     * 
     * @return Max length or <code>null</code> if undefined.
     */
    public final Long getMaxLength() {
        final Max max = find(Max.class);
        if (max == null) {
            final Size size = find(Size.class);
            if (size == null) {
                return null;
            }
            return Long.valueOf(size.max());
        }
        return max.value();
    }

    /**
     * Tries to find an annotation of a given type on this field. The search is
     * recursive - Annotations that have annotations are also checked.
     * 
     * @param <A>
     *            Type of the annotation.
     * @param type
     *            Annotation class.
     * 
     * @return Annotation found or <code>null</code>.
     */
    public final <A extends Annotation> A find(final Class<A> type) {
        return find(type, field.getAnnotations(), new ArrayList<Class<? extends Annotation>>());
    }

    /**
     * Tries to find an annotation of a given type within a list of annotations.
     * The search is recursive - Annotations that have annotations are also
     * checked.
     * 
     * @param <A>
     *            Type of the annotation to find.
     * @param type
     *            Class of annotation to find.
     * @param annotations
     *            Array of annotations to search.
     * @param alreadyProcessed
     *            List of already processed annotations to avoid endless
     *            looping.
     * 
     * @return Annotation found or <code>null</code>.
     */
    @SuppressWarnings("unchecked")
    public static <A extends Annotation> A find(final Class<A> type,
            final Annotation[] annotations, final List<Class<? extends Annotation>> alreadyProcessed) {

        for (final Annotation annotation : annotations) {
            final Class<? extends Annotation> foundType = annotation.annotationType();
            if (alreadyProcessed.indexOf(foundType) == -1) {
                alreadyProcessed.add(foundType);
                if (annotation.annotationType().equals(type)) {
                    return (A) annotation;
                } else {
                    final A found = find(type, annotation.annotationType().getAnnotations(),
                            alreadyProcessed);
                    if (found != null) {
                        return found;
                    }
                }
            }
        }
        return null;

    }

    /**
     * Creates the render information for a given field.
     * 
     * @param field
     *            Field to inspect.
     * @param locale
     *            Locale to use.
     * 
     * @return Information or <code>null</code>.
     */
    @Ensures("\result != null")
    public static RenderFieldInfo create(final Field field, final Locale locale) {

        final FieldTextInfo labelFieldInfo = new AnnotationAnalyzer<Label>(Label.class)
                .createFieldInfo(field, locale);
        final TextFieldInfo textFieldInfo = TextFieldInfo.create(field, locale);
        final TableColumnInfo tableColumnInfo = TableColumnInfo.create(field, locale);
        return new RenderFieldInfo(field, labelFieldInfo, textFieldInfo, tableColumnInfo);

    }

}
