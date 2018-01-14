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

* [FileExists Validator](#file-exists-validator)
* [IsDirectory Validator](#is-directory-validator)
* [TraceStringCapable](#trace-string-capable)

* * *

# Description

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
