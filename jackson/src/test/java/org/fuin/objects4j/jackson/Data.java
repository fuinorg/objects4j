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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

    @JsonSerialize(using = EmailAddressJacksonSerializer.class)
    @JsonDeserialize(using = EmailAddressJacksonDeserializer.class)
    public EmailAddress email;

    @JsonSerialize(using = PasswordSha512JacksonSerializer.class)
    @JsonDeserialize(using = PasswordSha512JacksonDeserializer.class)
    public PasswordSha512 passwordSha512;

    @JsonSerialize(using = UserNameJacksonSerializer.class)
    @JsonDeserialize(using = UserNameJacksonDeserializer.class)
    public UserName userName;

    @JsonSerialize(using = UUIDJacksonSerializer.class)
    @JsonDeserialize(using = UUIDJacksonDeserializer.class)
    public UUID uuid;

    @JsonProperty("ca")
    @JsonSerialize(using = CurrencyAmountJacksonSerializer.class)
    @JsonDeserialize(using = CurrencyAmountJacksonDeserializer.class)
    public CurrencyAmount currencyAmount;

    @JsonProperty("c")
    @JsonSerialize(using = CurrencyJacksonSerializer.class)
    @JsonDeserialize(using = CurrencyJacksonDeserializer.class)
    public Currency currency;

    @JsonProperty("any-str")
    @JsonSerialize(using = AnyStrJacksonSerializer.class)
    @JsonDeserialize(using = AnyStrJacksonDeserializer.class)
    public AnyStr anyStr;

    @JsonSerialize(using = HourJacksonSerializer.class)
    @JsonDeserialize(using = HourJacksonDeserializer.class)
    public Hour hour;

    @JsonSerialize(using = HourRangeJacksonSerializer.class)
    @JsonDeserialize(using = HourRangeJacksonDeserializer.class)
    public HourRange hourRange;

    @JsonSerialize(using = HourRangesJacksonSerializer.class)
    @JsonDeserialize(using = HourRangesJacksonDeserializer.class)
    public HourRanges hourRanges;

    @JsonSerialize(using = DayOfTheWeekJacksonSerializer.class)
    @JsonDeserialize(using = DayOfTheWeekJacksonDeserializer.class)
    public DayOfTheWeek dayOfTheWeek;

    @JsonSerialize(using = MultiDayOfTheWeekJacksonSerializer.class)
    @JsonDeserialize(using = MultiDayOfTheWeekJacksonDeserializer.class)
    public MultiDayOfTheWeek multiDayOfTheWeek;

    @JsonSerialize(using = DayOpeningHoursJacksonSerializer.class)
    @JsonDeserialize(using = DayOpeningHoursJacksonDeserializer.class)
    public DayOpeningHours dayOpeningHours;

    @JsonSerialize(using = WeeklyOpeningHoursJacksonSerializer.class)
    @JsonDeserialize(using = WeeklyOpeningHoursJacksonDeserializer.class)
    public WeeklyOpeningHours weeklyOpeningHours;

    @JsonSerialize(using = LocaleJacksonSerializer.class)
    @JsonDeserialize(using = LocaleJacksonDeserializer.class)
    public Locale locale;

}
