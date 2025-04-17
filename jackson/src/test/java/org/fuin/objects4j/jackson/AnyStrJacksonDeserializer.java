package org.fuin.objects4j.jackson;

import org.fuin.utils4j.TestOmitted;

/**
 * Test implementation for a {@link ValueObjectStringJacksonDeserializer}.
 */
@TestOmitted("Only a test class")
public class AnyStrJacksonDeserializer extends ValueObjectStringJacksonDeserializer<AnyStr> {

    /**
     * Default constructor.
     */
    public AnyStrJacksonDeserializer() {
        super(AnyStr.class, AnyStr::valueOf);
    }

}