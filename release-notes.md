# Release Notes

## 0.11.0
- Removed unnecessary Jackson serializer/deserializer classes

## 0.10.0

### General
- **New (incompatible) module structure** (See [New Module Structure](new-module-structure.md))

### Jackson
The new [jackson](jackson) package adds serializers and deserializers for the [core types](core/src/main/java/org/fuin/objects4j/core).

### JUnit
The new [junit](junit) package adds some ArchUnit conditions that verifies if annotations from common are used correctly.
- [HasPublicStaticIsValidMethodCondition](junit/src/main/java/org/fuin/objects4j/junit/HasPublicStaticIsValidMethodCondition.java) for the [@HasPublicStaticIsValidMethods](common/src/main/java/org/fuin/objects4j/common/HasPublicStaticIsValidMethods.java) annotation
- [HasPublicStaticValueOfMethodCondition](junit/src/main/java/org/fuin/objects4j/junit/HasPublicStaticValueOfMethodCondition.java) for the [@HasPublicStaticValueOfMethods](common/src/main/java/org/fuin/objects4j/common/HasPublicStaticValueOfMethods.java) annotation
- They can be used in your code by using the [Objects4Conditions](junit/src/main/java/org/fuin/objects4j/junit/Objects4Conditions.java) methods.

