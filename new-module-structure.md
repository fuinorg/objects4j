# New Module Structure
Starting with version `0.10.0` the classes where moved to separate modules.
This allows to avoid unnecessary dependencies on external libraries that are not wanted.
For example the `EmailAddress` class used a `@XmlJavaTypeAdapter` annotation which leads to a dependency
on JAX-B, even if you use another mapping framework like Jackson.

## New Maven Group ID
- OLD: `<groupId>org.fuin</groupId>`
- NEW: `<groupId>org.fuin.objects4j</groupId>`

## New Maven modules
The classes where moved to different modules/packages. 
- [common](common) - Package `org.fuin.objects4j.common`
- [core](core) - Package `org.fuin.objects4j.core`
- [jackson](jackson) - Package `org.fuin.objects4j.jackson` (NEW functionality)
- [jaxb](jaxb) - Package `org.fuin.objects4j.jaxb`
- [jpa](jpa) - Package `org.fuin.objects4j.jpa`
- [jsonb](jsonb) - Package `org.fuin.objects4j.jsonb`
- [junit](junit) - Package `org.fuin.objects4j.junit` (NEW functionality)
- [ui](ui) - Package `org.fuin.objects4j.ui`

## Converter split
Previously the `Converter` classes depended on three external libraries and these packages: 
- JAX-B: `jakarta.xml.bind.annotation.adapters.XmlAdapter`
- JSON-B: `jakarta.json.bind.adapter.JsonbAdapter`
- JPA: `jakarta.persistence.AttributeConverter`

Now there are separate converters for these external libraries:
- [jaxb](jaxb) - `*XmlAdapter` in package `org.fuin.objects4j.jaxb`
- [jpa](jpa) - `*AttributeConverter` in package `org.fuin.objects4j.jpa`
- [jsonb](jsonb) - `*JsonbAdapter` in package `org.fuin.objects4j.jsonb`

## No more JPA 'autoApply'
Previously the converter classes where annotated with `@Converter(autoApply = true)`.
This was removed, and you will need to add the converter either directly to your field
or create a `package-info.java` file and add all the converters at once (only possible with Hibernate).
