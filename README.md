# objects4j
A library with common Java types that are mostly immutable value objects.

[![Java Maven Build](https://github.com/fuinorg/objects4j/actions/workflows/maven.yml/badge.svg)](https://github.com/fuinorg/objects4j/actions/workflows/maven.yml)
[![Coverage Status](https://sonarcloud.io/api/project_badges/measure?project=org.fuin%3Aobjects4j&metric=coverage)](https://sonarcloud.io/dashboard?id=org.fuin%3Aobjects4j)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.fuin/objects4j/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.fuin/objects4j/)
[![LGPLv3 License](http://img.shields.io/badge/license-LGPLv3-blue.svg)](https://www.gnu.org/licenses/lgpl.html)
[![Java Development Kit 17](https://img.shields.io/badge/JDK-17-green.svg)](https://openjdk.java.net/projects/jdk/17/)

## Versions
- [0.11.1](CHANGELOG.md#0111)
- [0.11.0](CHANGELOG.md#0110)
- [0.10.0](CHANGELOG.md#0100)
- 0.9.0 = **Java 17**
- 0.8.0 = **Java 11** with new **jakarta** namespace
- 0.7.x = **Java 11** before namespace change from 'javax' to 'jakarta'
- 0.6.9 (or previous) = **Java 8**

# Features
- [BOM](bom) - "Bill of Materials" that manages the versions of all other modules.
- [Common](common) - Interfaces, annotations and validators.
- [Core](core) - Value objects and utility classes for these value objects.
- [Jackson](jackson) - FasterXML Jackson serializer/deserializer for the types defined in [Core](core).
- [JAX-B](jaxb) - Jakarta XML Binding (JAX-B) XML adapters for the types defined in [Core](core).
- [JPA](jpa) - Jakarta Persistence API (JPA) attribute converters for the types defined in [Core](core).
- [JSON-B](jsonb) - Jakarta JSON Binding (JSON-B) adapters for the types defined in [Core](../core).
- [JUnit](junit) - Defines ArchUnit conditions for classes using this library.
- [UI](ui) - Annotations that can be placed on plain objects but may be used by a user interface to render that object in some way.

* * *

### Usage

The [objects4j-bom](bom) ("Bill of Materials") lets you manage the versions of all objects4j modules in
a single place. Import it into the `dependencyManagement` section of your project and then reference the
modules you need without specifying a version:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.fuin.objects4j</groupId>
            <artifactId>objects4j-bom</artifactId>
            <version>0.11.1</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependencies>
    <dependency>
        <groupId>org.fuin.objects4j</groupId>
        <artifactId>objects4j-core</artifactId>
    </dependency>
    <dependency>
        <groupId>org.fuin.objects4j</groupId>
        <artifactId>objects4j-jackson</artifactId>
    </dependency>
</dependencies>
```

* * *

## Snapshots

Snapshots can be found on the [Central Portal Snapshots Repository](https://central.sonatype.com/repository/maven-snapshots/org/fuin "Snapshot Repository").

Add the following to your .m2/settings.xml to enable snapshots in your Maven build:

```xml
<repository>
    <id>central-portal-snapshots</id>
    <name>Central Portal Snapshots</name>
    <url>https://central.sonatype.com/repository/maven-snapshots/</url>
    <releases>
        <enabled>false</enabled>
    </releases>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</repository>
```
