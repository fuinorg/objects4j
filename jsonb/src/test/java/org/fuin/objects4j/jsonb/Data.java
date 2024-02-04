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
package org.fuin.objects4j.jsonb;

import com.tngtech.archunit.junit.ArchIgnore;
import jakarta.json.bind.annotation.JsonbProperty;
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

import java.util.Currency;
import java.util.Locale;
import java.util.UUID;

/**
 * JSON container used for tests.
 */
@ArchIgnore
public class Data {

    @JsonbProperty
    public EmailAddress email;

    @JsonbProperty
    public PasswordSha512 passwordSha512;

    @JsonbProperty
    public UserName userName;

    @JsonbProperty
    public UUID uuid;

    @JsonbProperty("ca")
    public CurrencyAmount currencyAmount;

    @JsonbProperty("c")
    public Currency currency;

    @JsonbProperty("any-str")
    public AnyStr anyStr;

    @JsonbProperty
    public Hour hour;

    @JsonbProperty
    public HourRange hourRange;

    @JsonbProperty
    public HourRanges hourRanges;

    @JsonbProperty
    public DayOfTheWeek dayOfTheWeek;

    @JsonbProperty
    public MultiDayOfTheWeek multiDayOfTheWeek;

    @JsonbProperty
    public DayOpeningHours dayOpeningHours;

    @JsonbProperty
    public WeeklyOpeningHours weeklyOpeningHours;

    @JsonbProperty
    public Locale locale;

}
