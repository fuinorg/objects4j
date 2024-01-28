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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The class to which this annotation is applied is not thread-safe. This annotation primarily exists for clarifying the non-thread-safety
 * of a class that might otherwise be assumed to be thread-safe, despite the fact that it is a bad idea to assume a class is thread-safe
 * without good reason.
 * 
 * Copyright (c) 2005 Brian Goetz Released under the Creative Commons Attribution License (http://creativecommons.org/licenses/by/2.5)
 * Official home: http://www.jcip.net
 * 
 * Original source: http://code.google.com/p/jsr-305/
 * 
 * @see ThreadSafe
 * 
 * @deprecated Use <code>javax.annotation.concurrent.NotThreadSafe</code> from JSR 305 instead (See Maven library
 *             "com.google.code.findbugs:jsr305"). As everything is now in "jakarta" namespace, it is OK again to use
 *             the "javax.annotation" package without having problems with "split package" errors.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
@Deprecated
public @interface NotThreadSafe {
}
