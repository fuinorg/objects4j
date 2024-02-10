# objects4j-core
A library with common Java types that are mostly immutable value objects.

## Description
Provides Value Objects (immutable objects) that represents an object whose equality isn't based on identity. 
That means instances of this type are equal when they have the same value, not necessarily being the same object. 
Additionally, some helper classes (like validators) are placed in this package.

**Tip**: Starting with Java 14 [records](https://docs.oracle.com/en/java/javase/14/language/records.html) are also a good choice for defining Value Objects. 

## Base classes** that allow an easy implementation of concrete classes. 
* [AbstractIntegerValueObject](src/main/java/org/fuin/objects4j/core/AbstractIntegerValueObject.java) - A value object that is based on an integer.
* [AbstractLongValueObject](src/main/java/org/fuin/objects4j/core/AbstractLongValueObject.java) - A value object that is based on a long.
* [AbstractStringValueObject](src/main/java/org/fuin/objects4j/core/AbstractStringValueObject.java) - A value object that is based on a string.
* [AbstractUuidValueObject](src/main/java/org/fuin/objects4j/core/AbstractUuidValueObject.java) - A value object that is based on a UUID.

## Predefined Value Objects
You can find different kind of converters for all value objects in the other modules:
- Jackson [serializer/deserializer](../jackson)
- JAX-B [adapter](../jaxb)
- JPA [attribute converter](../jpa)
- JSON-B [adapter](../jsonb)

### Opening Hours
Classes allowing a structured textual description of opening hours (The time during which a business or organization is open for customers or visitors).

For example shop opening hours may be defined like this: 

`Mon-Fri 09:00-12:00+13:00-17:00,Sat/Sun 09:-12:00`

| Type                                                                                | Example                                                                 | Description                                                                                    |
|-------------------------------------------------------------------------------------|-------------------------------------------------------------------------|------------------------------------------------------------------------------------------------|
| [Hour](src/main/java/org/fuin/objects4j/core/Hour.java)                             | **23:59**                                                               | Hour of a day  (24 hours, sometimes called Military Time)                                      |
| [HourRange](src/main/java/org/fuin/objects4j/core/HourRange.java)                   | **00:00-24:00**                                                         | Range of hours of a day (24 hours, sometimes called Military Time)                             |
| [HourRanges](src/main/java/org/fuin/objects4j/core/HourRanges.java)                 | **09:00-12:00+13:00-17:00**                                             | Multiple range of hours of a day                                                               |
| [DayOfTheWeek](src/main/java/org/fuin/objects4j/core/DayOfTheWeek.java)             | **Mon**, **Tue**, **Wed**, **Thu**, **Fri**, **Sat**, **Sun** / '**PH** | The days of the week (from Monday to Sunday) plus Public Holiday                               |
| [MultiDayOfTheWeek](src/main/java/org/fuin/objects4j/core/MultiDayOfTheWeek.java)   | **Mon/Tue/Wed-Fri/PH**                                                  | Multiple days of the week like  Monday AND Tuesday AND Wednesday TO Friday AND Public Holidays |
| [DayOpeningHours](src/main/java/org/fuin/objects4j/core/DayOpeningHours.java)       | **Mon 09:00-12:00+13:00-17:00**                                         | Multiple range of hours of single a day                                                        |
| [WeeklyOpeningHours](src/main/java/org/fuin/objects4j/core/WeeklyOpeningHours.java) | **Mon-Fri 09:00-12:00+13:00-17:00,Sat/Sun 09:-12:00**                   | weekly opening hours separated by a comma                                                      |

### General Types
| Type                                                                        | Example                            | Description                                                        |
|-----------------------------------------------------------------------------|------------------------------------|--------------------------------------------------------------------|
| [CurrencyAmount](src/main/java/org/fuin/objects4j/core/CurrencyAmount.java) | **512 EUR**                        | Combines a currency (like "USD" or "EUR") with a big decimal value |
| [EmailAddress](src/main/java/org/fuin/objects4j/core/EmailAddress.java)     | **peter.parker@nowhere-no-no.com** | Email address                                                      |
| [Password](src/main/java/org/fuin/objects4j/core/Password.java)             | -                                  | A password with a length between 8 and 20 characters               |
| [PasswordSha512](src/main/java/org/fuin/objects4j/core/PasswordSha512.java) | -                                  | A SHA-512 hashed password that is HEX encoded                      |
| [UserName](src/main/java/org/fuin/objects4j/core/UserName.java)             | **peter-1_a**                      | Username with restricted character set and length                  |

## Validators
* [DateStrValidator](src/main/java/org/fuin/objects4j/core/DateStrValidator.java) - Validates that a string is a date using the current locale
  ```java
  @DateStr // Verifies X
  private String dateStr;
  ```
* [LocaleStrValidator](src/main/java/org/fuin/objects4j/core/LocaleStrValidator.java) - Validates that a string is a valid locale 
  ```java
  @LocaleStr // Verifies that the String is a valid locale
  private String name;
  ```
* [PropertiesContainValidator](src/main/java/org/fuin/objects4j/core/PropertiesContainValidator.java) - Validates that a properties contains **at least** the expected properties, but they can contain more keys.
  ```java
  @PropertiesContain({ "a", "b" }) // These key are expected in the properties 
  private Properties props;
  ```
* [TrimmedNotEmptyValidator](src/main/java/org/fuin/objects4j/core/TrimmedNotEmptyValidator.java) - Validates that a string is not empty after it was trimmed
  ```java
  @TrimmedNotEmpty // Not null and has at least one character
  private String name;
  ```
* [UUIDStrValidator](src/main/java/org/fuin/objects4j/core/UUIDStrValidator.java) - Must be a valid UUID 
  ```java
  @UUIDStr // Verifies that the String is a valid UUID
  private String uuid;
  ```

## Other
* [KeyValue](src/main/java/org/fuin/objects4j/core/KeyValue.java) - Container for a key (String) and a value (Object).
  ```java
  KeyValue kv = new KeyValue("millis", 1000);
  ```
