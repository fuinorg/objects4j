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

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.ui.Prompt;

/**
 * Represents the opening hours of one day of the week.<br>
 * Equals, HashCode and Comparable are based on the day of the week. <br>
 * Examples:
 * <ul>
 * <li>'Mon 09:00-12:00+13:00-17:00'</li>
 * <li>'Tue 09:00-17:00'</li>
 * <li>'Fri 18:00-03:00 (Every Friday from 6 pm to early morning next day</li>
 * </ul>
 */
@Immutable
@Prompt("Mon 09:00-12:00+13:00-17:00")
@XmlJavaTypeAdapter(DayOpeningHoursConverter.class)
public final class DayOpeningHours implements ValueObjectWithBaseType<String>, Comparable<DayOpeningHours>, Serializable, AsStringCapable {

    private static final long serialVersionUID = 1000L;

    @NotNull
    private DayOfTheWeek dayOfTheWeek;

    @NotNull
    private HourRanges hourRanges;

    /**
     * Protected default constructor for deserialization.
     */
    protected DayOpeningHours() {// NOSONAR Ignore JAXB default constructor
        super();
    }

    /**
     * Constructor with string.
     * 
     * @param dayOpeningHours
     *            Opening hours like 'Mon 09:00-12:00+13:00-17:00'.
     */
    public DayOpeningHours(@NotNull @DayOpeningHoursStr final String dayOpeningHours) {
        super();
        Contract.requireArgNotEmpty("dayOpeningHours", dayOpeningHours);
        requireArgValid("dayOpeningHours", dayOpeningHours);
        final int p = dayOpeningHours.indexOf(' ');
        dayOfTheWeek = DayOfTheWeek.valueOf(dayOpeningHours.substring(0, p));
        hourRanges = new HourRanges(dayOpeningHours.substring(p + 1));
    }

    /**
     * Constructor with objects.
     * 
     * @param dayOfTheWeek
     *            Day of the week this instance is representing.
     * @param hourRanges
     *            Open hours in that day of the week.
     */
    public DayOpeningHours(@NotNull final DayOfTheWeek dayOfTheWeek, @NotNull final HourRanges hourRanges) {
        super();
        Contract.requireArgNotNull("dayOfTheWeek", dayOfTheWeek);
        Contract.requireArgNotNull("hourRanges", hourRanges);
        this.dayOfTheWeek = dayOfTheWeek;
        this.hourRanges = hourRanges;
    }

    @Override
    public final int compareTo(final DayOpeningHours other) {
        return this.dayOfTheWeek.compareTo(other.dayOfTheWeek);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dayOfTheWeek == null) ? 0 : dayOfTheWeek.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DayOpeningHours other = (DayOpeningHours) obj;
        if (dayOfTheWeek == null) {
            if (other.dayOfTheWeek != null) {
                return false;
            }
        } else if (!dayOfTheWeek.equals(other.dayOfTheWeek)) {
            return false;
        }
        return true;
    }

    @Override
    @NotEmpty
    public String asBaseType() {
        return dayOfTheWeek + " " + hourRanges;
    }

    @Override
    public final Class<String> getBaseType() {
        return String.class;
    }

    @Override
    public final String asString() {
        return asBaseType();
    }

    @Override
    public String toString() {
        return asBaseType();
    }

    /**
     * Verifies if the string can be converted into an instance of this class.
     * 
     * @param dayOpeningHours
     *            String to test.
     * 
     * @return {@literal true} if the string is valid, else {@literal false}.
     */
    public static boolean isValid(@Nullable final String dayOpeningHours) {
        if (dayOpeningHours == null) {
            return true;
        }
        final int p = dayOpeningHours.indexOf(' ');
        if (p < 0) {
            return false;
        }
        final String dayOfTheWeekStr = dayOpeningHours.substring(0, p);
        final String hourRangesStr = dayOpeningHours.substring(p + 1);
        return DayOfTheWeek.isValid(dayOfTheWeekStr) && HourRanges.isValid(hourRangesStr);
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
    public static DayOpeningHours valueOf(@Nullable final String str) {
        if (str == null) {
            return null;
        }
        return new DayOpeningHours(str);
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
                    + "' does not represent a valid hour range like 'Mon 09:00-12:00+13:00-17:00': '" + value + "'");
        }

    }

}
