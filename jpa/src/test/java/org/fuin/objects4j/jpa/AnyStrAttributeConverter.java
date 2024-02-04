package org.fuin.objects4j.jpa;

import com.tngtech.archunit.junit.ArchIgnore;

/**
 * Test implementation for a {@link ValueObjectStringAttributeConverter}.
 */
@ArchIgnore
public class AnyStrAttributeConverter extends ValueObjectStringAttributeConverter<AnyStr> {

    /**
     * Default constructor.
     */
    public AnyStrAttributeConverter() {
        super(AnyStr::valueOf);
    }

}