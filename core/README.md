# objects4j-core
A library with common Java types that are mostly immutable value objects.

## Description
Provides Value Objects (immutable objects) that represents an object whose equality isn't based on identity. 
That means instances of this type are equal when they have the same value, not necessarily being the same object. 
Additionally, some helper classes (like validators) are placed in this package.

**Base classes** that allow an easy implementation of concrete classes. 
* [AbstractIntegerValueObject](src/main/java/org/fuin/objects4j/core/AbstractIntegerValueObject.java) - A value object that is based on an integer.
* [AbstractLongValueObject](src/main/java/org/fuin/objects4j/core/AbstractLongValueObject.java) - A value object that is based on a long.
* [AbstractStringValueObject](src/main/java/org/fuin/objects4j/core/AbstractStringValueObject.java) - A value object that is based on a string.
* [AbstractUuidValueObject](src/main/java/org/fuin/objects4j/core/AbstractUuidValueObject.java) - A value object that is based on a UUID.

**Predefined Value Objects**
* [CurrencyAmount](src/main/java/org/fuin/objects4j/core/CurrencyAmount.java) - Combines a currency (like "USD" or "EUR") with a big decimal value (like "**512 EUR**"). There is also a [CurrencyAmountConverter](src/main/java/org/fuin/objects4j/core/CurrencyAmountConverter.java) (JAX-B, JSON-B and JPA) and a [CurrencyAmountValidator](src/main/java/org/fuin/objects4j/core/CurrencyAmountValidator.java) (```@CurrencyAmountStr```) available.
* [EmailAddress](src/main/java/org/fuin/objects4j/core/EmailAddress.java) - Email address. There is also a [EmailAddressConverter](src/main/java/org/fuin/objects4j/core/EmailAddressConverter.java) (JAX-B, JSON-B and JPA) and a [EmailAddressValidator](src/main/java/org/fuin/objects4j/core/EmailAddressValidator.java) (```@EmailAddressStr```) available.
* [Password](src/main/java/org/fuin/objects4j/core/Password.java) - A password with a length between 8 and 20 characters. There is also a [PasswordConverter](src/main/java/org/fuin/objects4j/core/PasswordConverter.java) (JAX-B, JSON-B and JPA) and a [PasswordValidator](src/main/java/org/fuin/objects4j/core/PasswordValidator.java) (```@PasswordStr```) available.
* [PasswordSha512](src/main/java/org/fuin/objects4j/core/PasswordSha512.java) - A SHA-512 hashed password that is HEX encoded. There is also a [PasswordSha512Converter](src/main/java/org/fuin/objects4j/core/PasswordSha512Converter.java) (JAX-B, JSON-B and JPA) and a [PasswordSha512Validator](src/main/java/org/fuin/objects4j/core/PasswordSha512Validator.java) (```@PasswordSha512Str```) available.
* [UserName](src/main/java/org/fuin/objects4j/core/UserName.java) - User name with restricted character set and length. There is also a [UserNameConverter](src/main/java/org/fuin/objects4j/core/UserNameConverter.java) (JAX-B, JSON-B and JPA) and a [UserNameValidator](src/main/java/org/fuin/objects4j/core/UserNameValidator.java) (```@UserNameStr```) available.
* [Hour](src/main/java/org/fuin/objects4j/core/Hour.java) - Hour of a day like '**23:59**' (24 hours, sometimes called Military Time). There is also an [HourConverter](src/main/java/org/fuin/objects4j/core/HourConverter.java) (JAX-B, JSON-B and JPA) and an [HourStrValidator](src/main/java/org/fuin/objects4j/core/HourStrValidator.java) (```@HourStr```) available.
* [HourRange](src/main/java/org/fuin/objects4j/core/HourRange.java) - Range of hours of a day like '**00:00-24:00**' (24 hours, sometimes called Military Time). There is also an [HourRangeConverter](src/main/java/org/fuin/objects4j/core/HourRangeConverter.java) (JAX-B, JSON-B and JPA) and an [HourRangeStrValidator](src/main/java/org/fuin/objects4j/core/HourRangeStrValidator.java) (```@HourRangeStr```) available.
* [HourRanges](src/main/java/org/fuin/objects4j/core/HourRanges.java) - Multiple range of hours of a day like '**09:00-12:00+13:00-17:00**'. There is also an [HourRangesConverter](src/main/java/org/fuin/objects4j/core/HourRangesConverter.java) (JAX-B, JSON-B and JPA) and an [HourRangesStrValidator](src/main/java/org/fuin/objects4j/core/HourRangesStrValidator.java) (```@HourRangesStr```) available.
* [DayOfTheWeek](src/main/java/org/fuin/objects4j/core/DayOfTheWeek.java) - The days of the week '**Mon**'-'**Sun**'(from Monday to Sunday) plus '**PH**' (Public Holiday). There is also an [DayOfTheWeekConverter](src/main/java/org/fuin/objects4j/core/DayOfTheWeekConverter.java) (JAX-B, JSON-B and JPA) and an [DayOfTheWeekStrValidator](src/main/java/org/fuin/objects4j/core/DayOfTheWeekStrValidator.java) (```@DayOfTheWeekStr```) available.
* [MultiDayOfTheWeek](src/main/java/org/fuin/objects4j/core/MultiDayOfTheWeek.java) - Multiple days of the week like '**Mon/Tue/Wed-Fri/PH**' (Monday AND Tuesday AND Wednesday TO Friday AND Public Holidays). There is also an [MultiDayOfTheWeekConverter](src/main/java/org/fuin/objects4j/core/MultiDayOfTheWeekConverter.java) (JAX-B, JSON-B and JPA) and an [MultiDayOfTheWeekStrValidator](src/main/java/org/fuin/objects4j/core/MultiDayOfTheWeekStrValidator.java) (```@MultiDayOfTheWeekStr```) available.
* [DayOpeningHours](src/main/java/org/fuin/objects4j/core/DayOpeningHours.java) - Multiple range of hours of single a day like '**Mon 09:00-12:00+13:00-17:00**'. There is also an [DayOpeningHoursConverter](src/main/java/org/fuin/objects4j/core/DayOpeningHoursConverter.java) (JAX-B, JSON-B and JPA) and an [DayOpeningHoursStrValidator](src/main/java/org/fuin/objects4j/core/DayOpeningHoursStrValidator.java) (```@DayOpeningHoursStr```) available.
* [WeeklyOpeningHours](src/main/java/org/fuin/objects4j/core/WeeklyOpeningHours.java) - weekly opening hours separated by a comma like '**Mon-Fri 09:00-12:00+13:00-17:00,Sat/Sun 09:-12:00**'. There is also an [WeeklyOpeningHoursConverter](src/main/java/org/fuin/objects4j/core/WeeklyOpeningHoursConverter.java) (JAX-B, JSON-B and JPA) and an [WeeklyOpeningHoursStrValidator](src/main/java/org/fuin/objects4j/core/WeeklyOpeningHoursStrValidator.java) (```@WeeklyOpeningHoursStr```) available.

