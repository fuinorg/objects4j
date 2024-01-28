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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The field or method to which this annotation is applied can only be accessed when holding a particular lock, which may be a built-in
 * (synchronization) lock, or may be an explicit java.util.concurrent.Lock.
 * 
 * The argument determines which lock guards the annotated field or method: this : The string literal "this" means that this field is
 * guarded by the class in which it is defined. class-name.this : For inner classes, it may be necessary to disambiguate 'this'; the
 * class-name.this designation allows you to specify which 'this' reference is intended itself : For reference fields only; the object to
 * which the field refers. field-name : The lock object is referenced by the (instance or static) field specified by field-name.
 * class-name.field-name : The lock object is reference by the static field specified by class-name.field-name. method-name() : The lock
 * object is returned by calling the named nil-ary method. class-name.class : The Class object for the specified class should be used as the
 * lock object.
 * 
 * Copyright (c) 2005 Brian Goetz Released under the Creative Commons Attribution License (http://creativecommons.org/licenses/by/2.5)
 * Official home: http://www.jcip.net
 * 
 * Original source: http://code.google.com/p/jsr-305/
 * 
 * @deprecated Use <code>javax.annotation.concurrent.GuardedBy</code> from JSR 305 instead (See Maven library
 *             "com.google.code.findbugs:jsr305"). As everything is now in "jakarta" namespace, it is OK again to use
 *  *             the "javax.annotation" package without having problems with "split package" errors.
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.CLASS)
@Deprecated
public @interface GuardedBy {

    /** 
     * Determines which lock guards the annotated field or method.
     * 
     * @return The lock that should be held.  
     */
    String value();

}
