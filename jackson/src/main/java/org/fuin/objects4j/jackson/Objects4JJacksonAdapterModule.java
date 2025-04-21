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
        serializers.addSerializer(new ValueObjectStringJacksonSerializer<>(CurrencyAmount.class));
        serializers.addSerializer(new CurrencyJacksonSerializer());
        serializers.addSerializer(new ValueObjectStringJacksonSerializer<>(DayOfTheWeek.class));
        serializers.addSerializer(new ValueObjectStringJacksonSerializer<>(DayOpeningHours.class));
        serializers.addSerializer(new ValueObjectStringJacksonSerializer<>(EmailAddress.class));
        serializers.addSerializer(new ValueObjectStringJacksonSerializer<>(Hour.class));
        serializers.addSerializer(new ValueObjectStringJacksonSerializer<>(HourRange.class));
        serializers.addSerializer(new ValueObjectStringJacksonSerializer<>(HourRanges.class));
        serializers.addSerializer(new LocaleJacksonSerializer());
        serializers.addSerializer(new ValueObjectStringJacksonSerializer<>(MultiDayOfTheWeek.class));
        serializers.addSerializer(new ValueObjectStringJacksonSerializer<>(PasswordSha512.class));
        serializers.addSerializer(new ValueObjectStringJacksonSerializer<>(UserName.class));
        serializers.addSerializer(new UUIDJacksonSerializer());
        serializers.addSerializer(new ValueObjectStringJacksonSerializer<>(WeeklyOpeningHours.class));
        context.addSerializers(serializers);

        final SimpleDeserializers deserializers = new SimpleDeserializers();
        deserializers.addDeserializer(CurrencyAmount.class, new ValueObjectStringJacksonDeserializer<>(CurrencyAmount.class, CurrencyAmount::valueOf));
        deserializers.addDeserializer(Currency.class, new CurrencyJacksonDeserializer());
        deserializers.addDeserializer(DayOfTheWeek.class, new ValueObjectStringJacksonDeserializer<>(DayOfTheWeek.class, DayOfTheWeek::valueOf));
        deserializers.addDeserializer(DayOpeningHours.class, new ValueObjectStringJacksonDeserializer<>(DayOpeningHours.class, DayOpeningHours::valueOf));
        deserializers.addDeserializer(EmailAddress.class, new ValueObjectStringJacksonDeserializer<>(EmailAddress.class, EmailAddress::valueOf));
        deserializers.addDeserializer(Hour.class, new ValueObjectStringJacksonDeserializer<>(Hour.class, Hour::valueOf));
        deserializers.addDeserializer(HourRange.class, new ValueObjectStringJacksonDeserializer<>(HourRange.class, HourRange::valueOf));
        deserializers.addDeserializer(HourRanges.class, new ValueObjectStringJacksonDeserializer<>(HourRanges.class, HourRanges::valueOf));
        deserializers.addDeserializer(Locale.class, new LocaleJacksonDeserializer());
        deserializers.addDeserializer(MultiDayOfTheWeek.class, new ValueObjectStringJacksonDeserializer<>(MultiDayOfTheWeek.class, MultiDayOfTheWeek::valueOf));
        deserializers.addDeserializer(PasswordSha512.class, new ValueObjectStringJacksonDeserializer<>(PasswordSha512.class, PasswordSha512::valueOf));
        deserializers.addDeserializer(UserName.class, new ValueObjectStringJacksonDeserializer<>(UserName.class, UserName::valueOf));
        deserializers.addDeserializer(UUID.class, new UUIDJacksonDeserializer());
        deserializers.addDeserializer(WeeklyOpeningHours.class, new ValueObjectStringJacksonDeserializer<>(WeeklyOpeningHours.class, WeeklyOpeningHours::valueOf));
        context.addDeserializers(deserializers);
    }

    @Override
    public Version version() {
        // Don't forget to change from release to SNAPSHOT and back!
        return new Version(0, 11, 0, "SNAPSHOT");
    }

}