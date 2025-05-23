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
import org.fuin.objects4j.core.DayOpeningHours.Change;
import org.fuin.objects4j.ui.Prompt;

import javax.annotation.concurrent.Immutable;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Represents weekly opening hours separated by a comma ','.<br>
 * Example: 'Mon-Fri 09:00-12:00+13:00-17:00,Sat/Sun 09:-12:00'.<br>
 * Daily hours should not overlap - That is why 'Sun 18:00-03:00+Mon 02:00-03:00' is not valid.<br>
 */
@Immutable
@Prompt("Mon-Fri 09:00-12:00+13:00-17:00,Sat/Sun 09:-12:00")
@HasPublicStaticIsValidMethod
@HasPublicStaticValueOfMethod
public final class WeeklyOpeningHours extends AbstractStringValueObject implements Iterable<DayOpeningHours> {

    @Serial
    private static final long serialVersionUID = 1000L;

    @NotEmpty
    private final List<DayOpeningHours> openingHours;

    private final String value;

    /**
     * Constructor with string.
     * 
     * @param openingHours
     *            Opening hours like 'Mon-Fri 09:00-12:00+13:00-17:00,Sat/Sun 09:-12:00'.
     */
    public WeeklyOpeningHours(@NotNull @WeeklyOpeningHoursStr final String openingHours) {
        super();
        Contract.requireArgNotEmpty("weeklyOpeningHours", openingHours);
        requireArgValid("weeklyOpeningHours", openingHours);

        this.openingHours = new ArrayList<>();

        final StringTokenizer tok = new StringTokenizer(openingHours, ",");
        while (tok.hasMoreTokens()) {
            final String part = tok.nextToken();
            final int p = part.indexOf(' ');
            final MultiDayOfTheWeek dayPart = MultiDayOfTheWeek.valueOf(part.substring(0, p));
            final HourRanges hourPart = new HourRanges(part.substring(p + 1));
            for (DayOfTheWeek dow : dayPart) {
                final DayOpeningHours doh = new DayOpeningHours(dow, hourPart);
                final List<DayOpeningHours> normalized = doh.normalize();
                if (normalized.size() == 1) {
                    addOrUpdate(normalized.get(0));
                } else {
                    addOrUpdate(normalized.get(0));
                    addOrUpdate(normalized.get(1));
                }
            }
        }

        Collections.sort(this.openingHours);
        this.value = openingHours.toUpperCase();

    }

    /**
     * Constructor with hour weeklyOpeningHours array.
     * 
     * @param dayOpeningHours
     *            Ranges.
     */
    public WeeklyOpeningHours(@NotEmpty final DayOpeningHours... dayOpeningHours) {
        super();
        Contract.requireArgNotNull("dayOpeningHours", dayOpeningHours);
        if (dayOpeningHours.length == 0) {
            throw new ConstraintViolationException("The argument 'dayOpeningHours' cannot be an empty array");
        }
        this.openingHours = new ArrayList<>();
        for (final DayOpeningHours doh : dayOpeningHours) {
            if (doh != null) {
                if (this.openingHours.contains(doh)) {
                    throw new ConstraintViolationException("The argument 'dayOpeningHours' cannot contain duplicates: " + doh);
                }
                addOrUpdate(doh);
            }
        }

        Collections.sort(this.openingHours);
        this.value = asString(this.openingHours);

    }

    private void addOrUpdate(final DayOpeningHours doh) {
        final int idx = this.openingHours.indexOf(doh);
        if (idx < 0) {
            this.openingHours.add(doh);
        } else {
            this.openingHours.set(idx, this.openingHours.get(idx).add(doh));
        }
    }

    @Override
    @NotEmpty
    public String asBaseType() {
        return this.value;
    }

    /**
     * Removes all opening hours that span more than one day and puts them as an extra range into the other day. Example: 'Fri
     * 18:00-03:00,Sat 09:00-18:00' will be converted to 'Fri 18:00-24:00,Sat 00:00-03:00+09:00-18:00'.
     * 
     * @return Weekly opening hours with all opening hours inside the day.
     */
    public WeeklyOpeningHours normalize() {

        final List<DayOpeningHours> days = new ArrayList<>();

        for (final DayOpeningHours doh : openingHours) {
            final List<DayOpeningHours> normalized = doh.normalize();
            for (final DayOpeningHours normalizedDay : normalized) {
                final int idx = days.indexOf(normalizedDay);
                if (idx < 0) {
                    days.add(normalizedDay);
                } else {
                    final DayOpeningHours found = days.get(idx);
                    final DayOpeningHours replacement = found.add(normalizedDay);
                    days.set(idx, replacement);
                }
            }
        }

        Collections.sort(days);

        return new WeeklyOpeningHours(days.toArray(new DayOpeningHours[0]));

    }

