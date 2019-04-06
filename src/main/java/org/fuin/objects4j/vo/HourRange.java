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

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.ui.Label;
import org.fuin.objects4j.ui.Prompt;
import org.fuin.objects4j.ui.ShortLabel;
import org.fuin.objects4j.ui.Tooltip;

/**
 * Represents a range of hours of a day (24 hourRanges representation).<br>
 * <br>
 * Examples:
 * <ul>
 * <li>'00:00-24:00' Full day</li>
 * <li>'09:00-17:00' From 9 am to 5 pm</li>
 * <li>'06:00-18:00' From 6 in the morning to 6 in the evening</li>
 * <li>'12:00-24:00' From noon to midnight</li>
 * <li>'17:00-03:00' From 5 pm (late afternoon) to 3 am (early morning)</li>
 * </ul>
 */
@Immutable
@ShortLabel("HH-HH")
@Label("From-To Hour")
@Tooltip("From hourRange of day until hourRange of day")
@Prompt("00:00-24:00")
@XmlJavaTypeAdapter(HourRangeConverter.class)
public final class HourRange extends AbstractStringValueObject {

    private static final long serialVersionUID = 1000L;

    @NotEmpty
    private Hour from;

    @NotEmpty
    private Hour to;

    /**
     * Protected default constructor for deserialization.
     */
    protected HourRange() {// NOSONAR Ignore JAXB default constructor
        super();
    }

    /**
     * Constructor with hour range FROM-TO string.
     * 
     * @param hourRange
     *            Hour like '00:00-24:00' (24 hours).
     */
    public HourRange(@NotNull @HourStr final String hourRange) {
        super();
        Contract.requireArgNotEmpty("hourRange", hourRange);
        requireArgValid("hourRange", hourRange);
        from = new Hour(hourRange.substring(0, 5));
        to = new Hour(hourRange.substring(6));
    }

    /**
     * Constructor with hour range FROM-TO objects.
     * 
     * @param from
     *            From hour.
     * @param to
     *            To hour.
     */
    public HourRange(@NotNull final Hour from, @NotNull final Hour to) {
        super();
        Contract.requireArgNotNull("from", from);
        Contract.requireArgNotNull("to", to);
        this.from = from;
        this.to = to;
    }
    
    @Override
    @NotEmpty
    public String asBaseType() {
        return from + "-" + to;
    }

    @Override
    public String toString() {
        return asBaseType();
    }

    /**
     * Verifies if the string is a valid hour range.
     * 
     * @param hourRange
     *            Hour range string to test.
     * 
     * @return {@literal true} if the string is a valid range, else {@literal false}.
     */
    public static boolean isValid(@Nullable final String hourRange) {
        if (hourRange == null) {
            return true;
        }
        final int p = hourRange.indexOf('-');
        if (p != 5) {
            return false;
        }
        final String from = hourRange.substring(0, 5);
        final String to = hourRange.substring(6);
        return Hour.isValid(from) && Hour.isValid(to);
    }

    /**
     * Converts a given string into an instance of this class.
     * 
     * @param str
     *            String to convert.
     * 
     * @return New instance.
     */
    @Nullable
    public static HourRange valueOf(@Nullable final String str) {
        if (str == null) {
            return null;
        }
        return new HourRange(str);
    }

    /**
     * Checks if the argument is valid and throws an exception if this is not the case.
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
            throw new ConstraintViolationException("The argument '" + name
                    + "' does not represent a valid hour range like '00:00-24:00' or '06:00-21:00': '" + value + "'");
        }

    }

}
