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

import java.lang.reflect.Field;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;

/**
 * Base class for runtime exceptions that removes the stack trace and cause while marshalling and unmarshalling using JAX-B.
 */
public abstract class AbstractJaxbMarshallableRuntimeException extends RuntimeException implements ExceptionJaxbMarshallable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "msg")
    private String msg;

    private transient StackTraceElement[] transientStackTrace;

    /**
     * JAX-B constructor.
     */
    protected AbstractJaxbMarshallableRuntimeException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * <p>
     * Note that the detail message associated with {@code cause} is <i>not</i> automatically incorporated in this exception's detail
     * message.
     *
     * @param message
     *            the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt> value is permitted, and
     *            indicates that the cause is nonexistent or unknown.)
     */
    public AbstractJaxbMarshallableRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified detail message. The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message
     *            the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public AbstractJaxbMarshallableRuntimeException(final String message) {
        super(message);
        this.msg = message;
    }

    /**
     * Invoked by Marshaller after it has created an instance of this object.
     * 
     * This method will remove the stack trace before the exception is serialized. This avoids having empty stack trace elements.
     * 
     * @param marshaller
     *            Current marshaller.
     */
    protected final void beforeMarshal(final Marshaller marshaller) {
        this.transientStackTrace = getStackTrace();
        this.msg = getMessage();
        setStackTrace(new StackTraceElement[] {});
    }

    /**
     * Invoked by Marshaller after it has marshalled all properties of this object.
     * 
     * This method will restore the stacktrace after the exception was serialized.
     * 
     * @param marshaller
     *            Current marshaller.
     */
    protected final void afterMarshal(final Marshaller marshaller) {
        setStackTrace(this.transientStackTrace);
    }

    /**
     * This method is called after all the properties (except IDREF) are unmarshalled for this object, but before this object is set to the
     * parent object.
     * 
     * @param unmarshaller
     *            Unmarshaller.
     * @param parent
     *            Parent object.
     */
    protected final void afterUnmarshal(final Unmarshaller unmarshaller, final Object parent) {
        setPrivateField(this, "detailMessage", msg);
        setStackTrace(new StackTraceElement[] {});
    }

    private static void setPrivateField(final Object obj, final String name, final Object value) {
        try {
            final Field field = Throwable.class.getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (final Exception ex) {
            throw new RuntimeException("Failed to set field '" + name + "' in class '" + obj.getClass() + "'", ex);
        }
    }

}