**Validators**
* [DateStrValidator](src/main/java/org/fuin/objects4j/core/DateStrValidator.java) - Validates that a string is a date using the current locale ([@DateStr](src/main/java/org/fuin/objects4j/core/DateStr.java)).
* [LocaleStrValidator](src/main/java/org/fuin/objects4j/core/LocaleStrValidator.java) - Validates that a string is a valid locale ([@LocaleStr](src/main/java/org/fuin/objects4j/core/LocaleStr.java)). 
* [PropertiesContainValidator](src/main/java/org/fuin/objects4j/core/PropertiesContainValidator.java) - Validates that a string is one out of a list of constants ([@PropertiesContain("one", "two", "three")](src/main/java/org/fuin/objects4j/core/PropertiesContain.java)).
* [TrimmedNotEmptyValidator](src/main/java/org/fuin/objects4j/core/TrimmedNotEmptyValidator.java) - Validates that a string is not empty after it was trimmed [@TrimmedNotEmpty](src/main/java/org/fuin/objects4j/core/TrimmedNotEmpty.java)
* [UUIDStrValidator](src/main/java/org/fuin/objects4j/core/UUIDStrValidator.java) - Validates that a string is a valid UUID ([@UUIDStr](src/main/java/org/fuin/objects4j/core/UUIDStr.java)). 

**Other**
* [KeyValue](src/main/java/org/fuin/objects4j/core/KeyValue.java) - Container for a key (String) and a value (Object).
