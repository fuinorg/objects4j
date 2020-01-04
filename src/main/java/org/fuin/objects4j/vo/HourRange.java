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
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.Immutable;
import org.fuin.objects4j.common.Nullable;
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

    @NotNull
    private Hour from;

    @NotNull
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
    public HourRange(@NotNull @HourRangeStr final String hourRange) {
        super();
        Contract.requireArgNotEmpty("hourRange", hourRange);
        requireArgValid("hourRange", hourRange);
        from = new Hour(hourRange.substring(0, 5));
        to = new Hour(hourRange.substring(6));
        if (from.equals(to)) {
            throw new ConstraintViolationException("The argument 'from' of the hour range cannot be equal 'to': '" + hourRange + "'");
        }
        if (from.equals(new Hour(24, 0))) {
            throw new ConstraintViolationException("The argument 'from' of the hour range cannot be '24:00'");
        }
        if (to.equals(new Hour(0, 0))) {
            throw new ConstraintViolationException("The argument 'to' of the hour range cannot be '00:00'");
        }
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
        if (from.equals(to)) {
            throw new ConstraintViolationException("The argument 'from' of the hour range cannot be equal 'to': " + from);
        }
        if (from.equals(new Hour(24, 0))) {
            throw new ConstraintViolationException("The argument 'from' of the hour range cannot be '24:00'");
        }
        if (to.equals(new Hour(0, 0))) {
            throw new ConstraintViolationException("The argument 'to' of the hour range cannot be '00:00'");
        }
    }

    /**
     * Returns the 'from' hour.
     * 
     * @return From hour.
     */
    public final Hour getFrom() {
        return from;
    }

    /**
     * Returns the 'to' hour.
     * 
     * @return To hour.
     */
    public final Hour getTo() {
        return to;
    }

    @Override
    @NotEmpty
    public final String asBaseType() {
        return from + "-" + to;
    }

    /**
     * Returns the range as a set of minutes. A value of {@literal false} means 'closed' and a value of {@literal true} means 'open'. It is
     * only allowed to call this method if the hour range represents only one day. This means a value like '18:00-03:00' will lead to an
     * error. To avoid this, call the {@link #normalize()} function before this one and pass the result per day as an argument to this
     * method.
     * 
     * @return Bitset representing the minutes of a day (0 = 00:00 - 1439 = 23:59).
     */
    public final BitSet toMinutes() {
        ensureSingleDayOnly(this);

        final BitSet minutes = new BitSet(1440);
        for (int i = from.toMinutes(); i < to.toMinutes(); i++) {
            minutes.set(i);
        }
        return minutes;

    }

    private static void ensureSingleDayOnly(final HourRange range) {
        final List<HourRange> list = range.normalize();
        if (list.size() > 1) {
            throw new IllegalArgumentException("Cannot convert an hour range to minutes that spans two days (" + list
                    + ") - Please use 'normalize()' method and pass then the hour range per day to this method!");
        }
    }

    @Override
    public final String toString() {
        return asBaseType();
    }

    /**
     * Determines if this range and the given range overlap.
     * 
     * @param other
     *            Range to compare with.
     * 
     * @return {@literal true} if the two ranges overlap, else {@literal false}.
     */
    public final boolean overlaps(@NotNull final HourRange other) {
        Contract.requireArgNotNull("other", other);
        if (this.equals(other)) {
            return true;
        }
        final int otherFrom = other.from.toMinutes();
        final int thisFrom = from.toMinutes();
        final int otherTo = other.to.toMinutes();
        final int thisTo = to.toMinutes();
        if (otherFrom <= thisTo && otherFrom >= thisFrom) {
            return true;
        }
        if (otherTo <= thisTo && otherTo >= thisFrom) {
            return true;
        }
        if (thisFrom <= otherTo && thisFrom >= otherFrom) {
            return true;
        }
        return (thisTo <= otherTo && thisTo >= otherFrom);
    }

    /**
     * If the hour range represents two different days, this method returns two hour ranges, one for each day. Example: '18:00-03:00' will
     * be splitted into '18:00-24:00' and '00:00-03:00'.
     * 
     * @return This range or range for today and tomorrow.
     */
    public final List<HourRange> normalize() {
        final List<HourRange> ranges = new ArrayList<>();
        if (this.from.toMinutes() > this.to.toMinutes()) {
            ranges.add(new HourRange(this.from, new Hour(24, 00)));
            ranges.add(new HourRange(new Hour(0, 0), this.to));
        } else {
            ranges.add(this);
        }
        return ranges;
    }
    
    /**
     * Returns the number of minutes the instance is 'open'.
     * CAUTION: Minutes that overlap to the next day are ignored!
     * This means '21:00-03:00' returns 180 minutes (and not 360).
     * 
     * @return Minutes (1-1440)
     */
    public final int getOpenMinutes() {
        final HourRange hr = normalize().get(0);        
        return hr.to.toMinutes() - hr.from.toMinutes();
    }

    /**
     * Returns the number of minutes the instance is 'closed'.
     * CAUTION: Minutes that overlap to the next day are ignored!
     * This means '21:00-03:00' returns 1260 minutes.
     * 
     * @return Minutes (0-1439)
     */
    public final int getClosedMinutes() {
        return 1440 - getOpenMinutes();
    }
    
    /**
     * Appends a single hour range to this one and returns a new instance. This is only allowed if this instance has 'to=24:00' and
     * 'from=00:00' for the other instance.
     * 
     * @param other
     *            Range start starts with '00:00'
     * 
     * @return New instance with "from" taken from this instance and 'to' taken from the other one.
     */
    public HourRange joinWithNextDay(@NotNull final HourRange other) {
        Contract.requireArgNotNull("other", other);
        if (!this.to.equals(new Hour(24, 0))) {
            throw new ConstraintViolationException("The 'to' hour value of this instance is not '24:00', but was: '" + this.to + "'");
        }
        if (!other.from.equals(new Hour(0, 0))) {
            throw new ConstraintViolationException(
                    "The 'from' hour value of the other instance is not '00:00', but was: '" + other.from + "'");
        }
        final int thisClosedMinutes = getClosedMinutes();
        final int otherOpenMinutes = other.getOpenMinutes();
        if (otherOpenMinutes > thisClosedMinutes) {
            throw new ConstraintViolationException(
                    "The hour range of the other instance cannot be greater than hours not used by this instance: this='" + this
                            + "', other='" + other + "'");
        }
        if (this.from.equals(other.to)) {
            return new HourRange("00:00-24:00");
        }
        return new HourRange(this.from, other.to);
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
        final String fromStr = hourRange.substring(0, 5);
        final String toStr = hourRange.substring(6);
        if (!(Hour.isValid(fromStr) && Hour.isValid(toStr))) {
            return false;
        }
        final Hour from = new Hour(fromStr);
        final Hour to = new Hour(toStr);
        if (from.equals(to)) {
            return false;
        }
        if (from.equals(new Hour(24, 0))) {
            return false;
        }
        return !to.equals(new Hour(0, 0));
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
