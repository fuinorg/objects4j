package org.fuin.objects4j.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
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
 * Module that registers the adapters for the package.
 */
public class Objects4JJacksonAdapterModule extends Module {

    @Override
    public String getModuleName() {
        return "Objects4JModule";
    }

    @Override
    public void setupModule(Module.SetupContext context) {

        final SimpleSerializers serializers = new SimpleSerializers();
        serializers.addSerializer(new CurrencyAmountJacksonSerializer());
        serializers.addSerializer(new CurrencyJacksonSerializer());
        serializers.addSerializer(new DayOfTheWeekJacksonSerializer());
        serializers.addSerializer(new DayOpeningHoursJacksonSerializer());
        serializers.addSerializer(new EmailAddressJacksonSerializer());
        serializers.addSerializer(new HourJacksonSerializer());
        serializers.addSerializer(new HourRangeJacksonSerializer());
        serializers.addSerializer(new HourRangesJacksonSerializer());
        serializers.addSerializer(new LocaleJacksonSerializer());
        serializers.addSerializer(new MultiDayOfTheWeekJacksonSerializer());
        serializers.addSerializer(new PasswordSha512JacksonSerializer());
        serializers.addSerializer(new UserNameJacksonSerializer());
        serializers.addSerializer(new UUIDJacksonSerializer());
        serializers.addSerializer(new WeeklyOpeningHoursJacksonSerializer());
        context.addSerializers(serializers);

        final SimpleDeserializers deserializers = new SimpleDeserializers();
        deserializers.addDeserializer(CurrencyAmount.class, new CurrencyAmountJacksonDeserializer());
        deserializers.addDeserializer(Currency.class, new CurrencyJacksonDeserializer());
        deserializers.addDeserializer(DayOfTheWeek.class, new DayOfTheWeekJacksonDeserializer());
        deserializers.addDeserializer(DayOpeningHours.class, new DayOpeningHoursJacksonDeserializer());
        deserializers.addDeserializer(EmailAddress.class, new EmailAddressJacksonDeserializer());
        deserializers.addDeserializer(Hour.class, new HourJacksonDeserializer());
        deserializers.addDeserializer(HourRange.class, new HourRangeJacksonDeserializer());
        deserializers.addDeserializer(HourRanges.class, new HourRangesJacksonDeserializer());
        deserializers.addDeserializer(Locale.class, new LocaleJacksonDeserializer());
        deserializers.addDeserializer(MultiDayOfTheWeek.class, new MultiDayOfTheWeekJacksonDeserializer());
        deserializers.addDeserializer(PasswordSha512.class, new PasswordSha512JacksonDeserializer());
        deserializers.addDeserializer(UserName.class, new UserNameJacksonDeserializer());
        deserializers.addDeserializer(UUID.class, new UUIDJacksonDeserializer());
        deserializers.addDeserializer(WeeklyOpeningHours.class, new WeeklyOpeningHoursJacksonDeserializer());
        context.addDeserializers(deserializers);
    }

    @Override
    public Version version() {
        return new Version(0, 10, 0, "SNAPSHOT");
    }

}