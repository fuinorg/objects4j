# objects4j-junit
Defines [ArchUnit](https://www.archunit.org/) conditions for classes depending on this library.

See [ArchitectureBaseTest.java](src/test/java/org/fuin/objects4j/junit/ArchitectureBaseTest.java) for a full usage example.

## Verify that classes using [@HasPublicStaticIsValidMethod](../common/src/main/java/org/fuin/objects4j/common/HasPublicStaticIsValidMethod.java) really have such a method.
```java
@ArchTest
static final ArchRule verify_public_static_is_valid_annotations =
    classes()
        .that()
            .doNotHaveModifier(JavaModifier.ABSTRACT)
            .and()
            .areAnnotatedWith(HasPublicStaticIsValidMethod.class)
        .should(haveACorrespondingPublicStaticIsValidMethod());
```

## Verify that classes using [@HasPublicStaticValueOfMethod](../common/src/main/java/org/fuin/objects4j/common/HasPublicStaticValueOfMethod.java) really have such a method.
```java
@ArchTest
static final ArchRule verify_public_static_value_of_annotations =
    classes()
        .that()
            .doNotHaveModifier(JavaModifier.ABSTRACT)
            .and()
            .areAnnotatedWith(HasPublicStaticValueOfMethod.class)
        .should(haveACorrespondingPublicStaticValueOfMethod());
```