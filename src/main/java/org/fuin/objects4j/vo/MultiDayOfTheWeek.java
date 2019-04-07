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
public final class MultiDayOfTheWeek extends AbstractStringValueObject {

    private static final long serialVersionUID = 1000L;

    @NotEmpty
    private List<DayOfTheWeek> multipleDayOfTheWeek;

    /**
     * Protected default constructor for deserialization.
     */
    protected MultiDayOfTheWeek() {// NOSONAR Ignore JAXB default constructor
        super();
    }

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
    }

    @Override
    @NotEmpty
    public String asBaseType() {
        final StringBuilder sb = new StringBuilder();
        
        for (final DayOfTheWeek dow : this.multipleDayOfTheWeek) {
            if (sb.length() > 0) {
                sb.append("/");
            }
            sb.append(dow);
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
     * @param multiDayOfTheWeeks
     *            Hour multipleDayOfTheWeek string to test.
     * 
     * @return {@literal true} if the string is a valid string, else {@literal false}.
     */
    public static boolean isValid(@Nullable final String multiDayOfTheWeeks) {
        if (multiDayOfTheWeeks == null) {
            return true;
        }
        final StringTokenizer tok = new StringTokenizer(multiDayOfTheWeeks, "/");
        if (tok.countTokens() == 0) {
            return false;
        }
        while (tok.hasMoreTokens()) {
            final String part = tok.nextToken();
            final int p = part.indexOf('-');
            if (p > -1) {
                final DayOfTheWeek from = DayOfTheWeek.valueOf(part.substring(0, p));
                final DayOfTheWeek to = DayOfTheWeek.valueOf(part.substring(p + 1));
                if (from == to || from.after(to)) {
                    return false;
                }
            } else {
                if (!DayOfTheWeek.isValid(part)) {
                    return false;
                }
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
    public static MultiDayOfTheWeek valueOf(@Nullable final String str) {
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
