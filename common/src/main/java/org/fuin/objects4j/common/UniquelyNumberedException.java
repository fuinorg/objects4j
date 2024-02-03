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

import jakarta.validation.constraints.NotNull;

import java.io.Serial;

/**
 * Exception that provides a number for the exception that is unique in the context.
 */
public abstract class UniquelyNumberedException extends Exception implements UniquelyNumbered {

    @Serial
    private static final long serialVersionUID = 1L;

    private final long number;

    /**
     * Constructs a new exception with the specified detail message.
     * 
     * @param number
     *            Number that identifies this exception uniquely in the context.
     * @param message
     *            The detail message.
     */
    public UniquelyNumberedException(final long number, @NotNull final String message) {
        super(message);
        this.number = number;
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * 
     * @param number
     *            Number that identifies this exception uniquely in the context.
     * @param message
     *            The detail message.
     * @param cause
     *            The cause .
     */
    public UniquelyNumberedException(final long number, @NotNull final String message, @NotNull final Throwable cause) {
        super(message, cause);
        this.number = number;
    }

    /**
     * Constructs a new exception with the specified cause and a detail message.
     * 
     * @param number
     *            Number that identifies this exception uniquely in the context.
     * @param cause
     *            The cause.
     */
    public UniquelyNumberedException(final long number, @NotNull final Throwable cause) {
        super(cause);
        this.number = number;
    }

    @Override
    public final long getUniqueNumber() {
        return number;
    }

}
