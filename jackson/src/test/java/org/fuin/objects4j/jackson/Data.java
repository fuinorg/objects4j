/**
 * Copyright (C) 2013 Future Invent Informationsmanagement GmbH. All rights
 * reserved. <http://www.fuin.org/>
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fuin.objects4j.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.fuin.objects4j.core.CurrencyAmount;
import org.fuin.objects4j.core.DayOfTheWeek;
import org.fuin.objects4j.core.DayOpeningHours;
import org.fuin.objects4j.core.EmailAddress;
import org.fuin.objects4j.core.Hour;
import org.fuin.objects4j.core.HourRange;
import org.fuin.objects4j.core.HourRanges;
import org.fuin.objects4j.core.MultiDayOfTheWeek;
import org.fuin.objects4j.core.PasswordSha512;
import org.fuin.objects4j.core.UserName;
import org.fuin.objects4j.core.WeeklyOpeningHours;
import org.fuin.utils4j.TestOmitted;

import java.util.Currency;
import java.util.Locale;
import java.util.UUID;

/**
 * JSON container used for tests.
 */
@TestOmitted("Only a test class")
public class Data {

    public EmailAddress email;

    public PasswordSha512 passwordSha512;

    public UserName userName;

    public UUID uuid;

    @JsonProperty("ca")
    public CurrencyAmount currencyAmount;

    @JsonProperty("c")
    public Currency currency;

    public Hour hour;

    public HourRange hourRange;

    public HourRanges hourRanges;

    public DayOfTheWeek dayOfTheWeek;

    public MultiDayOfTheWeek multiDayOfTheWeek;

    public DayOpeningHours dayOpeningHours;

    public WeeklyOpeningHours weeklyOpeningHours;

    public Locale locale;

}
