/**
 * Copyright (C) 2015 Michael Schnell. All rights reserved.
 * http://www.fuin.org/
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see http://www.gnu.org/licenses/.
 */
package org.fuin.objects4j.core;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.fuin.objects4j.common.ThreadSafe;

/**
 * Provides the Jakarta Bean Validation {@link Validator} used to verify the constraints of a value object. It allows a
 * generated value object to run the validators of its constraints without creating a {@link ValidatorFactory} on every
 * call.
 * <p>
 * The {@link ValidatorFactory} is expensive to create and is shared by all threads. The {@link Validator} itself is
 * kept in a {@link ThreadLocal}, so {@link #clear()} can release it when a thread finishes handling a request. In a
 * pooled request/response environment (where the same thread serves many requests) {@link #clear()} MUST be called
 * when the thread finishes handling a request (see the Spring/Quarkus integrations in cqrs-4-java).
 * <p>
 * Requires an implementation of the Jakarta Bean Validation API (like Hibernate Validator) on the classpath.
 */
@ThreadSafe
public final class Validators {

    private static final ValidatorFactory FACTORY = Validation.buildDefaultValidatorFactory();

    private static final ThreadLocal<Validator> VALIDATOR = ThreadLocal.withInitial(FACTORY::getValidator);

    private Validators() {
        throw new UnsupportedOperationException("It is not allowed to create an instance of a utility class");
    }

    /**
     * Returns the validator bound to the current thread. A new one is created if the thread has none yet.
     *
     * @return Validator of the current thread.
     */
    public static Validator get() {
        return VALIDATOR.get();
    }

    /**
     * Removes the {@link Validator} bound to the current thread. This must be called when a thread finishes handling a
     * request in a pooled request/response scenario to free it.
     */
    public static void clear() {
        VALIDATOR.remove();
    }

}
