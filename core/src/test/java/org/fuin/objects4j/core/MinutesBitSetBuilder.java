/**
 * Copyright (C) 2013 Future Invent Informationsmanagement GmbH. All rights
 * reserved. <http://www.fuin.org/>
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
 * along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fuin.objects4j.core;

import com.tngtech.archunit.junit.ArchIgnore;
import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.common.Contract;

import java.util.BitSet;

/**
 * Helps building a bit set for hour/minutes.
 */
@ArchIgnore
public final class MinutesBitSetBuilder {

    private BitSet bitSet;

    /**
     * Default constructor.
     */
    public MinutesBitSetBuilder() {
        super();
        this.bitSet = new BitSet(1440);
    }

    /**
     * Set a single minute in the day.
     * 
     * @param minute
     *            Minute of the day (0..1439).
     * 
     * @return Builder.
     */
    public MinutesBitSetBuilder minute(final int minute) {
        Contract.requireArgMin("minute", minute, 0);
        Contract.requireArgMax("minute", minute, 1439);
        bitSet.set(minute);
        return this;
    }

    /**
     * Sets all minutes of an hour.
     * 
     * @param hour
     *            Hour of the day (0..23).
     * 
     * @return Builder.
     */
    public MinutesBitSetBuilder hour(final int hour) {
        Contract.requireArgMin("hour", hour, 0);
        Contract.requireArgMax("hour", hour, 23);
        final int start = hour * 60;
        final int end = start + 60;
        for (int i = start; i < end; i++) {
            bitSet.set(i);
        }
        return this;
    }

    /**
     * Sets a range of minutes of an hour.
     * 
     * @param hour
     *            Hour of the day (0..23).
     * @param minuteFrom
     *            Minute of the day (0..59).
     * @param minuteTo
     *            Minute of the day (0..59).
     * 
     * @return Builder.
     */
    public MinutesBitSetBuilder hourMinutes(int hour, int minuteFrom, int minuteTo) {
        Contract.requireArgMin("hour", hour, 0);
        Contract.requireArgMax("hour", hour, 23);
        Contract.requireArgMin("minuteFrom", minuteFrom, 0);
        Contract.requireArgMax("minuteFrom", minuteFrom, 59);
        Contract.requireArgMin("minuteTo", minuteTo, 0);
        Contract.requireArgMax("minuteTo", minuteTo, 59);
        if (minuteFrom > minuteTo) {
            throw new ConstraintViolationException(
                    "Value 'minuteFrom' (" + minuteFrom + ") cannot be greater than 'minuteTo' (" + minuteTo + ")");
        }

        final int start = (hour * 60) + minuteFrom;
        final int end = (hour * 60) + minuteTo;
        for (int i = start; i < end; i++) {
            bitSet.set(i);
        }
        return this;
    }

    /**
     * Sets a range of minutes from/to hour/minute.
     * 
     * @param fromHour
     *            Hour of the day (0..23).
     * @param fromMinute
     *            Minute of the day (0..59).
     * @param toHour
     *            Hour of the day (0..23).
     * @param toMinute
     *            Minute of the day (0..59).
     * 
     * @return Builder.
     */
    public MinutesBitSetBuilder fromTo(int fromHour, int fromMinute, int toHour, int toMinute) {
        Contract.requireArgMin("fromHour", fromHour, 0);
        Contract.requireArgMax("fromHour", fromHour, 24);
        Contract.requireArgMin("fromMinute", fromMinute, 0);
        Contract.requireArgMax("fromMinute", fromMinute, 59);

        Contract.requireArgMin("toHour", toHour, 0);
        Contract.requireArgMax("toHour", toHour, 24);
        Contract.requireArgMin("toMinute", toMinute, 0);
        Contract.requireArgMax("toMinute", toMinute, 59);

        if (fromHour > toHour) {
            throw new ConstraintViolationException("Value 'fromHour' (" + fromHour + ") cannot be greater than 'toHour' (" + toHour + ")");
        }

        final int start = (fromHour * 60) + fromMinute;
        final int end = (toHour * 60) + toMinute;
        for (int i = start; i < end; i++) {
            bitSet.set(i);
        }
        return this;
    }

    /**
     * Returns the current instance and sets a new one internally.
     * 
     * @return New instance.
     */
    public BitSet build() {
        final BitSet set = bitSet;
        bitSet = new BitSet(1440);
        return set;
    }

}