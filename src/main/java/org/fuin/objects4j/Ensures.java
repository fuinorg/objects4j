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

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// CHECKSTYLE:OFF
/**
 * Documents a postcondition that must always be true just after the execution
 * the annotated section of code.<br>
 * <br>
 * This is intended to be replaced by the corresponding JML Annotation:
 * 
 * {@link http://sourceforge.net/apps/trac/jmlspecs/browser/JMLAnnotations}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Ensures {

    boolean redundantly() default false;

    String value();

}
// CHECKSTYLE:ON
