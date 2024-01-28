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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation to assign preferred table column values to a field. The information may be used by UI elements to configure a table
 * column when the field is displayed as a table column.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TableColumn {

    /**
     * Preferred column width in a table.
     * 
     * @return Width.
     */
    float width();

    /**
     * Unit of the preferred column width.
     * 
     * @return Unit.
     */
    FontSizeUnit unit() default FontSizeUnit.PIXEL;

    /**
     * Preferred column in a table (0..n).
     * 
     * @return Position.
     */
    int pos();

    /**
     * Defines a getter other than "getFieldName".
     * 
     * @return Getter.
     */
    String getter() default "";

}
