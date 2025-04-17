package org.fuin.objects4j.jpa;

import org.fuin.utils4j.TestOmitted;

/**
 * Test implementation for a {@link ValueObjectStringAttributeConverter}.
 */
@TestOmitted("Only a test class")
public class AnyStrAttributeConverter extends ValueObjectStringAttributeConverter<AnyStr> {

    /**
     * Default constructor.
     */
    public AnyStrAttributeConverter() {
        super(AnyStr::valueOf);
    }

}