    @Override
    public Iterator<DayOpeningHours> iterator() {
        return Collections.unmodifiableList(openingHours).iterator();
    }

    /**
     * Returns the added/removed opening hours from this week to the other one.
     * 
     * @param toOther
     *            New weekly information to compare with.
     * 
     * @return Changes from this week to the new one.
     */
    public List<Change> diff(@NotNull final WeeklyOpeningHours toOther) {

        Contract.requireArgNotNull("toOther", toOther);

        final WeeklyOpeningHours thisNormalized = this.normalize();
        final WeeklyOpeningHours otherNormalized = toOther.normalize();

        final List<Change> changes = new ArrayList<>();

        for (final DayOpeningHours thisDoh : thisNormalized) {
            final DayOpeningHours otherDoh = otherNormalized.findDay(thisDoh);
            if (otherDoh == null) {
                // A day was completely deleted
                changes.addAll(thisDoh.asRemovedChanges());
            } else {
                // Day exists, but hours may have changed
                changes.addAll(thisDoh.diff(otherDoh));
            }
        }
        for (final DayOpeningHours otherDoh : otherNormalized) {
            final DayOpeningHours thisDoh = thisNormalized.findDay(otherDoh);
            if (thisDoh == null) {
                // A new day was added
                changes.addAll(otherDoh.asAddedChanges());
            }
        }

        return changes;

    }

    /**
     * Returns all days and hour ranges as if they were removed.
     * 
     * @return Removed day changes.
     */
    public List<Change> asRemovedChanges() {
        final WeeklyOpeningHours normalized = this.normalize();
        final List<Change> changes = new ArrayList<>();
        for (final DayOpeningHours doh : normalized) {
            changes.addAll(doh.asRemovedChanges());
        }
        return changes;
    }

    /**
     * Returns all days and hour ranges as if they were added.
     * 
     * @return Added day changes.
     */
    public List<Change> asAddedChanges() {
        final WeeklyOpeningHours normalized = this.normalize();
        final List<Change> changes = new ArrayList<>();
        for (final DayOpeningHours doh : normalized) {
            changes.addAll(doh.asAddedChanges());
        }
        return changes;
    }

    private DayOpeningHours findDay(final DayOpeningHours toFind) {
        final int idx = openingHours.indexOf(toFind);
        if (idx < 0) {
            return null;
        }
        return openingHours.get(idx);
    }

    /**
     * Determines if this instance if "open" at the given day and time ranges. It is only allowed to call this method if the parameter
     * 'dayOpeningHours' represents only one day. This means a value like 'Fri 18:00-03:00' will lead to an error. To avoid this, call the
     * {@link DayOpeningHours#normalize()} function before this one and pass the result per day as an argument to this method.
     * 
     * 
     * @param dayOpeningHours
     *            Day and times to verify.
     * 
     * @return {@literal true} if open else {@literal false} if not open.
     */
    public final boolean openAt(@NotNull final DayOpeningHours dayOpeningHours) {
        Contract.requireArgNotNull("dayOpeningHours", dayOpeningHours);
        if (!dayOpeningHours.isNormalized()) {
            throw new ConstraintViolationException(
                    "The argument 'dayOpeningHours' is expected to have only hours of a single day, but was: " + dayOpeningHours);
        }

        final int idx = openingHours.indexOf(dayOpeningHours);
        if (idx < 0) {
            return false;
        }
        final DayOpeningHours found = openingHours.get(idx);
        final List<DayOpeningHours> normalized = found.normalize();
        final DayOpeningHours day = normalized.get(0);

        return day.openAt(dayOpeningHours);

    }

    /**
     * Returns the information if this instance is similar to the other one.<br>
     * For example is 'Mon 00:00-24:00' similar to 'Fri 18:00-24:00,Sat 00:00-03:00'.
     * 
     * @param other
     *            Instance to compare with.
     * 
     * @return {@literal true} if the instance is simlar.
     */
    public boolean isSimilarTo(final WeeklyOpeningHours other) {

        return compress().equals(other.compress());

    }

