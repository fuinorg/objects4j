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
import java.util.BitSet;
import java.util.Collections;
import java.util.Iterator;
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
 * Represents multiple ranges of hours of a day (24 hour representation) separated by a '+'.<br>
 * Example: '09:00-12:00+13:00-17:00' From 9 am to 12 noon and from 1 pm to 5 pm
 */
@Immutable
@Prompt("09:00-12:00+13:00-17:00")
@XmlJavaTypeAdapter(HourRangesConverter.class)
public final class HourRanges extends AbstractStringValueObject implements Iterable<HourRange> {

    private static final long serialVersionUID = 1000L;

    @NotEmpty
    private List<HourRange> ranges;

    /**
     * Protected default constructor for deserialization.
     */
    protected HourRanges() {// NOSONAR Ignore JAXB default constructor
        super();
    }

    /**
     * Constructor with hour ranges string.
     * 
     * @param ranges
     *            Hour like '09:00-12:00+13:00-17:00'.
     */
    public HourRanges(@NotNull @HourRangesStr final String ranges) {
        super();
        Contract.requireArgNotEmpty("ranges", ranges);
        requireArgValid("ranges", ranges);

        this.ranges = new ArrayList<>();
        final StringTokenizer tok = new StringTokenizer(ranges, "+");
        while (tok.hasMoreTokens()) {
            this.ranges.add(new HourRange(tok.nextToken()));
        }
        Collections.sort(this.ranges);

    }

    /**
     * Constructor with hour ranges array.
     * 
     * @param ranges
     *            Ranges.
     */
    public HourRanges(@NotEmpty final HourRange... ranges) {
        super();
        Contract.requireArgNotNull("ranges", ranges);
        if (ranges.length == 0) {
            throw new ConstraintViolationException("The argument 'hourRange' cannot be an empty array");
        }
        this.ranges = new ArrayList<>();
        for (final HourRange range : ranges) {
            if (range != null) {
                this.ranges.add(range);
            }
        }
    }

    @Override
    @NotEmpty
    public String asBaseType() {
        final StringBuilder sb = new StringBuilder();
        for (final HourRange range : ranges) {
            if (sb.length() > 0) {
                sb.append("+");
            }
            sb.append(range.toString());
        }
        return sb.toString();
    }

    @Override
    public final Iterator<HourRange> iterator() {
        return Collections.unmodifiableList(ranges).iterator();
    }

    /**
     * If the hour ranges represent two different days, this method returns two hour ranges, one for each day. Example:
     * '09:00-14:00+18:00-03:00' will be splitted into '09:00-14:00+18:00-24:00' and '00:00-03:00'.
     * 
     * @return This range or range for today and tomorrow.
     */
    public final List<HourRanges> normalize() {
        final List<HourRange> today = new ArrayList<>();
        final List<HourRange> tommorrow = new ArrayList<>();
        for (final HourRange range : ranges) {
            final List<HourRange> nr = range.normalize();
            if (nr.size() == 1) {
                today.add(nr.get(0));
            } else if (nr.size() == 2) {
                today.add(nr.get(0));
                tommorrow.add(nr.get(1));
            } else {
                throw new IllegalStateException("Normalized hour range returned an unexpected number of elements: " + nr);
            }
        }
        final List<HourRanges> list = new ArrayList<>();
        list.add(new HourRanges(today.toArray(new HourRange[today.size()])));
        if (tommorrow.size() > 0) {
            list.add(new HourRanges(tommorrow.toArray(new HourRange[tommorrow.size()])));
        }
        return list;
    }

    /**
     * Returns the difference when changing this opening hours to the other one.
     * 
     * @param toOther
     *            Opening hours to compare with.
     * 
     * @return List of changes or an empty list if both are equal.
     */
    public final List<Change> diff(final HourRanges toOther) {

        ensureSingleDayOnly("from", this);
        ensureSingleDayOnly("to", toOther);

        final BitSet thisMinutes = this.toMinutes();
        final BitSet otherMinutes = toOther.toMinutes();

        final BitSet addedMinutes = new BitSet(1440);
        final BitSet removedMinutes = new BitSet(1440);
        for (int i = 0; i < 1440; i++) {
            if (thisMinutes.get(i) && !otherMinutes.get(i)) {
                removedMinutes.set(i);
            }
            if (!thisMinutes.get(i) && otherMinutes.get(i)) {
                addedMinutes.set(i);
            }
        }

        final List<Change> changes = new ArrayList<>();

        if (!removedMinutes.isEmpty()) {
            final HourRanges removed = HourRanges.valueOf(removedMinutes);
            for (final HourRange hr : removed) {
                changes.add(new Change(ChangeType.REMOVED, hr));
            }
        }
        if (!addedMinutes.isEmpty()) {
            final HourRanges added = HourRanges.valueOf(addedMinutes);
            for (final HourRange hr : added) {
                changes.add(new Change(ChangeType.ADDED, hr));
            }
        }

        return changes;

    }

    private static void ensureSingleDayOnly(final String name, final HourRanges ranges) {
        final List<HourRanges> list = ranges.normalize();
        if (list.size() > 1) {
            throw new ConstraintViolationException("Cannot calculate the difference for hour ranges that spans two days (" + name + "="
                    + list + ") - Please use 'normalize()' method and pass then the hour ranges per day to this method!");
        }
    }

