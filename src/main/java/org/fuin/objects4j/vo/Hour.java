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

import java.util.regex.Pattern;

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

    @NotEmpty
    private String value;

    /**
     * Protected default constructor for deserialization.
     */
    protected Hour() {// NOSONAR Ignore JAXB default constructor
        super();
    }

    /**
     * Constructor with hour.
     * 
     * @param hour
     *            Hour like '00:00' (midnight new day), '24:00' (midnight prev. day), '12:00' (noon) or '23:59' (a minute before midnight).
     */
    public Hour(@NotNull @HourStr final String hour) {
        super();
        Contract.requireArgNotEmpty("hour", hour);
        if (!isValid(hour)) {
            throw new ConstraintViolationException(
                    "The argument 'hour' does not represent a valid hour like '00:00' or '23:59' or '24:00': '" + hour + "'");
        }
        this.value = hour;
    }

    @Override
    @NotEmpty
    public String asBaseType() {
        return value;
    }

    @Override
    public String toString() {
        return value;
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
    
}
