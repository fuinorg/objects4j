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
 * The class to which this annotation is applied is immutable <em>after</em> it has been fully populated by a marshalling, deserialization
 * or persistence framework (such as JAXB, JSON-B, Jackson or JPA) through its no-argument constructor. Such a class typically has non-final
 * fields that the framework writes by reflection after construction, but it exposes no setters and performs no further mutation once
 * populated (in particular, no lazy-initializing getters that change state on first read).
 *
 * This is a weaker statement than {@link Immutable}: because the state is established after the constructor returns and the fields are not
 * {@code final}, the Java Memory Model's final-field publication guarantee does not apply. An instance is therefore thread-safe (it behaves
 * like an immutable value) <em>only if it is safely published</em> - i.e. handed to other threads through a mechanism that establishes a
 * happens-before relationship (a {@code final} or {@code volatile} field, a concurrent collection, synchronization, container injection,
 * etc.). It must not be shared via a data race.
 *
 * Do not use this annotation for types that mutate on read (lazy caches) or that expose setters or other mutators after population; those
 * are {@link NotThreadSafe}. For types whose complete state is established within the constructor use {@link Immutable} instead.
 *
 * See also {@link Immutable}, {@link NotThreadSafe}, {@link ThreadSafe} and {@link ThreadSafetyUndefined}.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface ImmutableAfterUnmarshal {
}
