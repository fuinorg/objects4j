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

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Currency;

import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.ui.Label;
import org.fuin.objects4j.ui.ShortLabel;

/**
 * Amount of a currency.
 */
@Immutable
@ShortLabel("Amount")
@Label("Amount of currency")
@XmlJavaTypeAdapter(CurrencyAmountConverter.class)
public final class CurrencyAmount implements ValueObjectWithBaseType<String>,
        Comparable<CurrencyAmount>, Serializable {

    private static final long serialVersionUID = 1000L;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Currency currency;

    /** Used to store the string representation, if computed. */
    private transient String stringCache;

    /**
     * Protected default constructor for deserialization.
     */
    protected CurrencyAmount() {
        super();
    }

    /**
     * Constructor with all data.
     * 
     * @param amount
     *            Amount.
     * @param currency
     *            Currency.
     */
    public CurrencyAmount(@NotNull final BigDecimal amount,
            @NotNull final Currency currency) {
        super();
        Contract.requireArgNotNull("amount", amount);
        Contract.requireArgNotNull("currency", currency);
        this.amount = amount;
        this.currency = currency;
    }

    /**
     * Constructor with string amount.
     * 
     * @param amount
     *            Amount.
     * @param currency
     *            Currency.
     */
    public CurrencyAmount(@NotNull final String amount,
            @NotNull final Currency currency) {
        this(strToAmount(amount), currency);
    }

    /**
     * Constructor with string amount and currency code.
     * 
     * @param amount
     *            Amount.
     * @param currencyCode
     *            ISO 4217 code of the currency.
     */
    public CurrencyAmount(@NotNull final String amount,
            @NotNull final String currencyCode) {
        this(strToAmount(amount), Currency.getInstance(currencyCode));
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + amount.hashCode();
        result = prime * result + currency.hashCode();
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CurrencyAmount other = (CurrencyAmount) obj;
        if (!amount.equals(other.amount)) {
            return false;
        }
        if (!currency.getCurrencyCode()
                .equals(other.currency.getCurrencyCode())) {
            return false;
        }
        return true;
    }

    @Override
    public final int compareTo(final CurrencyAmount other) {
        final String thisCode = currency.getCurrencyCode();
        final String otherCode = other.currency.getCurrencyCode();
        int c = thisCode.compareTo(otherCode);
        if (c > 0) {
            return c;
        }
        if (c < 0) {
            return c;
        }
        c = this.amount.compareTo(other.amount);
        if (c > 0) {
            return c;
        }
        if (c < 0) {
            return c;
        }
        return 0;
    }

    /**
     * Returns the amount part.
     * 
     * @return Amount.
     */
    @NotNull
    public final BigDecimal getAmount() {
        return amount;
    }

    /**
     * Returns the currency part.
     * 
     * @return Currency.
     */
    @NotNull
    public final Currency getCurrency() {
        return currency;
    }

    @Override
    public final Class<String> getBaseType() {
        return String.class;
    }

    @Override
    public final String asBaseType() {
        if (stringCache == null) {
            stringCache = amountToStr(amount) + " "
                    + currency.getCurrencyCode();
        }
        return stringCache;
    }

    @Override
    public final String toString() {
        return asBaseType();
    }

    /**
     * Converts a big decimal into a canonical string representation. A
     * <code>null</code> argument will return <code>null</code>.
     * 
     * @param amount
     *            Amount to convert.
     * 
     * @return Amount as string.
     */
    public static String amountToStr(final BigDecimal amount) {
        if (amount == null) {
            return null;
        }
        final BigInteger unscaled = amount.unscaledValue();
        if (unscaled.equals(BigInteger.ZERO)) {
            return "0." + repeat(amount.scale(), '0');
        }
        final String unscaledStr = unscaled.toString();
        if (amount.scale() == 0) {
            return unscaledStr;
        }
        final int p = unscaledStr.length() - amount.scale();
        if (p == 0) {
            return "0." + unscaledStr;
        }
        return unscaledStr.substring(0, p) + "." + unscaledStr.substring(p);
    }

    /**
     * Converts an amount from it's canonical string representation back into a
     * big decimal. A <code>null</code> argument will return <code>null</code>.
     * 
     * @param amount
     *            Amount string to convert.
     * 
     * @return String as big decimal.
     */
    public static BigDecimal strToAmount(
            @CurrencyAmountStr final String amount) {
        if (amount == null) {
            return null;
        }
        final int dot = amount.indexOf('.');
        final int scale;
        final String unscaledStr;
        if (dot == -1) {
            scale = 0;
            unscaledStr = amount;
        } else {
            scale = amount.length() - dot - 1;
            unscaledStr = amount.substring(0, dot) + amount.substring(dot + 1);
        }
        final BigInteger unscaled = new BigInteger(unscaledStr);
        return new BigDecimal(unscaled, scale);
    }

    private static String repeat(final int count, final char ch) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }

}
