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

import java.util.regex.Pattern;

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

    private static final Pattern PATTERN = Pattern.compile("^Mon|Tue|Wed|Thu|Fri|Sat|Sun|PH$", Pattern.CASE_INSENSITIVE);

    private String value;

    /**
     * Protected default constructor for deserialization.
     */
    protected DayOfTheWeek() {// NOSONAR Ignore JAXB default constructor
        super();
    }

    /**
     * Constructor with string.
     * 
     * @param dayOfTheWeek
     *            Day of the week 'Mon'-'Sun'(from Monday to Sunday) plus 'PH' (Public Holiday).
     */
    public DayOfTheWeek(@NotNull @DayOfTheWeekStr final String dayOfTheWeek) {
        super();
        Contract.requireArgNotEmpty("dayOfTheWeek", dayOfTheWeek);
        requireArgValid("dayOfTheWeek", dayOfTheWeek);
        this.value = dayOfTheWeek.toUpperCase();
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
        return PATTERN.matcher(dayOfTheWeek).matches();
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
        return new DayOfTheWeek(str);
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

}
