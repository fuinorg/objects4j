objects4j
=========

A library with common Java types that are mostly immutable value objects.

[![Build Status](https://fuin-org.ci.cloudbees.com/job/objects4j/badge/icon)](https://fuin-org.ci.cloudbees.com/job/objects4j/)
[![Coverage Status](https://coveralls.io/repos/fuinorg/objects4j/badge.svg)](https://coveralls.io/r/fuinorg/objects4j)
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
Provides Value Objects (immutable objects) that represents an object whose equality isn't based on identity. 
That means instances of this type are equal when they have the same value, not necessarily being the same object. 
Additionally some helper classes are placed in this package.



* * *

## Description "common"

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
This allows easy creation of a table model for example for JavaFX.
```Java
AnnotationAnalyzer analyzer = new AnnotationAnalyzer();
List<TableColumnInfo> columns = TableColumnInfo.create(MyClass.class, Locale.US);
for (TableColumnInfo column : columns) {
    System.out.println(column.getText());
}
```



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
