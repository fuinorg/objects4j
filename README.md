objects4j
=========

A library with common Java types that are mostly immutable value objects.

[![Build Status](https://fuin-org.ci.cloudbees.com/job/objects4j/badge/icon)](https://fuin-org.ci.cloudbees.com/job/objects4j/)
[![Coverage Status](https://coveralls.io/repos/fuinorg/objects4j/badge.svg)](https://coveralls.io/r/fuinorg/objects4j)

<a href="https://fuin-org.ci.cloudbees.com/job/objects4j"><img src="http://www.fuin.org/images/Button-Built-on-CB-1.png" width="213" height="72" border="0" alt="Built on CloudBees"/></a>

###Snapshots

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
