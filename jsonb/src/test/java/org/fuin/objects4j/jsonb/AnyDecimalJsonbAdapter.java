package org.fuin.objects4j.jsonb;

import org.fuin.utils4j.TestOmitted;

import java.math.BigDecimal;
/** Adapter for AnyDecimal. */
@TestOmitted("Only a test class")
public class AnyDecimalJsonbAdapter extends ValueObjectJsonbAdapter<BigDecimal, AnyDecimal> {

    /** Default constructor. */
    public AnyDecimalJsonbAdapter() {
        super(AnyDecimal::new);
    }

}
