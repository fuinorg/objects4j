package org.fuin.objects4j.jackson;

import org.fuin.utils4j.TestOmitted;

/**
 * Test implementation for a {@link ValueObjectStringJacksonSerializer}.
 */
@TestOmitted("Only a test class")
public class AnyStrJacksonSerializer extends ValueObjectStringJacksonSerializer<AnyStr> {

    /**
     * Default constructor.
     */
    public AnyStrJacksonSerializer() {
        super(AnyStr.class, AnyStr::valueOf);
    }

}