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
import org.jboss.weld.exceptions.IllegalStateException;

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
    public HourRanges(@NotEmpty final HourRange...ranges) {
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
     * If the hour ranges represent two different days, this method returns two hour ranges, one for each day.
     * Example: '09:00-14:00+18:00-03:00' will be splitted into '09:00-14:00+18:00-24:00' and '00:00-03:00'.
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
        list .add(new HourRanges(today.toArray(new HourRange[today.size()])));
        if (tommorrow.size() > 0) {
            list .add(new HourRanges(tommorrow.toArray(new HourRange[tommorrow.size()])));
        }
        return list;
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
                    + "' does not represent a valid hour range like '09:00-12:00+13:00-17:00': '" + value + "'");
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

        private final boolean nextDay;

        /**
         * Constructor with all data.
         * 
         * @param type
         *            Type of change.
         * @param range
         *            The changed hours.
         * @param nextDay
         *            Is this a change for hours of the next day?
         */
        public Change(@NotNull final ChangeType type, @NotNull final HourRange range, final boolean nextDay) {
            super();
            Contract.requireArgNotNull("type", type);
            Contract.requireArgNotNull("range", range);
            this.type = type;
            this.range = range;
            this.nextDay = nextDay;
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

        /**
         * Determines if this change is for the next day.
         * 
         * @return {@literal true} if the hour range changed for the next day.
         */
        public final boolean isNextDay() {
            return nextDay;
        }

        
        @Override
        public String toString() {
            return type + " " + range;
        }

    }
    
}
