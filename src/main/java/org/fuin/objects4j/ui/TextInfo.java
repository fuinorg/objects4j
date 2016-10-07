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

import javax.enterprise.inject.Vetoed;

import org.fuin.objects4j.common.Immutable;

/**
 * Stores some text.
 */
@Immutable
@Vetoed
public abstract class TextInfo {

    private final String text;

    /**
     * Constructor with text.
     * 
     * @param text
     *            Text or <code>null</code>.
     */
    public TextInfo(final String text) {
        super();
        this.text = text;
    }

    /**
     * Returns the text.
     * 
     * @return Text or <code>null</code>.
     */
    public final String getText() {
        return text;
    }

}
