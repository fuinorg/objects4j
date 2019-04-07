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
import java.util.Collections;
import java.util.List;

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
 * Represents an abbreviated English day of the week name plus public holiday.<br>
 */
@Immutable
@ShortLabel("DOW")
@Label("Days of the week")
@Tooltip("The days of the week 'Mon'-'Sun'(from Monday to Sunday) plus 'PH' (Public Holiday)")
@Prompt("Fri")
@XmlJavaTypeAdapter(DayOfTheWeekConverter.class)
public final class DayOfTheWeek extends AbstractStringValueObject {

    private static final long serialVersionUID = 1000L;

    /** Monday. */
    public static final DayOfTheWeek MON = new DayOfTheWeek(1, "MON");
    
    /** Tuesday. */
    public static final DayOfTheWeek TUE = new DayOfTheWeek(2, "TUE");
    
    /** Wednesday. */
    public static final DayOfTheWeek WED = new DayOfTheWeek(3, "WED");
    
    /** Thursday. */
    public static final DayOfTheWeek THU = new DayOfTheWeek(4, "THU");
    
    /** Friday. */
    public static final DayOfTheWeek FRI = new DayOfTheWeek(5, "FRI");
    
    /** Saturday. */
    public static final DayOfTheWeek SAT = new DayOfTheWeek(6, "SAT");
    
    /** Sunday. */
    public static final DayOfTheWeek SUN = new DayOfTheWeek(7, "SUN");
    
    /** Public Holiday. */
    public static final DayOfTheWeek PH = new DayOfTheWeek(8, "PH");

    private static final DayOfTheWeek[] ALL = new DayOfTheWeek[] { MON, TUE, WED, THU, FRI, SAT, SUN, PH };
    
    private final int id;
    
    private final String value;

    /**
     * Constructor with string.
     * 
     * @param dayOfTheWeek
     *            Day of the week 'Mon'-'Sun'(from Monday to Sunday) plus 'PH' (Public Holiday).
     */
    private DayOfTheWeek(final int id, @NotNull @DayOfTheWeekStr final String dayOfTheWeek) {
        super();
        this.id = id;
        this.value = dayOfTheWeek.toUpperCase();
    }

    @Override
    @NotEmpty
    public String asBaseType() {
        return value;
    }

    /**
     * Returns the information this day of the week logically follows directly the given one.
     * Example TUE.follows(MON) would be true, but MON.follows(TUE) or WED.follows(MON) is not.
     * Public holidays does not follow any other day. 
     * 
     * @param other Day to compare with.
     * 
     * @return {@literal true} if this day of the week is the one right after the given one. 
     */
    public boolean follows(@NotNull final DayOfTheWeek other) {
        Contract.requireArgNotNull("other", other);
        if (this == PH || other == PH) {
            return false;
        }
        return this.id == (other.id + 1); 
    }

    /**
     * Returns the information this day of the week logically follows after the given one.
     * Example TUE.follows(MON) or FRI.follows(MON) would be true, but MON.follows(TUE) is not.
     * Public holidays does not follow any other day. 
     * 
     * @param other Day to compare with.
     * 
     * @return {@literal true} if this day of the week is later in the week than the given one. 
     */
    public boolean after(@NotNull final DayOfTheWeek other) {
        Contract.requireArgNotNull("other", other);
        return this.id > other.id; 
    }
    
    @Override
    public String toString() {
        return value;
    }

    /**
     * Verifies if the string is a valid dayOfTheWeek.
     * 
     * @param dayOfTheWeek
     *            Weekday string to test.
     * 
     * @return {@literal true} if the string is a valid number, else {@literal false}.
     */
    public static boolean isValid(@Nullable final String dayOfTheWeek) {
        if (dayOfTheWeek == null) {
            return true;
        }
        for (final DayOfTheWeek dow : ALL) {
            if (dow.value.equalsIgnoreCase(dayOfTheWeek)) {
                return true;
            }
        }
        return false;
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
    public static DayOfTheWeek valueOf(@Nullable final String str) {
        if (str == null) {
            return null;
        }
        for (final DayOfTheWeek dow : ALL) {
            if (dow.value.equalsIgnoreCase(str)) {
                return dow;
            }
        }
        throw new IllegalArgumentException("Unknown day of week: '" + str + "'");
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

        if (!DayOfTheWeek.isValid(value)) {
            throw new ConstraintViolationException("The argument '" + name
                    + "' does not represent a valid day of the week like 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun' or 'PH': '" + value
                    + "'");
        }

    }
    
    /**
     * Returns a list of the weekdays ordered by the id (Mon-Sun,PH). 
     * 
     * @return Unmodifiable list.
     */
    public static List<DayOfTheWeek> getAll() {
        final List<DayOfTheWeek> days = new ArrayList<>();
        for (final DayOfTheWeek day : ALL) {
            days.add(day);
        }
        return Collections.unmodifiableList(days);
    }

    /**
     * Returns a list of the weekdays ordered by the id from/to the given days. 
     * 
     * @return Unmodifiable list.
     */
    public static List<DayOfTheWeek> getPart(@NotNull final DayOfTheWeek from, @NotNull final DayOfTheWeek to) {
        final List<DayOfTheWeek> days = new ArrayList<>();
        for (final DayOfTheWeek day : ALL) {
            if (day.id >= from.id && day.id <= to.id) {
                days.add(day);
            }
        }
        return Collections.unmodifiableList(days);
    }
    
}
