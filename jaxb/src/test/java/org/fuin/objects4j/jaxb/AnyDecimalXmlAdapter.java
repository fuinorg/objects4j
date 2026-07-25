package org.fuin.objects4j.jaxb;

import org.fuin.utils4j.TestOmitted;

import java.math.BigDecimal;
/** Adapter for AnyDecimal. */
@TestOmitted("Only a test class")
public class AnyDecimalXmlAdapter extends ValueObjectXmlAdapter<BigDecimal, AnyDecimal> {

    /** Default constructor. */
    public AnyDecimalXmlAdapter() {
        super(BigDecimal::new, AnyDecimal::new);
    }

}
