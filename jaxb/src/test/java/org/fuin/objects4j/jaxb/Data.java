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
package org.fuin.objects4j.jaxb;

import com.tngtech.archunit.junit.ArchIgnore;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.fuin.objects4j.core.CurrencyAmount;
import org.fuin.objects4j.core.DayOfTheWeek;
import org.fuin.objects4j.core.DayOpeningHours;
import org.fuin.objects4j.core.EmailAddress;
import org.fuin.objects4j.core.Hour;
import org.fuin.objects4j.core.HourRange;
import org.fuin.objects4j.core.HourRanges;
import org.fuin.objects4j.core.MultiDayOfTheWeek;
import org.fuin.objects4j.core.Password;
import org.fuin.objects4j.core.PasswordSha512;
import org.fuin.objects4j.core.UserName;
import org.fuin.objects4j.core.WeeklyOpeningHours;

import java.util.Currency;
import java.util.Locale;
import java.util.UUID;

/**
 * XML container used for tests.
 */
@ArchIgnore
@XmlRootElement
public class Data {

    @XmlAttribute
    public EmailAddress email;

    @XmlAttribute
    public Password password;

    @XmlAttribute
    public PasswordSha512 passwordSha512;

    @XmlAttribute
    public UserName userName;

    @XmlAttribute
    public UUID uuid;

    @XmlAttribute(name = "ca")
    public CurrencyAmount currencyAmount;

    @XmlAttribute(name = "c")
    public Currency currency;

    @XmlAttribute(name = "any-str")
    @XmlJavaTypeAdapter(AnyStrXmlAdapter.class)
    public AnyStr anyStr;

    @XmlAttribute
    public Hour hour;

    @XmlAttribute
    public HourRange hourRange;

    @XmlAttribute
    public HourRanges hourRanges;

    @XmlAttribute
    public DayOfTheWeek dayOfTheWeek;

    @XmlAttribute
    public MultiDayOfTheWeek multiDayOfTheWeek;

    @XmlAttribute
    public DayOpeningHours dayOpeningHours;

    @XmlAttribute
    public WeeklyOpeningHours weeklyOpeningHours;

    @XmlAttribute
    public Locale locale;

}
