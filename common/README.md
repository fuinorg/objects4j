# objects4j-common
Some interfaces, annotations and validators.

## Bean validation (JSR 303)
Constraint annotations and the corresponding validators.

### [@IsFile](src/main/java/org/fuin/objects4j/common/IsFile.java)
Verifies that the annotated java File is a file and not a directory
```Java
@IsFile
private File myDir;
```

### [@FileExists](src/main/java/org/fuin/objects4j/common/FileExists.java)
Verifies that the file exists
```Java
@FileExists
private File myFile;
```

### [@IsDirectory](src/main/java/org/fuin/objects4j/common/IsDirectory.java)
Verifies that it's a directory and not a file
```Java
@IsDirectory
private File myDir;
```

### [@HasPublicStaticIsValidMethod](src/main/java/org/fuin/objects4j/common/HasPublicStaticIsValidMethod.java) / [@HasPublicStaticIsValidMethods](src/main/java/org/fuin/objects4j/common/HasPublicStaticIsValidMethods.java)
Tags a class that has a public static method that verifies if a given value can be converted into an instance of the class.
The default is a method named "isValid" with a String parameter. You can change this using the annotation arguments.
```Java
@HasPublicStaticIsValidMethod
public class IBAN {
    public static boolean isValid(String ibanStr) {
        ...
    }
}
```

### [@HasPublicStaticValueOfMethod](src/main/java/org/fuin/objects4j/common/HasPublicStaticValueOfMethod.java) / [@HasPublicStaticValueOfMethods](src/main/java/org/fuin/objects4j/common/HasPublicStaticValueOfMethods.java)
Tags a class that has a public static method that converts if a given value into an instance of the class.
The default is a method named "valueOf" with a String parameter. You can change this using the annotation arguments.
```Java
@HasPublicStaticValueOfMethod
public class IBAN {
    public static IBAN valueOf(String ibanStr) {
        return new IBAN(ibanStr);
    }
}
```

## Interfaces
Annotated classes provide some special functionality.

### String methods

#### [TraceStringCapable](src/main/java/org/fuin/objects4j/common/TraceStringCapable.java)
Marker interface for classes that provide a special representation for trace output.

#### [AsStringCapable](src/main/java/org/fuin/objects4j/common/AsStringCapable.java)
Objects that provides a string representation of itself that is used for displaying business values.
In contrast to that is the standard "toString()" method often used for technical information.

```Java
public class CompanyName implements TraceStringCapable, AsStringCapable {

    private String str;
    
    @Override
    public String asString() {
        return str; 
    }

    @Override
    public String toTraceString() {
        return str + "[" + this.hashCode() + "]";
    }

    @Override
    public String toString() {
        return "CompanyName{str=" + str + '}';
    }
   
}
```

### [MarshalInformation](src/main/java/org/fuin/objects4j/common/MarshalInformation.java)
Tags any instance that is capable of delivering additional information for serialization/deserialization. 
In contrast to XML where the element name can be used to determine the type of the object, as with JSON 
there is often just an unknown type of object. This interface helps to determine the full qualified name 
of the class and the tag name to use.

### [ExceptionShortIdentifable](src/main/java/org/fuin/objects4j/common/ExceptionShortIdentifable.java)
Marks an exception that provides a unique short identifier. This identifier should be human-readable and 
is meant to be displayed to an end user. A service desk can use it to identify the type of error quickly 
and for example attach a guideline to it that explains how to solve the problem.

### [ToExceptionCapable](src/main/java/org/fuin/objects4j/common/ToExceptionCapable.java)
Tags objects that can create an exception based on their data.

### [UniquelyNumbered](src/main/java/org/fuin/objects4j/common/UniquelyNumbered.java)
Tags objects that provide a unique (long) number in their context.
This is used along with [UniquelyNumberedException](src/main/java/org/fuin/objects4j/common/UniquelyNumberedException.java) 
which is a base class for exceptions that have such a unique ID.

### [ValueObject](src/main/java/org/fuin/objects4j/common/ValueObject.java)
Immutable object that represents an object whose equality isn't based on identity.
That means instances of this type are equal when they have the same value, not necessarily being the same object.
A Java record is a perfect match for this kind of object. 

### [ValueObjectWithBaseType](src/main/java/org/fuin/objects4j/common/ValueObjectWithBaseType.java)
Value object that may be expressed in a more general type with relaxed restrictions.
Often basic Java types like String or numeric values (Long, Integer, ...) are used for this.
