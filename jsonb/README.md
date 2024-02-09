# objects4j-jsonb
Jakarta JSON Binding ([JSON-B](https://jakarta.ee/specifications/jsonb/3.0/jakarta-jsonb-spec-3.0)) adapters for the types defined in [Core](../core).

## Getting started
Simply add an [@JsonbTypeAdapter](https://jakarta.ee/specifications/platform/9/apidocs/jakarta/json/bind/annotation/jsonbtypeadapter) to your field:
```java
public class Data {

    @JsonbProperty
    @JsonbTypeAdapter(EmailAddressJsonbAdapter.class)
    public EmailAddress email;
    
}
```
You can also use the [JsonbUtils.getJsonbAdapters()](src/main/java/org/fuin/objects4j/jsonb/JsonbUtils.java) method to add all adapters to the [JsonbConfig](https://jakarta.ee/specifications/platform/9/apidocs/jakarta/json/bind/jsonbconfig):
```java
public static <T> String toJson(final T obj) {
    final JsonbConfig config = new JsonbConfig().withAdapters(JsonbUtils.getJsonbAdapterArray());
    final Jsonb jsonb = JsonbBuilder.create(config);
    return jsonb.toJson(obj, obj.getClass());
}
```
