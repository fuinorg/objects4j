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
package org.fuin.objects4j.core;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.HasPublicStaticIsValidMethod;
import org.fuin.objects4j.common.HasPublicStaticValueOfMethod;
import org.fuin.objects4j.ui.Label;
import org.fuin.objects4j.ui.Prompt;
import org.fuin.objects4j.ui.ShortLabel;
import org.fuin.objects4j.ui.Tooltip;

import javax.annotation.concurrent.Immutable;
import java.io.Serial;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents an abbreviated English day of the week name plus public holiday.<br>
 */
@Immutable
@ShortLabel("DOW")
@Label("Days of the week")
@Tooltip("The days of the week 'Mon'-'Sun'(from Monday to Sunday) plus 'PH' (Public Holiday)")
@Prompt("Fri")
@HasPublicStaticIsValidMethod
@HasPublicStaticValueOfMethod(method = "valueOf", param = String.class)
@HasPublicStaticValueOfMethod(method = "valueOf", param = DayOfWeek.class)
public final class DayOfTheWeek implements ValueObjectWithBaseType<String>, Comparable<DayOfTheWeek>, Serializable, AsStringCapable {

    @Serial
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
     * Returns the information this day of the week logically follows directly the given one. Example TUE.follows(MON) would be true, but
     * MON.follows(TUE) or WED.follows(MON) is not. Public holidays does not follow any other day.
     * 
     * @param other
     *            Day to compare with.
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
     * Returns the information this day of the week logically follows after the given one. Example TUE.follows(MON) or FRI.follows(MON)
     * would be true, but MON.follows(TUE) is not. Public holidays does not follow any other day.
     * 
     * @param other
     *            Day to compare with.
     * 
     * @return {@literal true} if this day of the week is later in the week than the given one.
     */
    public boolean after(@NotNull final DayOfTheWeek other) {
        Contract.requireArgNotNull("other", other);
        return this.id > other.id;
    }

    /**
     * Returns the next day.
     * 
     * @return Day that follows this one. In case of {@link #PH} {@literal null} is returned.
     */
    public DayOfTheWeek next() {
        if (this == PH) {
            return null;
        }
        final int nextId = id + 1;
        if (nextId == PH.id) {
            return MON;
        }
        for (final DayOfTheWeek dow : ALL) {
            if (dow.id == nextId) {
                return dow;
            }
        }
        throw new IllegalStateException("Wasn't able to find next day for: " + this);
    }

    /**
     * Returns the previous day.
     * 
     * @return Day before this one. In case of {@link #PH} {@literal null} is returned.
     */
    public DayOfTheWeek previous() {
        if (this == PH) {
            return null;
        }
        final int nextId = id - 1;
        for (final DayOfTheWeek dow : ALL) {
            if (dow.id == nextId) {
                return dow;
            }
        }
        return SUN;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
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
        DayOfTheWeek other = (DayOfTheWeek) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    /**
     * Returns the length.
     * 
     * @return Number of characters.
     */
    public final int length() {
        return asBaseType().length();
    }

    @Override
    public int compareTo(final DayOfTheWeek other) {
        if (id > other.id) {
            return 1;
        }
        if (id < other.id) {
            return -1;
        }
        return 0;
    }

    @Override
    public final Class<String> getBaseType() {
        return String.class;
    }

    @Override
    public final String asString() {
        return asBaseType();
    }

    /**
     * Converts the instance into java time instance.
     * 
     * @return Day of week.
     */
    public final DayOfWeek toDayOfWeek() {
        if (this == PH) {
            throw new UnsupportedOperationException("Cannot convert public holiday into java time instance");
        }
        return DayOfWeek.of(id);
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
     * Converts the instance into java time instance.
     * 
     * @param dayOfWeek
     *            Java time day of the week.
     * 
     * @return Day of week.
     */
    @Nullable
    public static DayOfTheWeek valueOf(@Nullable final DayOfWeek dayOfWeek) {
        if (dayOfWeek == null) {
            return null;
        }
        final int value = dayOfWeek.getValue();
        for (final DayOfTheWeek dow : ALL) {
            if (value == dow.id) {
                return dow;
            }
        }
        throw new IllegalArgumentException("Unknown day week: " + dayOfWeek);
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
     * @param from
     *            From day of the week (inclusive).
     * @param to
     *            To day of the week (inclusive).
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
