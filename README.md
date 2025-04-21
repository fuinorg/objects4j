# objects4j
A library with common Java types that are mostly immutable value objects.

[![Java Maven Build](https://github.com/fuinorg/objects4j/actions/workflows/maven.yml/badge.svg)](https://github.com/fuinorg/objects4j/actions/workflows/maven.yml)
[![Coverage Status](https://sonarcloud.io/api/project_badges/measure?project=org.fuin%3Aobjects4j&metric=coverage)](https://sonarcloud.io/dashboard?id=org.fuin%3Aobjects4j)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.fuin/objects4j/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.fuin/objects4j/)
[![LGPLv3 License](http://img.shields.io/badge/license-LGPLv3-blue.svg)](https://www.gnu.org/licenses/lgpl.html)
[![Java Development Kit 17](https://img.shields.io/badge/JDK-17-green.svg)](https://openjdk.java.net/projects/jdk/17/)

## Versions
- [0.11.0](release-notes.md#0110)
- [0.10.0](release-notes.md#0100)
- 0.9.0 = **Java 17**
- 0.8.0 = **Java 11** with new **jakarta** namespace
- 0.7.x = **Java 11** before namespace change from 'javax' to 'jakarta'
- 0.6.9 (or previous) = **Java 8**

# Features
- [Common](common) - Interfaces, annotations and validators.
- [Core](core) - Value objects and utility classes for these value objects.
- [Jackson](jackson) - FasterXML Jackson serializer/deserializer for the types defined in [Core](core).
- [JAX-B](jaxb) - Jakarta XML Binding (JAX-B) XML adapters for the types defined in [Core](core).
- [JPA](jpa) - Jakarta Persistence API (JPA) attribute converters for the types defined in [Core](core).
- [JSON-B](jsonb) - Jakarta JSON Binding (JSON-B) adapters for the types defined in [Core](../core).
- [JUnit](junit) - Defines ArchUnit conditions for classes using this library.
- [UI](ui) - Annotations that can be placed on plain objects but may be used by a user interface to render that object in some way.

* * *

### Snapshots

Snapshots can be found on the [OSS Sonatype Snapshots Repository](https://oss.sonatype.org/index.html#view-repositories;snapshots~browsestorage~/org/fuin "Snapshot Repository"). 

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
