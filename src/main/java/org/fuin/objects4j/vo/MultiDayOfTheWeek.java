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

import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.ui.Prompt;

/**
 * Represents multiple days of the week 'Mon'-'Sun'(from Monday to Sunday) plus 'PH' (Public Holiday).<br>
 * Character '/' between the day names means 'and' while '-' means 'from-to' (including start/end). <br>
 * Examples:
 * <ul>
 * <li>Mon/Tue = Monday AND Tuesday</li>
 * <li>Mon-Fri = Monday TO Friday</li>
 * <li>Mon/Tue/Wed-Fri = Monday AND Tuesday AND Wednesday TO Friday (which is equal to 'Wed/Thu/Fri')</li>
 * </ul>
 */
@Immutable
@Prompt("Mon/Tue/Wed-Fri")
@XmlJavaTypeAdapter(MultiDayOfTheWeekConverter.class)
public final class MultiDayOfTheWeek extends AbstractStringValueObject implements Iterable<DayOfTheWeek> {

    private static final long serialVersionUID = 1000L;

    @NotEmpty
    private final List<DayOfTheWeek> multipleDayOfTheWeek;

    @NotNull
    private final String value;

    /**
     * Constructor with multiple days of the week string.
     * 
     * @param multipleDayOfTheWeek
     *            Value like 'Mon/Tue/Wed-Fri'.
     */
    public MultiDayOfTheWeek(@NotNull @MultiDayOfTheWeekStr final String multipleDayOfTheWeek) {
        super();
        Contract.requireArgNotEmpty("multipleDayOfTheWeek", multipleDayOfTheWeek);
        requireArgValid("multipleDayOfTheWeek", multipleDayOfTheWeek);

        this.multipleDayOfTheWeek = new ArrayList<>();
        final StringTokenizer tok = new StringTokenizer(multipleDayOfTheWeek, "/");
        while (tok.hasMoreTokens()) {
            final String part = tok.nextToken();
            final int p = part.indexOf('-');
            if (p > -1) {
                final DayOfTheWeek from = DayOfTheWeek.valueOf(part.substring(0, p));
                final DayOfTheWeek to = DayOfTheWeek.valueOf(part.substring(p + 1));
                for (final DayOfTheWeek dow : DayOfTheWeek.getPart(from, to)) {
                    this.multipleDayOfTheWeek.add(dow);
                }
            } else {
                this.multipleDayOfTheWeek.add(DayOfTheWeek.valueOf(part));
            }
        }

        Collections.sort(this.multipleDayOfTheWeek);
        this.value = multipleDayOfTheWeek.toUpperCase();

    }

    /**
     * Constructor with multiple day of the week array.
     * 
     * @param dayOfTheWeek
     *            Multiple day of the week.
     */
    public MultiDayOfTheWeek(@NotEmpty final DayOfTheWeek... dayOfTheWeek) {
        super();
        Contract.requireArgNotNull("dayOfTheWeek", dayOfTheWeek);
        if (dayOfTheWeek.length == 0) {
            throw new ConstraintViolationException("The argument 'dayOfTheWeek' cannot be an empty array");
        }
        this.multipleDayOfTheWeek = new ArrayList<>();
        for (final DayOfTheWeek dow : dayOfTheWeek) {
            if (dow != null) {
                this.multipleDayOfTheWeek.add(dow);
            }
        }

        Collections.sort(this.multipleDayOfTheWeek);
        this.value = asStr(multipleDayOfTheWeek);

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

    @Override
    public Iterator<DayOfTheWeek> iterator() {
        return Collections.unmodifiableList(multipleDayOfTheWeek).iterator();
    }

    /**
     * Returns a compressed version of this instance. Compressed means, that for example 'Mo/Tue/Wed/Thu/Fri' will be returned as 'Mon-Fri'.
     * 
     * @return Shortened version.
     */
    public MultiDayOfTheWeek compress() {

        if (multipleDayOfTheWeek.size() == 1) {
            return this;
        }

        DayOfTheWeek start = null;
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < multipleDayOfTheWeek.size(); i++) {
            final DayOfTheWeek current = multipleDayOfTheWeek.get(i);
            if (start == null) {
                if (i > 0) {
                    sb.append("/");
                }
                start = current;
                sb.append(start);
            } else {
                if (i == multipleDayOfTheWeek.size() - 1) {
                    sb.append(separator(start, current));
                    sb.append(current);
                } else {
                    if (multipleDayOfTheWeek.get(i + 1).previous() != current) {
                        sb.append(separator(start, current));
                        sb.append(current);
                        start = null;
                    }
                }
            }
        }
        return new MultiDayOfTheWeek(sb.toString());

    }

    private String separator(DayOfTheWeek start, final DayOfTheWeek current) {
        if (current.follows(start)) {
            return "/";
        }
        return "-";
    }

    private static String asStr(final List<DayOfTheWeek> days) {
        final StringBuilder sb = new StringBuilder();
        for (final DayOfTheWeek dow : days) {
            if (sb.length() > 0) {
                sb.append("/");
            }
            sb.append(dow);
        }
        return sb.toString();
    }

    /**
     * Verifies if the string is valid and could be converted into an object.
     * 
     * @param multiDayOfTheWeeks
     *            Hour multipleDayOfTheWeek string to test.
     * 
     * @return {@literal true} if the string is a valid string, else {@literal false}.
     */
    public static boolean isValid(@javax.annotation.Nullable final String multiDayOfTheWeeks) {
        if (multiDayOfTheWeeks == null) {
            return true;
        }
        final StringTokenizer tok = new StringTokenizer(multiDayOfTheWeeks, "/");
        if (tok.countTokens() == 0) {
            return false;
        }
        final List<DayOfTheWeek> days = new ArrayList<>();
        while (tok.hasMoreTokens()) {
            final String part = tok.nextToken();
            final int p = part.indexOf('-');
            if (p > -1) {
                // dd-dd
                final String part1 = part.substring(0, p);
                final String part2 = part.substring(p + 1);
                if (!DayOfTheWeek.isValid(part1) || !DayOfTheWeek.isValid(part2)) {
                    return false;
                }
                final DayOfTheWeek from = DayOfTheWeek.valueOf(part1);
                final DayOfTheWeek to = DayOfTheWeek.valueOf(part2);
                if (from == to || from.after(to)) {
                    return false;
                }
                for (final DayOfTheWeek dow : DayOfTheWeek.getPart(from, to)) {
                    if (days.contains(dow)) {
                        return false;
                    }
                    days.add(dow);                    
                }
                
            } else {
                // dd
                if (!DayOfTheWeek.isValid(part)) {
                    return false;
                }
                final DayOfTheWeek dow = DayOfTheWeek.valueOf(part);
                if (days.contains(dow)) {
                    return false;
                }
                days.add(dow);                    
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
    @javax.annotation.Nullable
    public static MultiDayOfTheWeek valueOf(@javax.annotation.Nullable final String str) {
        if (str == null) {
            return null;
        }
        return new MultiDayOfTheWeek(str);
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
                    "The argument '" + name + "' does not represent valid days of the week like 'Mon/Tue/Wed-Fri': '" + value + "'");
        }

    }

}
