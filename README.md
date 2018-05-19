objects4j
=========

A library with common Java types that are mostly immutable value objects.

[![Build Status](https://fuin-org.ci.cloudbees.com/job/objects4j/badge/icon)](https://fuin-org.ci.cloudbees.com/job/objects4j/)
[![Coverage Status](https://sonarcloud.io/api/project_badges/measure?project=org.fuin%3Aobjects4j&metric=coverage)](https://sonarcloud.io/dashboard?id=org.fuin%3Aobjects4j)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.fuin/objects4j/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.fuin/objects4j/)
[![LGPLv3 License](http://img.shields.io/badge/license-LGPLv3-blue.svg)](https://www.gnu.org/licenses/lgpl.html)
[![Java Development Kit 1.8](https://img.shields.io/badge/JDK-1.8-green.svg)](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

<a href="https://fuin-org.ci.cloudbees.com/job/objects4j"><img src="http://www.fuin.org/images/Button-Built-on-CB-1.png" width="213" height="72" border="0" alt="Built on CloudBees"/></a>

# Features

## Package "common"
Base classes like utilities and general annotations.

* [FileExists Validator](#fileexists-validator)
* [IsDirectory Validator](#isdirectory-validator)
* [TraceStringCapable](#tracestringcapable)

## Package "ui"
Annotations that can be placed on plain objects but may be used by a user interface to render that object in some way.

* [Label annotation](#label-annotation)
* [Prompt annotation](#prompt-annotation)
* [ShortLabel annotation](#shortlabel-annotation)
* [TableColumn annotation](#tablecolumn-annotation)
* [TextField annotation](#textfield-annotation)
* [Tooltip annotation](#tooltip-annotation)

## Package "vo"
Provides Value Objects and utility classes for those immutable objects. 

[Click here for details...](#description-vo)

* * *

## Description "common"
Base classes like utilities and general annotations.

### FileExists Validator
Verifies that the file exists (Bean validation JSR 303).
```Java
@FileExists
private File myFile;
```

### IsDirectory Validator
Verifies that it's a directory and not a file (Bean validation JSR 303).
```Java
@IsDirectory
private File myDir;
```

### TraceStringCapable
Marker interface for classes that provide a special representation for trace output.
```Java
public class MyClass implements TraceStringCapable {

   @Override
   public String toTraceString() {
       return "A long and very detailed instance description for trace output"; 
   }
   
}
```

## Description "ui"
The annotations may be used by UI elements to display an appropriate text for a type or a field.
You can also configure a bundle and key for internationalization.

### Label annotation
Use this annotation to assign a label to a class or an attribute.
```Java
@Label(value = "Birthday", bundle = "my/MyBundle", key = "birthday.label")
private Date birthday;
```

### Prompt annotation
Use this annotation to assign a prompt (example value) to a class or an attribute.
```Java
@Prompt(value = "12-31-1993", bundle = "my/MyBundle", key = "birthday.prompt")
private Date birthday;
```

### ShortLabel annotation
Use this annotation to assign an abbreviation to a class or an attribute.
```Java
@ShortLabel(value = "BD", bundle = "my/MyBundle", key = "birthday.short")
private Date birthday;
```

### TableColumn annotation
Use this annotation to assign preferred table column values to a field.
```Java
@TableColumn(pos = 3, width = 20, unit= FontSizeUnit.EM, getter = "isInternal")
private boolean permanentEmployee;
```

### TextField annotation
Use this annotation to express the annotated attribute should be rendered as a text field. 
User agents are expected to use this information as hint for the rendering process.
```Java
@TextField(width = 50)
private String lastName;
```

### Tooltip annotation
Use this annotation to assign a tooltip to a class or a field.
```Java
@Tooltip(value = "The person's birthday", bundle = "my/MyBundle", key = "birthday.tooltip")
private Date birthday;
```

### AnnotationAnalyzer
You can use [AnnotationAnalyzer](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/ui/AnnotationAnalyzer.java) to read the internationalized text from above annotations. 

Read a single ```@Label``` text:
```Java
AnnotationAnalyzer analyzer = new AnnotationAnalyzer();

Field field = MyClass.class.getDeclaredField("birthday");

FieldTextInfo labelInfoGermany = analyzer.createFieldInfo(field, Locale.GERMANY, Label.class);        
System.out.println(labelInfoGermany.getText());
// Prints "Geburtstag"

FieldTextInfo labelInfoUs = analyzer.createFieldInfo(field, Locale.US, Label.class);        
System.out.println(labelInfoUs.getText());
// Prints "Birthday"
```

Read all ```@Label``` texts from a class:
```Java
AnnotationAnalyzer analyzer = new AnnotationAnalyzer();
List<FieldTextInfo> infos = analyzer.createFieldInfos(MyClass.class, Locale.US, Label.class);
for (final FieldTextInfo info : infos) {
	// Text of the label or the name of the field if the text is null
	System.out.println(info.getTextOrField());
}
```

Create a list of [TableColumnInfo](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/ui/TableColumnInfo.java) 
that combines [@Label](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/ui/Label.java), 
[@ShortLabel](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/ui/ShortLabel.java) and 
[@TableColumn](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/ui/TableColumn.java) annotations. 
```Java
List<TableColumnInfo> columns = TableColumnInfo.create(MyClass.class, Locale.US);
for (TableColumnInfo column : columns) {
    System.out.println(column.getText());
}
```

This allows easy creation of a table model for example for JavaFX:
```Java
TableView<MyClass> tableView = new TableView<>();
List<TableColumnInfo> tableCols = TableColumnInfo.create(MyClass.class, Locale.getDefault());
Collections.sort(tableCols);
for (TableColumnInfo column : tableCols) {
    TableColumn<MyClass, String> tc = new TableColumn<>();
    tc.setStyle("-fx-alignment: CENTER;");
    Label label = new Label(column.getShortText());
    label.setTooltip(new Tooltip(column.getTooltip()));
    tc.setGraphic(label);
    tc.setCellValueFactory(new PropertyValueFactory<MyClass, String>(column.getField().getName()));
    tc.setPrefWidth(column.getWidth().getSize());
    tableView.getColumns().add(tc);
}
```


## Description "vo"
Provides Value Objects (immutable objects) that represents an object whose equality isn't based on identity. 
That means instances of this type are equal when they have the same value, not necessarily being the same object. 
Additionally some helper classes (like validators) are placed in this package.

**Interfaces**
* [ValueObject](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/ValueObject.java) - Tag interface for value objects.
* [ValueObjectConverter](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/ValueObjectConverter.java) - Converts a value object into it's base type and back.
* [ValueObjectWithBaseType](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/ValueObjectWithBaseType.java) - Value object that may be expressed in a more general type with relaxed restrictions. Often basic Java types like String or numeric values (Long, Integer, ...) are used for this.
* [ValueOfCapable](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/ValueOfCapable.java) - Classes that can convert a string into a type (They have a ```valueOf(String)``` method).
* [AsStringCapable](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/AsStringCapable.java) - Classes that provides a string representation (They have a ```asString()``` method).

**Base classes** that allow an easy implementation of concrete classes. 
* [AbstractIntegerValueObject](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/AbstractIntegerValueObject.java) - A value object that is based on an integer.
* [AbstractLongValueObject](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/AbstractLongValueObject.java) - A value object that is based on a long.
* [AbstractStringValueObject](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/AbstractStringValueObject.java) - A value object that is based on a string.
* [AbstractUuidValueObject](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/AbstractUuidValueObject.java) - A value object that is based on a UUID.
* [AbstractValueObjectConverter](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/AbstractValueObjectConverter.java) - Converts value objects. It combines a JAXB [XmlAdapter](https://docs.oracle.com/javase/8/docs/api/javax/xml/bind/annotation/adapters/XmlAdapter.html), JPA [AttributeConverter](https://docs.oracle.com/javaee/7/api/javax/persistence/AttributeConverter.html) and [ValueObjectConverter](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/ValueObjectConverter.java).

**Predefined Value Objects**
* [CurrencyAmount](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/CurrencyAmount.java) - Combines a currency (like "USD" or "EUR") with a big decimal value. There is also a [CurrencyAmountConverter](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/CurrencyAmountConverter.java) and a [CurrencyAmountValidator](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/CurrencyAmountValidator.java) (```@CurrencyAmountStr```) available.
* [EmailAddress](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/EmailAddress.java) - Email address. There is also a [EmailAddressConverter](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/EmailAddressConverter.java) and a [EmailAddressValidator](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/EmailAddressValidator.java) (```@EmailAddressStr```) available.
* [Password](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/Password.java) - A password with a length between 8 and 20 characters. There is also a [PasswordConverter](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/PasswordConverter.java) and a [PasswordValidator](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/PasswordValidator.java) (```@PasswordStr```) available.
* [PasswordSha512](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/PasswordSha512.java) - A SHA-512 hashed password that is HEX encoded. There is also a [PasswordSha512Converter](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/PasswordSha512Converter.java) and a [PasswordSha512Validator](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/PasswordSha512Validator.java) (```@PasswordSha512Str```) available.
* [UserName](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/UserName.java) - User name with restricted character set and length. There is also a [UserNameConverter](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/UserNameConverter.java) and a [UserNameValidator](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/UserNameValidator.java) (```@UserNameStr```) available.

**Validators**
* [DateValidator](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/DateValidator.java) - Validates that a string is a date using the current locale ([@DateStr](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/DateStr.java)).
* [LocaleValidator](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/LocaleValidator.java) - Validates that a string is a valid locale ([@LocaleStr](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/LocaleStr.java)). There is also a [LocaleConverter](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/LocaleConverter.java) available. 
* [PropertiesContainValidator](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/PropertiesContainValidator.java) - Validates that a string is one out of a list of constants ([@PropertiesContain("one", "two", "three")](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/PropertiesContain.java)).
* [TrimmedNotEmptyValidator](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/TrimmedNotEmptyValidator.java) - Validates that a string is not empty after it was trimmed [@TrimmedNotEmpty](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/TrimmedNotEmpty.java)
* [UUIDValidator](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/UUIDValidator.java) - Validates that a string is a valid UUID ([@UUIDStr](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/UUIDStr.java)). There is also a [UUIDConverter](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/UUIDConverter.java) available. 

**Other**
* [KeyValue](https://github.com/fuinorg/objects4j/blob/master/src/main/java/org/fuin/objects4j/vo/KeyValue.java) - Container for a key (String) and a value (Object).

* * *

### Snapshots

Snapshots can be found on the [OSS Sonatype Snapshots Repository](http://oss.sonatype.org/content/repositories/snapshots/org/fuin "Snapshot Repository"). 

Add the following to your .m2/settings.xml to enable snapshots in your Maven build:

```xml
<repository>
    <id>sonatype.oss.snapshots</id>
    <name>Sonatype OSS Snapshot Repository</name>
    <url>http://oss.sonatype.org/content/repositories/snapshots</url>
    <releases>
        <enabled>false</enabled>
    </releases>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</repository>
```
