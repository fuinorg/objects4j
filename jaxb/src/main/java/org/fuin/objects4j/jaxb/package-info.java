@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(value = CurrencyAmountConverter.class, type = CurrencyAmount.class),
        @XmlJavaTypeAdapter(value = CurrencyConverter.class, type = Currency.class),
        @XmlJavaTypeAdapter(value = DayOfTheWeekConverter.class, type = DayOfTheWeek.class),
        @XmlJavaTypeAdapter(value = DayOpeningHoursConverter.class, type = DayOpeningHours.class),
        @XmlJavaTypeAdapter(value = EmailAddressConverter.class, type = EmailAddress.class),
        @XmlJavaTypeAdapter(value = HourConverter.class, type = Hour.class),
        @XmlJavaTypeAdapter(value = HourRangeConverter.class, type = HourRange.class),
        @XmlJavaTypeAdapter(value = HourRangesConverter.class, type = HourRanges.class),
        @XmlJavaTypeAdapter(value = LocaleConverter.class, type = Locale.class),
        @XmlJavaTypeAdapter(value = MultiDayOfTheWeekConverter.class, type = MultiDayOfTheWeek.class),
        @XmlJavaTypeAdapter(value = PasswordConverter.class, type = Password.class),
        @XmlJavaTypeAdapter(value = PasswordSha512Converter.class, type = PasswordSha512.class),
        @XmlJavaTypeAdapter(value = UserNameConverter.class, type = UserName.class),
        @XmlJavaTypeAdapter(value = UUIDConverter.class, type = UUID.class),
        @XmlJavaTypeAdapter(value = WeeklyOpeningHoursConverter.class, type = WeeklyOpeningHours.class)
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
