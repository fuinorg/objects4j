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
package org.fuin.objects4j.common;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Used to express that a method's return value is never <code>null</code> or
 * empty.
 * 
 * @deprecated Use <code>javax.validation.constraints.NotNull</code> from bean
 *             validation instead. It wasn't allowed to use it for a return
 *             value, but it is now.
 */
// CHECKSTYLE:OFF
@Documented
@Target(value = { METHOD })
@Retention(value = RUNTIME)
@Deprecated
public @interface NeverEmpty {
}
// CHECKSTYLE:ON
