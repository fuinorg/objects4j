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
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.ui.Prompt;
import org.fuin.objects4j.vo.HourRanges.ChangeType;

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
    public final String toString() {
        return asBaseType();
    }

    /**
     * Returns a normalized version of the hour ranges. A day may contain hours that belong logically to the next day.<br>
     * An example is 'FRI 18:00-03:00' which means from Friday 6pm to Saturday 3am.<br>
     * This example would return then two entries: 'FRI 18:00-24:00' + 'SAT 00:00-03:00'.<br>
     * 
     * @return One or two days.
     */
    public final List<DayOpeningHours> normalize() {
        final List<HourRanges> ranges = hourRanges.normalize();
        final List<DayOpeningHours> list = new ArrayList<>();
        list.add(new DayOpeningHours(dayOfTheWeek, ranges.get(0)));
        if (ranges.size() > 1) {
            list.add(new DayOpeningHours(dayOfTheWeek.next(), ranges.get(1)));
        }
        return list;
    }

    /**
     * Determines if this instance contains more than one day. For example 'FRI 18:00-03:00' will return {@literal true} while 'FRI
     * 00:00-24:00' will not.<br>
     * 
     * @return {@literal true} if the time range overlaps into the next day.
     */
    public boolean isNormalized() {
        return hourRanges.isNormalized();
    }

    /**
     * Returns the difference when changing this opening hours to the other one. The day of the week must be equal for both compared
     * instances.
     * 
     * @param toOther
     *            Opening hours to compare with.
     * 
     * @return List of changes or an empty list if both are equal.
     */
    public final List<Change> diff(final DayOpeningHours toOther) {
        Contract.requireArgNotNull("toOther", toOther);
        if (dayOfTheWeek != toOther.dayOfTheWeek) {
            throw new ConstraintViolationException(
                    "Expected same day (" + dayOfTheWeek + ") for argument 'toOther', but was: " + toOther.dayOfTheWeek);
        }

        final List<DayOpeningHours> fromDays = normalize();
        final List<DayOpeningHours> toDays = toOther.normalize();

        final List<Change> changes = new ArrayList<>();

        if (fromDays.size() == 1) {
            if (toDays.size() == 1) {
                // Both only 1 day
                final DayOpeningHours from = fromDays.get(0);
                final DayOpeningHours to = toDays.get(0);
                changes.addAll(changes(this.dayOfTheWeek, from.hourRanges, to.hourRanges));
            } else {
                // From 1 day / To 2 days
                final DayOpeningHours from = fromDays.get(0);
                final DayOpeningHours to1 = toDays.get(0);
                final DayOpeningHours to2 = toDays.get(1);
                changes.addAll(changes(this.dayOfTheWeek, from.hourRanges, to1.hourRanges));
                changes.addAll(changes(ChangeType.ADDED, to2.dayOfTheWeek, to2.hourRanges));
            }
        } else {
            if (toDays.size() == 1) {
                // From 2 days / To 1 day
                final DayOpeningHours from1 = fromDays.get(0);
                final DayOpeningHours from2 = fromDays.get(1);
                final DayOpeningHours to = toDays.get(0);
                changes.addAll(changes(this.dayOfTheWeek, from1.hourRanges, to.hourRanges));
                changes.addAll(changes(ChangeType.REMOVED, from2.dayOfTheWeek, from2.hourRanges));
            } else {
                // Both 2 days
                final DayOpeningHours from1 = fromDays.get(0);
                final DayOpeningHours from2 = fromDays.get(1);
                final DayOpeningHours to1 = toDays.get(0);
                final DayOpeningHours to2 = toDays.get(1);
                changes.addAll(changes(from1.dayOfTheWeek, from1.hourRanges, to1.hourRanges));
                changes.addAll(changes(from2.dayOfTheWeek, from2.hourRanges, to2.hourRanges));
            }
        }

        return changes;
    }

    /**
     * Determines of the hours of both days overlap. The day is ignored for this comparison.
     * 
     * @param other
     *            Other day to compare the hours with.
     * 
     * @return {@literal true} if at least one minute is the same for both days.
     */
    public final boolean overlaps(@NotNull final DayOpeningHours other) {
        Contract.requireArgNotNull("other", other);
        return hourRanges.overlaps(other.hourRanges);
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
    public final DayOpeningHours add(@NotNull final HourRanges other) {
        Contract.requireArgNotNull("other", other);
        final HourRanges added = this.hourRanges.add(other);
        return new DayOpeningHours(dayOfTheWeek, added);
    }

    /**
     * Adds some hour ranges to this instance and returns a new one. The day of the argument is ignored.<br>
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
    public final DayOpeningHours add(@NotNull final DayOpeningHours other) {
        Contract.requireArgNotNull("other", other);
        return this.add(other.hourRanges);
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
    public final DayOpeningHours remove(@NotNull final HourRanges other) {
        Contract.requireArgNotNull("other", other);
        final HourRanges removed = this.hourRanges.remove(other);
        if (removed == null) {
            return null;
        }
        return new DayOpeningHours(dayOfTheWeek, removed);

    }

    /**
     * Removes some hour ranges from this instance and returns a new one. The day of the argument is ignored.<br>
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
    public final DayOpeningHours remove(@NotNull final DayOpeningHours other) {
        Contract.requireArgNotNull("other", other);
        return this.remove(other.hourRanges);

    }

    /**
     * Returns all hour ranges of this day as if they were removed.
     * 
     * @return Removed day changes.
     */
    public List<Change> asRemovedChanges() {
        final List<Change> changes = new ArrayList<>();
        for (final HourRange hr : hourRanges) {
            changes.add(new Change(ChangeType.REMOVED, dayOfTheWeek, hr));
        }
        return changes;
    }

    /**
     * Returns all hour ranges of this day as if they were added.
     * 
     * @return Added day changes.
     */
    public List<Change> asAddedChanges() {
        final List<Change> changes = new ArrayList<>();
        for (final HourRange hr : hourRanges) {
            changes.add(new Change(ChangeType.ADDED, dayOfTheWeek, hr));
        }
        return changes;
    }

    /**
     * Determines if this instance if "open" at the given time range. It is only allowed to call this method if the hour ranges represents
     * only one day. This means a value like '18:00-03:00' will lead to an error. To avoid this, call the {@link #normalize()} function
     * before this one and pass the result per day as an argument to this method.
     * 
     * @param range
     *            Time range to verify.
     * 
     * @return {@literal true} if open else {@literal false} if not open.
     */
    public boolean openAt(@NotNull final HourRange range) {
        Contract.requireArgNotNull("range", range);
        return hourRanges.openAt(range);
    }

    /**
     * Determines if this instance if "open" at the given time range. It is only allowed to call this method if the hour ranges represents
     * only one day. This means a value like 'Fri 18:00-03:00' will lead to an error. To avoid this, call the {@link #normalize()} function
     * before this one and pass the result per day as an argument to this method. If the day of the week of the parameter is different from
     * this one the method will return {@literal false}.
     * 
     * @param other
     *            Day/time ranges to verify.
     * 
     * @return {@literal true} if open else {@literal false} if not open.
     */
    public boolean openAt(@NotNull final DayOpeningHours other) {
        Contract.requireArgNotNull("other", other);
        if (dayOfTheWeek != other.dayOfTheWeek) {
            return false;
        }
        for (final HourRange otherRange : other.hourRanges) {
            if (!hourRanges.openAt(otherRange)) {
                return false;
            }
        }
        return true;
    }

    private static List<Change> changes(final ChangeType type, final DayOfTheWeek dayOfTheWeek, final HourRanges ranges) {
        final List<Change> changes = new ArrayList<>();
        for (final HourRange hr : ranges) {
            changes.add(new Change(type, dayOfTheWeek, hr));
        }
        return changes;
    }

    private static List<Change> changes(final DayOfTheWeek dayOfTheWeek, final HourRanges from, final HourRanges to) {
        final List<Change> changes = new ArrayList<>();
        final List<HourRanges.Change> diff = from.diff(to);
        for (HourRanges.Change change : diff) {
            changes.add(new Change(dayOfTheWeek, change));
        }
        return changes;
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

    /**
     * Represents a single change of opening hours.
     */
    public static final class Change {

        private final ChangeType type;

        private final DayOfTheWeek day;

        private final HourRange range;

        /**
         * Constructor with hour ranges change.
         * 
         * @param day
         *            DayOf the week.
         * @param change
         *            The changed hour ranges.
         */
        public Change(@NotNull final DayOfTheWeek day, @NotNull final HourRanges.Change change) {
            super();
            Contract.requireArgNotNull("day", day);
            Contract.requireArgNotNull("change", change);
            this.day = day;
            this.type = change.getType();
            this.range = change.getRange();
        }

        /**
         * Constructor with all data.
         * 
         * @param type
         *            Type of change.
         * @param day
         *            DayOf the week.
         * @param range
         *            The changed hours.
         */
        public Change(@NotNull final ChangeType type, @NotNull final DayOfTheWeek day, @NotNull final HourRange range) {
            super();
            Contract.requireArgNotNull("type", type);
            Contract.requireArgNotNull("day", day);
            Contract.requireArgNotNull("range", range);
            this.type = type;
            this.day = day;
            this.range = range;
        }

        /**
         * Returns the day of the week.
         * 
         * @return Day.
         */
        public final DayOfTheWeek getDay() {
            return day;
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
            result = prime * result + ((day == null) ? 0 : day.hashCode());
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
            if (day == null) {
                if (other.day != null) {
                    return false;
                }
            } else if (!day.equals(other.day)) {
                return false;
            }
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
        public String toString() {
            return type + " " + day + " " + range;
        }

    }

}
