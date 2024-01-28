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
package org.fuin.objects4j.jsonb;

import jakarta.json.bind.adapter.JsonbAdapter;
import org.fuin.objects4j.common.ThreadSafe;
import org.fuin.objects4j.core.CurrencyAmount;

import java.util.Currency;

/**
 * Converts a {@link CurrencyAmount} from/to string.
 */
@ThreadSafe
public final class CurrencyAmountConverter implements JsonbAdapter<CurrencyAmount, String> {

    @Override
    public String adaptToJson(final CurrencyAmount obj) throws Exception {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    @Override
    public CurrencyAmount adaptFromJson(final String str) throws Exception {
        if (str == null) {
            return null;
        }
        final int p = str.indexOf(' ');
        if (p == -1) {
            throw new IllegalArgumentException("No space character found in '" + str + "'");
        }
        final String amountStr = str.substring(0, p);
        final String currencyCode = str.substring(p + 1);
        final Currency currency = Currency.getInstance(currencyCode);
        return new CurrencyAmount(amountStr, currency);
    }

}
