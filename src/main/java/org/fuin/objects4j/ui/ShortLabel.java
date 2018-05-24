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
 * Use this annotation to assign a short label to a type of object or an object's attribute. The label may be used by UI elements to display
 * an appropriate text for the field.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
public @interface ShortLabel {

    /**
     * Default label text. This is only used if no entry in the resource bundle can be found.
     */
    String value() default "";

    /**
     * Resource name and path. If this is empty the resource bundle is expected to be in the same package and has the same name as the class
     * of the field annotated.
     */
    String bundle() default "";

    /**
     * Key within the resource bundle. If this is empty the field name itself is used as the key.
     */
    String key() default "";

}
