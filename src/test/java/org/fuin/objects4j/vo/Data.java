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
package org.fuin.objects4j.vo;

import java.util.UUID;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * XML/JSON container used for tests.
 */
// CHECKSTYLE:OFF
@XmlRootElement
public class Data {

    @Valid
    @XmlAttribute
    @JsonbProperty
    public EmailAddress email;

    @Valid
    @XmlAttribute
    @JsonbProperty
    public Password password;

    @Valid
    @XmlAttribute
    @JsonbProperty
    public PasswordSha512 passwordSha512;

    @Valid
    @XmlAttribute
    @JsonbProperty
    public UserName userName;

    @Valid
    @XmlAttribute
    @JsonbProperty
    public UUID uuid;

    @Valid
    @XmlAttribute(name = "ca")
    @JsonbProperty("ca")
    public CurrencyAmount currencyAmount;

    @Valid
    @XmlAttribute(name = "any-str")
    @JsonbProperty("any-str")
    public AnyStr anyStr;

    @Valid
    @XmlAttribute
    @JsonbProperty
    public Hour hour;

    @Valid
    @XmlAttribute
    @JsonbProperty
    public HourRange hourRange;

    @Valid
    @XmlAttribute
    @JsonbProperty
    public HourRanges hourRanges;
    
    @Valid
    @XmlAttribute
    @JsonbProperty
    public DayOfTheWeek dayOfTheWeek;

    @Valid
    @XmlAttribute
    @JsonbProperty
    public MultiDayOfTheWeek multiDayOfTheWeek;
    

    @Valid
    @XmlAttribute
    @JsonbProperty
    public DayOpeningHours dayOpeningHours;
    
}
// CHECKSTYLE:ON
