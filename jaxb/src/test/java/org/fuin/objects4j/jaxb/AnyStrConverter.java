package org.fuin.objects4j.jaxb;

import com.tngtech.archunit.junit.ArchIgnore;

/**
 * Test implementation for a {@link ValueObjectStringConverter}.
 */
@ArchIgnore
public class AnyStrConverter extends ValueObjectStringConverter<AnyStr> {

    /**
     * Default constructor.
     */
    public AnyStrConverter() {
        super(AnyStr::valueOf);
    }

}