package org.fuin.objects4j.springboot;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
import org.fuin.objects4j.jpa.CurrencyAmountAttributeConverter;
import org.fuin.objects4j.jpa.CurrencyAttributeConverter;
import org.fuin.objects4j.jpa.DayOfTheWeekAttributeConverter;
import org.fuin.objects4j.jpa.DayOpeningHoursAttributeConverter;
import org.fuin.objects4j.jpa.EmailAddressAttributeConverter;
import org.fuin.objects4j.jpa.HourAttributeConverter;
import org.fuin.objects4j.jpa.HourRangeAttributeConverter;
import org.fuin.objects4j.jpa.HourRangesAttributeConverter;
import org.fuin.objects4j.jpa.LocaleAttributeConverter;
import org.fuin.objects4j.jpa.MultiDayOfTheWeekAttributeConverter;
import org.fuin.objects4j.jpa.PasswordSha512AttributeConverter;
import org.fuin.objects4j.jpa.UUIDAttributeConverter;
import org.fuin.objects4j.jpa.UserNameAttributeConverter;
import org.fuin.objects4j.jpa.WeeklyOpeningHoursAttributeConverter;

import java.util.Currency;
import java.util.Locale;
import java.util.UUID;

@Entity(name = "DATA")
public class Data {

    @Id
    @Column(name = "ID")
    @JsonProperty
    public String id;

    @Column(name = "EMAIL", nullable = true)
    @Convert(converter = EmailAddressAttributeConverter.class)
    @JsonProperty
    public EmailAddress email;

    @Column(name = "PW", nullable = true)
    @Convert(converter = PasswordSha512AttributeConverter.class)
    @JsonProperty
    public PasswordSha512 passwordSha512;

    @Column(name = "USERNAME", nullable = true)
    @Convert(converter = UserNameAttributeConverter.class)
    @JsonProperty
    public UserName userName;

    @Column(name = "UUID", nullable = true)
    @Convert(converter = UUIDAttributeConverter.class)
    @JsonProperty
    public UUID uuid;

    @Column(name = "PRICE", nullable = true)
    @Convert(converter = CurrencyAmountAttributeConverter.class)
    @JsonProperty
    public CurrencyAmount price;

    @Column(name = "CURRENCY", nullable = true)
    @Convert(converter = CurrencyAttributeConverter.class)
    @JsonProperty
    public Currency currency;

    @Column(name = "HOUR", nullable = true)
    @Convert(converter = HourAttributeConverter.class)
    @JsonProperty
    public Hour hour;

    @Column(name = "HOUR_RANGE", nullable = true)
    @Convert(converter = HourRangeAttributeConverter.class)
    @JsonProperty
    public HourRange hourRange;

    @Column(name = "HOUR_RANGES", nullable = true)
    @Convert(converter = HourRangesAttributeConverter.class)
    @JsonProperty
    public HourRanges hourRanges;

    @Column(name = "DAY_OF_THE_WEEK", nullable = true)
    @Convert(converter = DayOfTheWeekAttributeConverter.class)
    @JsonProperty
    public DayOfTheWeek dayOfTheWeek;

    @Column(name = "MULTI_DAY_OF_THE_WEEK", nullable = true)
    @Convert(converter = MultiDayOfTheWeekAttributeConverter.class)
    @JsonProperty
    public MultiDayOfTheWeek multiDayOfTheWeek;

    @Column(name = "DAY_OPENING_HOURS", nullable = true)
    @Convert(converter = DayOpeningHoursAttributeConverter.class)
    @JsonProperty
    public DayOpeningHours dayOpeningHours;

    @Column(name = "WEEKLY_OPENING_HOURS", nullable = true)
    @Convert(converter = WeeklyOpeningHoursAttributeConverter.class)
    @JsonProperty
    public WeeklyOpeningHours weeklyOpeningHours;

    @Column(name = "LOCALE", nullable = true)
    @Convert(converter = LocaleAttributeConverter.class)
    @JsonProperty
    public Locale locale;

}
