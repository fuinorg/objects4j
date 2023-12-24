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

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.Immutable;
import org.fuin.objects4j.common.Nullable;
import org.fuin.objects4j.ui.Label;
import org.fuin.objects4j.ui.Prompt;
import org.fuin.objects4j.ui.ShortLabel;
import org.fuin.objects4j.ui.Tooltip;

import java.util.regex.Pattern;

/**
 * Represents an hour of a day (24 hours, sometimes called Military Time).<br>
 * <br>
 * Examples:
 * <ul>
 * <li>'00:00' Midnight next/new day</li>
 * <li>'01:00' One hour after midnight</li>
 * <li>'11:30' Half hour before noon</li>
 * <li>'12:00' Noon</li>
 * <li>'13:00' One hour after noon</li>
 * <li>'23:59' One minute before midnight</li>
 * <li>'24:00' Midnight current day</li>
 * </ul>
 */
@Immutable
@ShortLabel("HH")
@Label("Hour")
@Tooltip("Hour of a day")
@Prompt("23:59")
@XmlJavaTypeAdapter(HourConverter.class)
public final class Hour extends AbstractStringValueObject {

    private static final long serialVersionUID = 1000L;

    private static final Pattern PATTERN = Pattern.compile("^([01]\\d|2[0-3]):?([0-5]\\d)|24:00$");

    private int hourValue;

    private int minuteValue;

    /**
     * Protected default constructor for deserialization.
     */
    protected Hour() {// NOSONAR Ignore JAXB default constructor
        super();
    }

    /**
     * Constructor with string.
     * 
     * @param hour
     *            Hour like '00:00' (midnight new day), '24:00' (midnight prev. day), '12:00' (noon) or '23:59' (a minute before midnight).
     */
    public Hour(@NotNull @HourStr final String hour) {
        super();
        Contract.requireArgNotEmpty("hour", hour);
        requireArgValid("hour", hour);
        this.hourValue = Integer.valueOf(hour.substring(0, 2));
        this.minuteValue = Integer.valueOf(hour.substring(3));
    }

    /**
     * Constructor with hour/minute.
     * 
     * @param hour
     *            Hour 0-24.
     * @param minute
     *            Minute 0-59.
     */
    public Hour(final int hour, final int minute) {
        super();
        if (hour < 0 || hour > 24) {
            throw new ConstraintViolationException("The argument 'hour' is not a valid hour (0-24): '" + hour + "'");
        }
        if (minute < 0 || minute > 59) {
            throw new ConstraintViolationException("The argument 'minute' is not a valid minute (0-59): '" + minute + "'");
        }
        if (hour == 24 && minute != 0) {
            throw new ConstraintViolationException("The argument 'minute' must be '0' if the hour is '24': '" + minute + "'");
        }
        this.hourValue = hour;
        this.minuteValue = minute;
    }

    @Override
    @NotEmpty
    public String asBaseType() {
        if (hourValue < 10) {
            if (minuteValue < 10) {
                return "0" + hourValue + ":" + "0" + minuteValue;
            }
            return "0" + hourValue + ":" + minuteValue;
        }
        if (minuteValue < 10) {
            return hourValue + ":" + "0" + minuteValue;
        }
        return hourValue + ":" + minuteValue;
    }

    @Override
    public String toString() {
        return asBaseType();
    }

    /**
     * Converts the hour into minutes of day. '00:00' = 0 and '24:00' = 1440.
     * 
     * @return 0-1440
     */
    public int toMinutes() {
        return (hourValue * 60) + minuteValue;
    }

    /**
     * Verifies if the string is a valid hour.
     * 
     * @param hour
     *            Hour string to test.
     * 
     * @return {@literal true} if the string is a valid number, else {@literal false}.
     */
    public static boolean isValid(@Nullable final String hour) {
        if (hour == null) {
            return true;
        }
        return PATTERN.matcher(hour).matches();
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
    public static Hour valueOf(@Nullable final String str) {
        if (str == null) {
            return null;
        }
        return new Hour(str);
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

        if (!Hour.isValid(value)) {
            throw new ConstraintViolationException(
                    "The argument '" + name + "' does not represent a valid hour like '00:00' or '23:59' or '24:00': '" + value + "'");
        }

    }

}
