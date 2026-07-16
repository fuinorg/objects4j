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

import jakarta.el.ELContext;
import jakarta.el.ELManager;
import jakarta.el.ELProcessor;
import jakarta.el.ValueExpression;
import org.fuin.objects4j.common.ThreadSafe;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Replaces variables in a message using Jakarta Expression Language (EL). This is an EL-powered variant of
 * {@link KeyValue#replace(String, KeyValue...)}: it keeps the same {@code ${NAME}} template syntax (literal text
 * around the expressions is preserved), but resolves each {@code ${...}} through EL, so full expressions like
 * {@code ${customer.name.toUpperCase()}} are supported.
 * <p>
 * An {@link ELProcessor} is relatively expensive to create and is not thread-safe, so this class keeps one instance
 * per thread in a {@link ThreadLocal}. This makes {@link #replace(String, KeyValue...)} safe to call concurrently
 * from multiple threads. In a pooled request/response environment (where the same thread serves many requests) the
 * {@link ThreadLocal} would otherwise retain the beans defined by the last call and leak memory, so {@link #clear()}
 * MUST be called when the thread finishes handling a request (see the Spring/Quarkus integrations in cqrs-4-java).
 */
@ThreadSafe
public final class KeyValueEL {

    private static final Logger LOG = LoggerFactory.getLogger(KeyValueEL.class);

    private static final ThreadLocal<ELProcessor> PROCESSOR = ThreadLocal.withInitial(ELProcessor::new);

    private KeyValueEL() {
        throw new UnsupportedOperationException("It is not allowed to create an instance of a utility class");
    }

    /**
     * Replaces all variables in the format "${NAME}" with the corresponding value using Jakarta EL. NAME is the name
     * of a key from the <code>keyValue</code> array and can be used inside a full EL expression (for example
     * <code>${NAME.toUpperCase()}</code>).
     * <p>
     * Note the behavioural differences to {@link KeyValue#replace(String, KeyValue...)}: because the EL engine
     * evaluates the message as a whole, a reference to an <em>unknown</em> variable makes the whole message fail to
     * render (and not only that single {@code ${NAME}}), and a key with a {@code null} value is undefined in the EL
     * context. This method never throws: the message is often built inside a {@code toString()}, so a failure is
     * logged as an error and the message is returned with its variables unreplaced.
     *
     * @param message  Message to replace. Returned unchanged if {@literal null} or empty.
     * @param keyValue Array of key values. If {@literal null} or empty there is nothing to substitute and the message
     *                 is returned unchanged without being evaluated - beans defined by a previous call on the same
     *                 thread are never applied to it.
     * @return Replaced message, or the message with its variables unreplaced if it could not be rendered.
     */
    @Nullable
    public static String replace(@Nullable final String message, final KeyValue... keyValue) {
        if (message == null || message.isEmpty()) {
            return message;
        }
        if (keyValue == null || keyValue.length == 0) {
            return message;
        }
        final ELProcessor elp = PROCESSOR.get();
        for (final KeyValue kv : keyValue) {
            elp.defineBean(kv.getKey(), kv.getValue());
        }
        final ELContext ctx = elp.getELManager().getELContext();
        final ValueExpression ve = ELManager.getExpressionFactory().createValueExpression(ctx, message, String.class);
        try {
            return ve.getValue(ctx);
        } catch (RuntimeException e) {
            LOG.error("Failed to render '{}'", message, e);
            return message;
        }
    }

    /**
     * Removes the {@link ELProcessor} bound to the current thread. This must be called when a thread finishes
     * handling a request in a pooled request/response scenario to free the retained beans and avoid a memory leak
     * (and to prevent beans defined by a previous request from leaking into a subsequent one on the same thread).
     */
    public static void clear() {
        PROCESSOR.remove();
    }

}
