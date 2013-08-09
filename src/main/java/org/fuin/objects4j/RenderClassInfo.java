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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Container for all information that is available to render a field of a class.
 * 
 * @param <T>
 *            Type of the class this render information is for.
 */
@Immutable
public final class RenderClassInfo<T> {

    private final Class<T> clasz;

    private final ClassTextInfo labelClassInfo;

    private final List<RenderFieldInfo> renderFields;

    /**
     * Constructor with class to render.
     * 
     * @param clasz
     *            Class with render information.
     * @param locale
     *            Locale to use.
     */
    @Requires("clasz != null")
    public RenderClassInfo(final Class<T> clasz, final Locale locale) {
        super();
        Contract.requireArgNotNull("clasz", clasz);
        this.clasz = clasz;
        this.labelClassInfo = new AnnotationAnalyzer<Label>(Label.class).createClassInfo(clasz,
                locale);
        this.renderFields = new ArrayList<RenderFieldInfo>();

        final Field[] fields = clasz.getDeclaredFields();
        for (final Field field : fields) {
            renderFields.add(RenderFieldInfo.create(field, locale));
        }

    }

    /**
     * Returns the class the label is for.
     * 
     * @return Class.
     */
    public final Class<?> getClasz() {
        return clasz;
    }

    /**
     * Returns the label info for the class.
     * 
     * @return Information or <code>null</code>.
     */
    public final ClassTextInfo getLabelClassInfo() {
        return labelClassInfo;
    }

    /**
     * Returns a list with all fields that have render information.
     * 
     * @return Unmodifiable list.
     */
    public final List<RenderFieldInfo> getRenderFields() {
        return Collections.unmodifiableList(renderFields);
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + clasz.getName().hashCode();
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
        final RenderClassInfo<?> other = (RenderClassInfo<?>) obj;
        return clasz.getName().equals(other.clasz.getName());
    }

}
