package org.fuin.objects4j.jaxb;

import com.tngtech.archunit.junit.ArchIgnore;

/**
 * Test implementation for a {@link ValueObjectStringXmlAdapter}.
 */
@ArchIgnore
public class AnyStrXmlAdapter extends ValueObjectStringXmlAdapter<AnyStr> {

    /**
     * Default constructor.
     */
    public AnyStrXmlAdapter() {
        super(AnyStr::valueOf);
    }

}