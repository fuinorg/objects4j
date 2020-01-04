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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.Immutable;
import org.fuin.objects4j.common.Nullable;
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
    private final List<HourRange> ranges;

    private final String value;

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
        int openMinutes = 0;
        final StringTokenizer tok = new StringTokenizer(ranges, "+");
        while (tok.hasMoreTokens()) {
            final HourRange range = new HourRange(tok.nextToken());
            openMinutes = openMinutes + range.getOpenMinutes();
            this.ranges.add(range);
        }
        if (openMinutes > 1440) {
            throw new ConstraintViolationException("The argument 'ranges' cannot contain more than 24 hours (1440 minutes)");
        }

        Collections.sort(this.ranges);
        this.value = asStr(this.ranges);
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
        int openMinutes = 0;
        this.ranges = new ArrayList<>();
        for (final HourRange range : ranges) {
            if (range != null) {
                openMinutes = openMinutes + range.getOpenMinutes();
                this.ranges.add(range);
            }
        }
        if (openMinutes > 1440) {
            throw new ConstraintViolationException("The argument 'ranges' cannot contain more than 24 hours (1440 minutes)");
        }

        Collections.sort(this.ranges);
        this.value = asStr(this.ranges);

    }

    @Override
    @NotEmpty
    public String asBaseType() {
        return this.value;
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
     * Determines if this instance contains logically more than one day. For example '18:00-03:00' will return {@literal true} while
     * '00:00-24:00' will not.<br>
     * 
     * @return {@literal true} if the time range overlaps into the next day.
     */
    public final boolean isNormalized() {
        return normalize().size() == 1;
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
     * @param other
     *            Other day to compare the hours with.
     * 
     * @return {@literal true} if at least one minute is the same for both days.
     */
    public final boolean overlaps(@NotNull final HourRanges other) {
        final BitSet thisMinutes = this.toMinutes();
        final BitSet otherMinutes = other.toMinutes();
        thisMinutes.and(otherMinutes);
        return !thisMinutes.isEmpty();
    }

    /**
     * Determines if this instance if "open" at the given time range.<br>
     * <br>
     * It is only allowed to call this method if the hour ranges represents only one day. This means a value like '18:00-03:00' will lead to
     * an error. To avoid this, call the {@link #normalize()} function before this one and pass the result per day as an argument to this
     * method.
     * 
     * @param range
     *            Time range to verify.
     * 
     * @return {@literal true} if open else {@literal false} if not open.
     */
    public final boolean openAt(@NotNull final HourRange range) {
        Contract.requireArgNotNull("range", range);
        ensureSingleDayOnly("this", this);

        final BitSet original = range.toMinutes();
        final BitSet anded = range.toMinutes();
        anded.and(this.toMinutes());

        return anded.equals(original);

    }

    /**
     * Adds some hour ranges to this instance and returns a new one.<br>
     * <br>
     * It is only allowed to call this method if the hour ranges represents only one day. This means a value like '18:00-03:00' will lead to
     * an error. To avoid this, call the {@link #normalize()} function before this one and pass the result per day as an argument to this
     * method.
     * 
     * @param other
     *            Ranges to add.
     * 
     * @return New instance with added times.
     */
    @NotNull
    public final HourRanges add(@NotNull final HourRanges other) {
        Contract.requireArgNotNull("other", other);
        ensureSingleDayOnly("this", this);
        ensureSingleDayOnly("other", other);

        final BitSet thisMinutes = this.toMinutes();
        final BitSet otherMinutes = other.toMinutes();
        thisMinutes.or(otherMinutes);

        return HourRanges.valueOf(thisMinutes);

    }

    /**
     * Removes some hour ranges from this instance and returns a new one.<br>
     * <br>
     * It is only allowed to call this method if the hour ranges represents only one day. This means a value like '18:00-03:00' will lead to
     * an error. To avoid this, call the {@link #normalize()} function before this one and pass the result per day as an argument to this
     * method.
     * 
     * @param other
     *            Ranges to remove.
     * 
     * @return New instance with removed times or {@literal null} if all times where removed.
     */
    @Nullable
    public final HourRanges remove(@NotNull final HourRanges other) {
        Contract.requireArgNotNull("other", other);
        ensureSingleDayOnly("this", this);
        ensureSingleDayOnly("other", other);

        final BitSet thisMinutes = this.toMinutes();
        final BitSet otherMinutes = other.toMinutes();
        otherMinutes.flip(0, 1440);
        thisMinutes.and(otherMinutes);

        if (thisMinutes.isEmpty()) {
            return null;
        }

        return HourRanges.valueOf(thisMinutes);

    }

    /**
     * Determines if the two instances are similar. For example is '12:00-13:00+13:00-14:00' similar to '12:00-14:00'. An expression is
     * similar if it denotes the same hour ranges.
     * 
     * @param other
     *            Other instance to compare this one with.
     * 
     * @return {@literal true} if both are similar.
     */
    public final boolean isSimilarTo(final HourRanges other) {
        return this.compress().equals(other.compress());
    }

    /**
     * Returns a compressed version of this instance.
     * 
     * @return Compressed version.
     */
    public final HourRanges compress() {
        final List<HourRanges> normalized = normalize();
        if (normalized.size() == 1) {
            return valueOf(normalized.get(0).toMinutes());
        } else if (normalized.size() == 2) {
            final HourRanges firstDay = valueOf(normalized.get(0).toMinutes());
            final HourRanges secondDay = normalized.get(1);
            if (secondDay.ranges.size() != 1) {
                throw new IllegalStateException(
                        "Expected exactly 1 hour range for the seond day, but was " + secondDay.ranges.size() + ": " + secondDay);
            }
            final HourRange seondDayHR = secondDay.ranges.get(0);

            final List<HourRange> newRanges = new ArrayList<>();

            int lastIdx = 0;
            if (firstDay.ranges.size() > 1) {
                lastIdx = firstDay.ranges.size() - 1;
                newRanges.addAll(firstDay.ranges.subList(0, lastIdx));
            }
            final HourRange firstDayHR = firstDay.ranges.get(lastIdx);
            newRanges.add(firstDayHR.joinWithNextDay(seondDayHR));

            return new HourRanges(newRanges.toArray(new HourRange[newRanges.size()]));

        } else {
            throw new IllegalStateException(
                    "Normalized hour ranges returned an unexpected number of elements (" + normalized.size() + "): " + normalized);
        }

    }

    @Override
    public final String toString() {
        return asString();
    }

    private static String asStr(final List<HourRange> ranges) {
        final StringBuilder sb = new StringBuilder();
        for (final HourRange range : ranges) {
            if (sb.length() > 0) {
                sb.append("+");
            }
            sb.append(range.toString());
        }
        return sb.toString();
    }

    private static void ensureSingleDayOnly(final String name, final HourRanges ranges) {
        if (!ranges.isNormalized()) {
            throw new ConstraintViolationException("The given hour ranges spans two days (" + name + "=" + ranges
                    + ") - Please use 'normalize()' method and pass then the hour ranges per day to this method!");
        }
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
     *            Bitset representing the minutes of a single day (0 = 00:00 - 1439 = 23:59).
     * 
     * @return New instance.
     */
    public static HourRanges valueOf(@Nullable final BitSet minutes) {
        return valueOf(minutes, 1440);
    }

    private static HourRanges valueOf(@Nullable final BitSet minutes, final int max) {
        if (minutes == null) {
            return null;
        }

        if (minutes.length() > max) {
            throw new IllegalArgumentException("Expected bitset length of max. 1440, but was " + minutes.length() + ": " + minutes);
        }

        Integer startHour = null;
        Integer startMinute = null;

        final List<HourRange> ranges = new ArrayList<>();
        for (int i = 0; i < max; i++) {
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
