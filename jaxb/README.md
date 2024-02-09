# objects4j-jaxb
Jakarta XML Binding ([JAX-B](https://jakarta.ee/specifications/xml-binding/3.0/jakarta-xml-binding-spec-3.0)) XML adapters for the types defined in [Core](../core).

## Getting started
Simply add an [@XmlJavaTypeAdapter](https://jakarta.ee/specifications/platform/9/apidocs/jakarta/xml/bind/annotation/adapters/xmljavatypeadapter) to your field:
```java
@XmlRootElement
public class Data {

    @XmlAttribute
    @XmlJavaTypeAdapter(EmailAddressXmlAdapter.class)
    private EmailAddress email;
    
}
```
You can also create a `package-info.java` file and add all the adapters at once:
```java
@XmlJavaTypeAdapter(EmailAddressXmlAdapter.class)
@XmlJavaTypeAdapter(CurrencyXmlAdapter.class)
package foo.bar;

import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.fuin.objects4j.jaxb.CurrencyXmlAdapter;
import org.fuin.objects4j.jaxb.EmailAddressXmlAdapter;
```
