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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.ui.Prompt;

/**
 * Represents weekly opening hours separated by a comma ','.<br>
 * Example: 'Mon-Fri 09:00-12:00+13:00-17:00,Sat/Sun 09:-12:00'.
 */
@Immutable
@Prompt("Mon-Fri 09:00-12:00+13:00-17:00,Sat/Sun 09:-12:00")
@XmlJavaTypeAdapter(WeeklyOpeningHoursConverter.class)
public final class WeeklyOpeningHours extends AbstractStringValueObject {

    private static final long serialVersionUID = 1000L;

    @NotEmpty
    private List<DayOpeningHours> weeklyOpeningHours;
    
    /**
     * Protected default constructor for deserialization.
     */
    protected WeeklyOpeningHours() {// NOSONAR Ignore JAXB default constructor
        super();
    }

    /**
     * Constructor with string.
     * 
     * @param weeklyOpeningHours
     *            Opening hours like 'Mon-Fri 09:00-12:00+13:00-17:00,Sat/Sun 09:-12:00'.
     */
    public WeeklyOpeningHours(@NotNull @WeeklyOpeningHoursStr final String weeklyOpeningHours) {
        super();
        Contract.requireArgNotEmpty("weeklyOpeningHours", weeklyOpeningHours);
        requireArgValid("weeklyOpeningHours", weeklyOpeningHours);
        
        this.weeklyOpeningHours = new ArrayList<>();
        final StringTokenizer tok = new StringTokenizer(weeklyOpeningHours, ",");
        while (tok.hasMoreTokens()) {
            final String part = tok.nextToken();
            final int p = part.indexOf(' ');
            final MultiDayOfTheWeek dayPart = MultiDayOfTheWeek.valueOf(part.substring(0, p));
            final HourRanges hourPart = new HourRanges(part.substring(p + 1));
            for (DayOfTheWeek dow : dayPart.getList()) {
                final DayOpeningHours doh = new DayOpeningHours(dow, hourPart);
                this.weeklyOpeningHours.add(doh);
            }            
        }
        
    }

    /**
     * Constructor with hour weeklyOpeningHours array.
     * 
     * @param dayOpeningHours
     *            Ranges.
     */
    public WeeklyOpeningHours(@NotEmpty final DayOpeningHours...dayOpeningHours) {
        super();
        Contract.requireArgNotNull("dayOpeningHours", dayOpeningHours);
        if (dayOpeningHours.length == 0) {
            throw new ConstraintViolationException("The argument 'dayOpeningHours' cannot be an empty array");
        }
        this.weeklyOpeningHours = new ArrayList<>();
        for (final DayOpeningHours doh : dayOpeningHours) {
            if (doh != null) {
                if (this.weeklyOpeningHours.contains(doh)) {
                    throw new ConstraintViolationException("The argument 'dayOpeningHours' cannot contain duplicates: " + doh);
                }
                this.weeklyOpeningHours.add(doh);
            }
        }
    }
    
    @Override
    @NotEmpty
    public String asBaseType() {
        final StringBuilder sb = new StringBuilder();
        for (final DayOpeningHours dow : weeklyOpeningHours) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(dow.toString());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return asBaseType();
    }
    
    /**
     * Verifies if the string is valid and could be converted into an object.
     * 
     * @param weeklyOpeningHours
     *            Hour weeklyOpeningHours string to test.
     * 
     * @return {@literal true} if the string is a valid string, else {@literal false}.
     */
    public static boolean isValid(@Nullable final String weeklyOpeningHours) {
        if (weeklyOpeningHours == null) {
            return true;
        }
        
        final List<DayOpeningHours> all = new ArrayList<>();
        
        final StringTokenizer tok = new StringTokenizer(weeklyOpeningHours, ",");
        if (tok.countTokens() == 0) {
            return false;
        }
        while (tok.hasMoreTokens()) {
            final String part = tok.nextToken();
            
            // Find divider between day(s) and hours
            final int p = part.indexOf(' ');
            if (p < 0) {
                return false;
            }
            
            // Make sure first part is one or more days
            final String dayPartStr = part.substring(0, p);
            if (!MultiDayOfTheWeek.isValid(dayPartStr)) {
                return false;
            }
            final MultiDayOfTheWeek dayPart = MultiDayOfTheWeek.valueOf(dayPartStr);

            // Next part should be one or more hours
            final String hourPartStr = part.substring(p + 1);
            if (!HourRanges.isValid(hourPartStr)) {
                return false;
            }
            final HourRanges hourPart = new HourRanges(hourPartStr);

            // Avoid duplicate days
            for (DayOfTheWeek dow : dayPart.getList()) {
                final DayOpeningHours doh = new DayOpeningHours(dow, hourPart);
                if (all.contains(doh)) {
                    return false;
                }
                all.add(doh);
            }
            
        }
        return true;
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
    public static WeeklyOpeningHours valueOf(@Nullable final String str) {
        if (str == null) {
            return null;
        }
        return new WeeklyOpeningHours(str);
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
                    + "' does not represent valid weekly opening hours like 'Mon-Fri 09:00-12:00+13:00-17:00,Sat/Sun 09:-12:00': '" + value + "'");
        }

    }

}
