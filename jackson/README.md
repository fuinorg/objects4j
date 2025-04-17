# objects4j-jackson
FasterXML [Jackson](https://github.com/FasterXML/jackson) JSON serializer/deserializer for the types defined in [Core](../core).

## Getting started
Simply add the [Objects4JJacksonAdapterModule](src/main/java/org/fuin/objects4j/jackson/Objects4JJacksonAdapterModule.java) 
to your object mapper to activate the serializers and deserializer.

```java
@Configuration
public class SpringBootConfig {

    @Bean
    @Primary
    public ObjectMapper jsonMapper() {
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .registerModule(new Objects4JJacksonAdapterModule());
    }

}
```
