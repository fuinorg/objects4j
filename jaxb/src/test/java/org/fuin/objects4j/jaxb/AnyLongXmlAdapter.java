package org.fuin.objects4j.jaxb;

import org.fuin.utils4j.TestOmitted;


/** Adapter for AnyLong. */
@TestOmitted("Only a test class")
public class AnyLongXmlAdapter extends ValueObjectXmlAdapter<Long, AnyLong> {

    /** Default constructor. */
    public AnyLongXmlAdapter() {
        super(Long::valueOf, AnyLong::new);
    }

}
