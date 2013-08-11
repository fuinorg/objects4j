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

import javax.validation.constraints.NotNull;

import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.Immutable;

/**
 * Stores some text associated with a class.
 */
@Immutable
public final class ClassTextInfo extends TextInfo {

    private final Class<?> clasz;

    /**
     * Constructor with class and text.
     * 
     * @param clasz
     *            Class the text belongs to.
     * @param text
     *            Text or <code>null</code>.
     */
    public ClassTextInfo(@NotNull final Class<?> clasz, final String text) {
        super(text);
        Contract.requireArgNotNull("clasz", clasz);
        this.clasz = clasz;
    }

    /**
     * Returns the class the text belongs to.
     * 
     * @return Class - Never <code>null</code>.
     */
    public final Class<?> getClasz() {
        return clasz;
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
        final ClassTextInfo other = (ClassTextInfo) obj;
        return clasz.getName().equals(other.clasz.getName());
    }

    @Override
    public final String toString() {
        return clasz.getName();
    }

}