    /**
     * Returns the ranges as a set of minutes. A value of {@literal false} means 'closed' and a value of {@literal true} means 'open'. It is
     * only allowed to call this method if the hour ranges represents only one day. This means a value like '18:00-03:00' will lead to an
     * error. To avoid this, call the {@link #normalize()} function before this one and pass the result per day as an argument to this
     * method.
     * 
     * @return Bitset representing the minutes of a day (0 = 00:00 - 1439 = 23:59).
     */
    public final BitSet toMinutes() {
        ensureSingleDayOnly("this", this);

        final BitSet minutes = new BitSet(1440);
        for (final HourRange range : ranges) {
            minutes.or(range.toMinutes());
        }
        return minutes;

    }


    /**
     * Determines of the hours of both days overlap. The day is ignored for this comparison.
     * 
     * @param other Other day to compare the hours with.
     * 
     * @return {@literal true} if at least one minute is the same for both days.
     */
    public final boolean overlaps(@NotNull final HourRanges other) {
        final BitSet thisMinutes = this.toMinutes();
        final BitSet otherMinutes = other.toMinutes();
        thisMinutes.and(otherMinutes);        
        return !thisMinutes.isEmpty();
    }
    
    @Override
    public String toString() {
        return asBaseType();
    }

    /**
     * Verifies if the string is valid and could be converted into an object.
     * 
     * @param hourRanges
     *            Hour ranges string to test.
     * 
     * @return {@literal true} if the string is a valid string, else {@literal false}.
     */
    public static boolean isValid(@Nullable final String hourRanges) {
        if (hourRanges == null) {
            return true;
        }
        final StringTokenizer tok = new StringTokenizer(hourRanges, "+");
        if (tok.countTokens() == 0) {
            return false;
        }
        while (tok.hasMoreTokens()) {
            final String rangeStr = tok.nextToken();
            if (!HourRange.isValid(rangeStr)) {
                return false;
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
    public static HourRanges valueOf(@Nullable final String str) {
        if (str == null) {
            return null;
        }
        return new HourRanges(str);
    }

    /**
     * Converts a given bit set with the minutes of the day into an instance of this class.
     * 
     * @param minutes
     *            Bitset representing the minutes of a day (0 = 00:00 - 1439 = 23:59).
     * 
     * @return New instance.
     */
    public static HourRanges valueOf(@NotNull final BitSet minutes) {
        if (minutes == null) {
            return null;
        }
        if (minutes.length() > 1440) {
            throw new IllegalArgumentException("Expected bitset length of max. 1440, but was " + minutes.length() + ": " + minutes);
        }

        Integer startHour = null;
        Integer startMinute = null;

        final List<HourRange> ranges = new ArrayList<>();
        for (int i = 0; i < minutes.length(); i++) {
            if (minutes.get(i)) {
                if (startHour == null) {
                    startHour = (i / 60);
                    startMinute = i - (startHour * 60);
                }
            } else {
                if (startHour != null) {
                    ranges.add(createHourRange(startHour, startMinute, i));
                    startHour = null;
                    startMinute = 0;
                }
            }
        }
        if (startHour != null) {
            ranges.add(createHourRange(startHour, startMinute, minutes.length()));
        }

        return new HourRanges(ranges.toArray(new HourRange[ranges.size()]));

    }

    private static HourRange createHourRange(final int startHour, final int startMinute, final int minute) {
        final int endHour = (minute / 60);
        final int endMinute;
        if (endHour == 24) {
            endMinute = 0;
        } else {
            endMinute = minute - (endHour * 60);
        }
        return new HourRange(new Hour(startHour, startMinute), new Hour(endHour, endMinute));
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
            throw new ConstraintViolationException(
                    "The argument '" + name + "' does not represent a valid hour range like '09:00-12:00+13:00-17:00': '" + value + "'");
        }

    }

    /**
     * Types of changes.
     */
    public static enum ChangeType {

        /** An hour range was added. */
        ADDED,

        /** An hour range was removed. */
        REMOVED

    }

    /**
     * Represents a single change of opening hours.
     */
    public static final class Change {

        private final ChangeType type;

        private final HourRange range;

        /**
         * Constructor with all data.
         * 
         * @param type
         *            Type of change.
         * @param range
         *            The changed hours.
         */
        public Change(@NotNull final ChangeType type, @NotNull final HourRange range) {
            super();
            Contract.requireArgNotNull("type", type);
            Contract.requireArgNotNull("range", range);
            this.type = type;
            this.range = range;
        }

        /**
         * Returns the type of change.
         * 
         * @return Type.
         */
        public final ChangeType getType() {
            return type;
        }

        /**
         * Returns the changed hours.
         * 
         * @return Hours.
         */
        public final HourRange getRange() {
            return range;
        }

        @Override
        public final int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((range == null) ? 0 : range.hashCode());
            result = prime * result + ((type == null) ? 0 : type.hashCode());
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
            Change other = (Change) obj;
            if (range == null) {
                if (other.range != null) {
                    return false;
                }
            } else if (!range.equals(other.range)) {
                return false;
            }
            if (type != other.type) {
                return false;
            }
            return true;
        }

        @Override
        public final String toString() {
            return type + " " + range;
        }

    }

}
