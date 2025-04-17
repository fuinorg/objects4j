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

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.fuin.objects4j.common.AsStringCapable;
import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.HasPublicStaticIsValidMethod;
import org.fuin.objects4j.common.HasPublicStaticValueOfMethod;
import org.fuin.objects4j.common.ValueObjectWithBaseType;
import org.fuin.objects4j.ui.Label;
import org.fuin.objects4j.ui.ShortLabel;

import javax.annotation.concurrent.Immutable;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Currency;

/**
 * Amount of a currency.
 */
@Immutable
@ShortLabel("Amount")
@Label("Amount of currency")
@HasPublicStaticIsValidMethod
@HasPublicStaticValueOfMethod(method = "valueOf", param = String.class)
public final class CurrencyAmount implements ValueObjectWithBaseType<String>, Comparable<CurrencyAmount>, Serializable, AsStringCapable {

    @Serial
    private static final long serialVersionUID = 1000L;

    private static final String DECIMAL = "((\\+|-)?([0-9]+(\\.?[0-9]+)))";

    private static final String INTEGER = "((\\+|-)?[0-9]+)";

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Currency currency;

    /** Used to store the string representation, if computed. */
    private transient String stringCache;

    /**
     * Protected default constructor for deserialization.
     */
    protected CurrencyAmount() { // NOSONAR Ignore JAXB default constructor
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
    public CurrencyAmount(@NotNull final BigDecimal amount, @NotNull final Currency currency) {
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
    public CurrencyAmount(@NotNull final String amount, @NotNull final Currency currency) {
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
    public CurrencyAmount(@NotNull final String amount, @NotNull final String currencyCode) {
        this(strToAmount(amount), Currency.getInstance(currencyCode));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + amount.hashCode();
        result = prime * result + currency.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
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
        return currency.getCurrencyCode().equals(other.currency.getCurrencyCode());
    }

    @Override
    public int compareTo(final CurrencyAmount other) {
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
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Returns the currency part.
     *
     * @return Currency.
     */
    @NotNull
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public Class<String> getBaseType() {
        return String.class;
    }

    @Override
    public String asBaseType() {
        if (stringCache == null) {
            stringCache = amountToStr(amount) + " " + currency.getCurrencyCode();
        }
        return stringCache;
    }

    @Override
    public String asString() {
        return asBaseType();
    }

    @Override
    public String toString() {
        return asBaseType();
    }

    /**
     * Converts a big decimal into a canonical string representation. A {@literal null} argument will return {@literal null}.
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
     * Converts an amount from its canonical string representation back into a big decimal. A {@literal null} argument will return
     * {@literal null}.
     *
     * @param amount
     *            Amount string to convert.
     *
     * @return String as big decimal.
     */
    public static BigDecimal strToAmount(@CurrencyAmountStr final String amount) {
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


    /**
     * Check that a given string is a well-formed currency amount.
     *
     * @param value
     *            Value to check.
     *
     * @return Returns {@literal true} if it's a valid currency amount else {@literal false} is returned.
     */
    public static boolean isValid(final String value) {
        if (value == null) {
            return true;
        }
        if (value.isEmpty()) {
            return false;
        }
        final int space = value.indexOf(' ');
        if (space == -1) {
            return false;
        }

        // Try converting amount
        final String amount = value.substring(0, space);
        if (!(amount.matches(DECIMAL) || amount.matches(INTEGER))) {
            return false;
        }

        // Try to convert code into a currency
        final String currencyCode = value.substring(space + 1);
        try {
            Currency.getInstance(currencyCode);
        } catch (RuntimeException ex) {
            return false;
        }

        return true;
    }

    @Nullable
    public static CurrencyAmount valueOf(@Nullable @CurrencyAmountStr final String value) {
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

    /**
     * Checks if the argument is a valid currency amount and throws an exception if this is not the case.
     *
     * @param name
     *            Name of the value for a possible error message.
     * @param value
     *            Value to check.
     *
     * @throws ConstraintViolationException
     *             The value was not valid.
     */
    // CHECKSTYLE:OFF:RedundantThrows
    public static void requireArgValid(@NotNull final String name, @NotNull final String value) throws ConstraintViolationException {
        // CHECKSTYLE:ON

        if (!isValid(value)) {
            throw new ConstraintViolationException("The argument '" + name + "' is not valid: '" + value + "'");
        }

    }

    private static String repeat(final int count, final char ch) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }

}