    /**
     * Creates a compressed version of this instance. The string representing this instance will be shortened.<br>
     * For example 'Mon 09:00-17:00,Tue 09:00-17:00' will become 'Mon/Tue 09:00-17:00'.
     * 
     * @return New shortened instance.
     */
    public final WeeklyOpeningHours compress() {

        final Map<HourRanges, List<DayOfTheWeek>> map = new HashMap<>();
        for (final DayOpeningHours doh : openingHours) {
            final HourRanges ranges = doh.getHourRanges().compress();
            List<DayOfTheWeek> dayList = map.computeIfAbsent(ranges, k -> new ArrayList<>());
            dayList.add(doh.getDayOfTheWeek());
        }

        final StringBuilder sb = new StringBuilder();
        for (final Map.Entry<HourRanges, List<DayOfTheWeek>> entry : map.entrySet()) {
            final List<DayOfTheWeek> days = entry.getValue();
            Collections.sort(days);
            if (!sb.isEmpty()) {
                sb.append(",");
            }
            sb.append(new MultiDayOfTheWeek(days.toArray(new DayOfTheWeek[0])).compress().asBaseType())
                    .append(" ")
                    .append(entry.getKey());
        }

        return new WeeklyOpeningHours(sb.toString());

    }

    @Override
    public final String toString() {
        return value;
    }

    private static String asString(final List<DayOpeningHours> weeklyOpeningHours) {
        final StringBuilder sb = new StringBuilder();
        for (final DayOpeningHours dow : weeklyOpeningHours) {
            if (!sb.isEmpty()) {
                sb.append(",");
            }
            sb.append(dow.toString());
        }
        return sb.toString();
    }

    /**
     * Verifies if the string is valid and could be converted into an object.
     * 
     * @param weeklyOpeningHours
     *            Hour weeklyOpeningHours string to test.
     * 
     * @return {@literal true} if the string is a valid string, else {@literal false}.
     */
    @SuppressWarnings("java:S3776") // Complexity not nice, but OK here
    public static boolean isValid(@Nullable final String weeklyOpeningHours) {
        if (weeklyOpeningHours == null) {
            return true;
        }

        final List<DayOpeningHours> all = new ArrayList<>();

        final StringTokenizer tok = new StringTokenizer(weeklyOpeningHours, ",");
        if (tok.countTokens() == 0) {
            return false;
        }
        while (tok.hasMoreTokens()) {
            final String part = tok.nextToken();

            // Find divider between day(s) and hours
            final int p = part.indexOf(' ');
            if (p < 0) {
                return false;
            }

            // Make sure first part is one or more days
            final String dayPartStr = part.substring(0, p);
            if (!MultiDayOfTheWeek.isValid(dayPartStr)) {
                return false;
            }
            final MultiDayOfTheWeek dayPart = MultiDayOfTheWeek.valueOf(dayPartStr);

            // Next part should be one or more hours
            final String hourPartStr = part.substring(p + 1);
            if (!HourRanges.isValid(hourPartStr)) {
                return false;
            }
            final HourRanges hourPart = new HourRanges(hourPartStr);

            // Avoid duplicate days
            for (DayOfTheWeek dow : dayPart) {
                final DayOpeningHours doh = new DayOpeningHours(dow, hourPart);
                if (all.contains(doh)) {
                    return false;
                }
                all.add(doh);
            }

            // Make sure the hours of the days do not overlap
            for (final DayOpeningHours doh : all) {
                final List<DayOpeningHours> normalized = doh.normalize();
                if (normalized.size() == 2) {
                    final DayOpeningHours secondDay = normalized.get(1);
                    final int idx = all.indexOf(secondDay);
                    if (idx > -1) {
                        final DayOpeningHours day = all.get(idx);
                        if (day.overlaps(secondDay)) {
                            return false;
                        }
                    }
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
    public static WeeklyOpeningHours valueOf(@Nullable final String str) {
        if (str == null) {
            return null;
        }
        return new WeeklyOpeningHours(str);
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
                    + "' does not represent valid weekly opening hours like 'Mon-Fri 09:00-12:00+13:00-17:00,Sat/Sun 09:-12:00': '" + value
                    + "'");
        }

    }

}
