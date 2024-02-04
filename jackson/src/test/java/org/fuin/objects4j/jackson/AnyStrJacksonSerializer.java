package org.fuin.objects4j.jackson;

import com.tngtech.archunit.junit.ArchIgnore;

/**
 * Test implementation for a {@link ValueObjectStringJacksonSerializer}.
 */
@ArchIgnore
public class AnyStrJacksonSerializer extends ValueObjectStringJacksonSerializer<AnyStr> {

    /**
     * Default constructor.
     */
    public AnyStrJacksonSerializer() {
        super(AnyStr.class, AnyStr::valueOf);
    }

}