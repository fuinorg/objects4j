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

import jakarta.annotation.Nullable;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serial;
import java.util.Collections;
import java.util.Set;

/**
 * The contract that was checked is violated.
 */
public final class ConstraintViolationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("squid:S1948") // Cannot fix as external interface, but Hibernate is serializable
    private final Set<ConstraintViolation<Object>> constraintViolations;

    /**
     * Constructor with error message.
     * 
     * @param message
     *            Message.
     */
    public ConstraintViolationException(@NotEmpty final String message) {
        super(message);
        this.constraintViolations = null;
    }

    /**
     * Constructor with error message and constraint violations.
     * 
     * @param message
     *            Message.
     * @param constraintViolations
     *            Constraint violations.
     */
    public ConstraintViolationException(@NotEmpty final String message, @Nullable final Set<ConstraintViolation<Object>> constraintViolations) {
        super(message);
        this.constraintViolations = constraintViolations;
    }

    /**
     * Returns the constraint violations.
     * 
     * @return Immutable set of constraint violations or {@literal null} if only a message is available.
     */
    @SuppressWarnings("squid:S1168") // Won't fix. Needs to be backward compatible.
    public final Set<ConstraintViolation<Object>> getConstraintViolations() {
        if (constraintViolations == null) {
            return null;
        }
        return Collections.unmodifiableSet(constraintViolations);
    }

}
