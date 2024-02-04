package org.fuin.objects4j.quarkus;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
import org.fuin.objects4j.core.PasswordSha512;
import org.fuin.objects4j.core.UserName;
import org.fuin.objects4j.core.WeeklyOpeningHours;
import org.fuin.objects4j.jaxb.CurrencyAmountXmlAdapter;
import org.fuin.objects4j.jaxb.CurrencyXmlAdapter;
import org.fuin.objects4j.jaxb.DayOfTheWeekXmlAdapter;
import org.fuin.objects4j.jaxb.DayOpeningHoursXmlAdapter;
import org.fuin.objects4j.jaxb.EmailAddressXmlAdapter;
import org.fuin.objects4j.jaxb.HourRangeXmlAdapter;
import org.fuin.objects4j.jaxb.HourRangesXmlAdapter;
import org.fuin.objects4j.jaxb.HourXmlAdapter;
import org.fuin.objects4j.jaxb.LocaleXmlAdapter;
import org.fuin.objects4j.jaxb.MultiDayOfTheWeekXmlAdapter;
import org.fuin.objects4j.jaxb.PasswordSha512XmlAdapter;
import org.fuin.objects4j.jaxb.UUIDXmlAdapter;
import org.fuin.objects4j.jaxb.UserNameXmlAdapter;
import org.fuin.objects4j.jaxb.WeeklyOpeningHoursXmlAdapter;
import org.fuin.objects4j.jsonb.CurrencyAmountJsonbAdapter;
import org.fuin.objects4j.jsonb.CurrencyJsonbAdapter;
import org.fuin.objects4j.jsonb.DayOfTheWeekJsonbAdapter;
import org.fuin.objects4j.jsonb.DayOpeningHoursJsonbAdapter;
import org.fuin.objects4j.jsonb.EmailAddressJsonbAdapter;
import org.fuin.objects4j.jsonb.HourJsonbAdapter;
import org.fuin.objects4j.jsonb.HourRangeJsonbAdapter;
import org.fuin.objects4j.jsonb.HourRangesJsonbAdapter;
import org.fuin.objects4j.jsonb.LocaleJsonbAdapter;
import org.fuin.objects4j.jsonb.MultiDayOfTheWeekJsonbAdapter;
import org.fuin.objects4j.jsonb.PasswordJsonbAdapter;
import org.fuin.objects4j.jsonb.PasswordSha512JsonbAdapter;
import org.fuin.objects4j.jsonb.UUIDJsonbAdapter;
import org.fuin.objects4j.jsonb.UserNameJsonbAdapter;
import org.fuin.objects4j.jsonb.WeeklyOpeningHoursJsonbAdapter;

import java.util.Currency;
import java.util.Locale;
import java.util.UUID;

@Entity(name = "DATA")
@XmlRootElement
public class Data {

    @Id
    @Column(name = "ID")
    @JsonbProperty
    @XmlAttribute
    public String id;

    @Column(name = "EMAIL", nullable = true)
    @JsonbProperty
    @JsonbTypeAdapter(EmailAddressJsonbAdapter.class)
    @XmlAttribute
    @XmlJavaTypeAdapter(EmailAddressXmlAdapter.class)
    public EmailAddress email;

    @Column(name = "PW", nullable = true)
    @JsonbProperty
    @JsonbTypeAdapter(PasswordSha512JsonbAdapter.class)
    @XmlAttribute
    @XmlJavaTypeAdapter(PasswordSha512XmlAdapter.class)
    public PasswordSha512 passwordSha512;

    @Column(name = "USERNAME", nullable = true)
    @JsonbProperty
    @JsonbTypeAdapter(UserNameJsonbAdapter.class)
    @XmlAttribute
    @XmlJavaTypeAdapter(UserNameXmlAdapter.class)
    public UserName userName;

