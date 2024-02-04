package org.fuin.objects4j.jackson;

import com.tngtech.archunit.junit.ArchIgnore;

/**
 * Test implementation for a {@link ValueObjectStringJacksonDeserializer}.
 */
@ArchIgnore
public class AnyStrJacksonDeserializer extends ValueObjectStringJacksonDeserializer<AnyStr> {

    /**
     * Default constructor.
     */
    public AnyStrJacksonDeserializer() {
        super(AnyStr.class, AnyStr::valueOf);
    }

}