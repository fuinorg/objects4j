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
package org.fuin.objects4j.vo;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.fuin.objects4j.common.ThreadSafe;

import java.util.Currency;

/**
 * Converts a {@link CurrencyAmount}.
 */
@ThreadSafe
@Converter(autoApply = true)
public final class CurrencyAmountConverter extends AbstractValueObjectConverter<String, CurrencyAmount>
        implements AttributeConverter<CurrencyAmount, String> {

    @Override
    public final Class<String> getBaseTypeClass() {
        return String.class;
    }

    @Override
    public final Class<CurrencyAmount> getValueObjectClass() {
        return CurrencyAmount.class;
    }

    @Override
    public final boolean isValid(final String value) {
        return CurrencyAmountStrValidator.isValid(value);
    }

    @Override
    public final CurrencyAmount toVO(final String value) {
        if (value == null) {
            return null;
        }
        final int p = value.indexOf(' ');
        if (p == -1) {
            throw new IllegalArgumentException("No space character found in '" + value + "'");
        }
        final String amountStr = value.substring(0, p);
        final String currencyCode = value.substring(p + 1);
        final Currency currency = Currency.getInstance(currencyCode);
        return new CurrencyAmount(amountStr, currency);
    }

    @Override
    public final String fromVO(final CurrencyAmount value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

}
