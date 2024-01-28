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
package org.fuin.objects4j.jaxb;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.fuin.objects4j.common.ThreadSafe;
import org.fuin.objects4j.core.CurrencyStrValidator;

import java.util.Currency;

/**
 * Converts a {@link Currency}.
 */
@ThreadSafe
public final class CurrencyConverter extends XmlAdapter<String, Currency> {

    /**
     * Verifies that the given value can be converted into a value object using the factory. A <code>null</code> parameter will return
     * {@literal true}.
     *
     * @param value Value to check.
     * @return {@literal true} if the value can be converted, else {@literal false}.
     */
    public final boolean isValid(final String value) {
        return CurrencyStrValidator.isValid(value);
    }

    /**
     * Converts the base type into an value object. A <code>null</code> parameter will return <code>null</code>.
     *
     * @param value Representation of the value object as base type.
     * @return Value object.
     */
    public final Currency toVO(final String value) {
        if (value == null) {
            return null;
        }
        return Currency.getInstance(value);
    }

    /**
     * Converts the value object into an base type. A <code>null</code> parameter will return <code>null</code>.
     *
     * @param value Value object.
     * @return Base type.
     */
    public final String fromVO(final Currency value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    @Override
    public final String marshal(final Currency value) throws Exception {
        return fromVO(value);
    }

    @Override
    public final Currency unmarshal(final String value) throws Exception {
        return toVO(value);
    }

}
