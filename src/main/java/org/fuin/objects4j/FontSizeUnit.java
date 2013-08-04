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

/**
 * Unit of a given font size. 1em = 12pt = 16px = 100%
 */
public enum FontSizeUnit {

    /**
     * Traditionally used in print media - One point is equal to 1/72 of an
     * inch.
     */
    POINT,

    /** Fixed-size units that are used in screen media. */
    PIXEL,

    /** Scalable unit that is used in web document media. */
    EM,

    /** The current font-size is equal to 100% (i.e. 12pt = 100%). */
    PERCENT

}
