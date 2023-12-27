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

import java.lang.annotation.*;

/**
 * The class to which this annotation is applied is thread-safe. This means that no sequences of accesses (reads and writes to public
 * fields, calls to public methods) may put the object into an invalid state, regardless of the interleaving of those actions by the
 * runtime, and without requiring any additional synchronization or coordination on the part of the caller.
 * 
 * Copyright (c) 2005 Brian Goetz Released under the Creative Commons Attribution License (http://creativecommons.org/licenses/by/2.5)
 * Official home: http://www.jcip.net
 * 
 * Original source: http://code.google.com/p/jsr-305/
 * 
 * @deprecated Use <code>javax.annotation.concurrent.ThreadSafe</code> from JSR 305 instead (See Maven library
 *             "com.google.code.findbugs:jsr305").
 */
@Documented
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.CLASS)
@Deprecated
public @interface ThreadSafe {
}
