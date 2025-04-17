package org.fuin.objects4j.jaxb;

import org.fuin.utils4j.TestOmitted;

/**
 * Test implementation for a {@link ValueObjectStringXmlAdapter}.
 */
@TestOmitted("Only a test class")
public class AnyStrXmlAdapter extends ValueObjectStringXmlAdapter<AnyStr> {

    /**
     * Default constructor.
     */
    public AnyStrXmlAdapter() {
        super(AnyStr::valueOf);
    }

}