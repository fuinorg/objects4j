@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(value = CurrencyAmountXmlAdapter.class, type = CurrencyAmount.class),
        @XmlJavaTypeAdapter(value = CurrencyXmlAdapter.class, type = Currency.class),
        @XmlJavaTypeAdapter(value = DayOfTheWeekXmlAdapter.class, type = DayOfTheWeek.class),
        @XmlJavaTypeAdapter(value = DayOpeningHoursXmlAdapter.class, type = DayOpeningHours.class),
        @XmlJavaTypeAdapter(value = EmailAddressXmlAdapter.class, type = EmailAddress.class),
        @XmlJavaTypeAdapter(value = HourXmlAdapter.class, type = Hour.class),
        @XmlJavaTypeAdapter(value = HourRangeXmlAdapter.class, type = HourRange.class),
        @XmlJavaTypeAdapter(value = HourRangesXmlAdapter.class, type = HourRanges.class),
        @XmlJavaTypeAdapter(value = LocaleXmlAdapter.class, type = Locale.class),
        @XmlJavaTypeAdapter(value = MultiDayOfTheWeekXmlAdapter.class, type = MultiDayOfTheWeek.class),
        @XmlJavaTypeAdapter(value = PasswordXmlAdapter.class, type = Password.class),
        @XmlJavaTypeAdapter(value = PasswordSha512XmlAdapter.class, type = PasswordSha512.class),
        @XmlJavaTypeAdapter(value = UserNameXmlAdapter.class, type = UserName.class),
        @XmlJavaTypeAdapter(value = UUIDXmlAdapter.class, type = UUID.class),
        @XmlJavaTypeAdapter(value = WeeklyOpeningHoursXmlAdapter.class, type = WeeklyOpeningHours.class)
})
package org.fuin.objects4j.jaxb;

import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
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