    @Column(name = "UUID", nullable = true)
    @JsonbProperty
    @JsonbTypeAdapter(UUIDJsonbAdapter.class)
    @XmlAttribute
    @XmlJavaTypeAdapter(UUIDXmlAdapter.class)
    public UUID uuid;

/*
    TODO CurrencyAmount requires an annotation @Embeddable but it should have no dependency to JPA as it's in CORE
    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "AMOUNT", precision = 12, scale = 2, nullable = true))
    @AttributeOverride(name = "currency", column = @Column(name = "CURRENCY", columnDefinition = "varchar(255)", nullable = true))
    @JsonbProperty
    @JsonbTypeAdapter(CurrencyAmountJsonbAdapter.class)
    @XmlAttribute
    @XmlJavaTypeAdapter(CurrencyAmountXmlAdapter.class)
    public CurrencyAmount amount;
*/
    @Column(name = "PRICE", nullable = true)
    @Basic
    @JsonbProperty
    @JsonbTypeAdapter(CurrencyAmountJsonbAdapter.class)
    @XmlAttribute
    @XmlJavaTypeAdapter(CurrencyAmountXmlAdapter.class)
    public CurrencyAmount price;

    @Column(name = "CURRENCY", nullable = true)
    @Basic
    @JsonbProperty
    @JsonbTypeAdapter(CurrencyJsonbAdapter.class)
    @XmlAttribute
    @XmlJavaTypeAdapter(CurrencyXmlAdapter.class)
    public Currency currency;

    @Column(name = "HOUR", nullable = true)
    @JsonbProperty
    @JsonbTypeAdapter(HourJsonbAdapter.class)
    @XmlAttribute
    @XmlJavaTypeAdapter(HourXmlAdapter.class)
    public Hour hour;

    @Column(name = "HOUR_RANGE", nullable = true)
    @JsonbProperty
    @JsonbTypeAdapter(HourRangeJsonbAdapter.class)
    @XmlAttribute
    @XmlJavaTypeAdapter(HourRangeXmlAdapter.class)
    public HourRange hourRange;

    @Column(name = "HOUR_RANGES", nullable = true)
    @JsonbProperty
    @JsonbTypeAdapter(HourRangesJsonbAdapter.class)
    @XmlAttribute
    @XmlJavaTypeAdapter(HourRangesXmlAdapter.class)
    public HourRanges hourRanges;

    @Column(name = "DAY_OF_THE_WEEK", nullable = true)
    @JsonbProperty
    @JsonbTypeAdapter(DayOfTheWeekJsonbAdapter.class)
    @XmlAttribute
    @XmlJavaTypeAdapter(DayOfTheWeekXmlAdapter.class)
    public DayOfTheWeek dayOfTheWeek;

    @Column(name = "MULTI_DAY_OF_THE_WEEK", nullable = true)
    @JsonbProperty
    @JsonbTypeAdapter(MultiDayOfTheWeekJsonbAdapter.class)
    @XmlAttribute
    @XmlJavaTypeAdapter(MultiDayOfTheWeekXmlAdapter.class)
    public MultiDayOfTheWeek multiDayOfTheWeek;

    @Column(name = "DAY_OPENING_HOURS", nullable = true)
    @JsonbProperty
    @JsonbTypeAdapter(DayOpeningHoursJsonbAdapter.class)
    @XmlAttribute
    @XmlJavaTypeAdapter(DayOpeningHoursXmlAdapter.class)
    public DayOpeningHours dayOpeningHours;

    @Column(name = "WEEKLY_OPENING_HOURS", nullable = true)
    @JsonbProperty
    @JsonbTypeAdapter(WeeklyOpeningHoursJsonbAdapter.class)
    @XmlAttribute
    @XmlJavaTypeAdapter(WeeklyOpeningHoursXmlAdapter.class)
    public WeeklyOpeningHours weeklyOpeningHours;

    @Column(name = "LOCALE", nullable = true)
    @JsonbProperty
    @JsonbTypeAdapter(LocaleJsonbAdapter.class)
    @XmlAttribute
    @XmlJavaTypeAdapter(LocaleXmlAdapter.class)
    public Locale locale;

}
