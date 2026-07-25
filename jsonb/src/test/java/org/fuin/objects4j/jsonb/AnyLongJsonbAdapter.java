package org.fuin.objects4j.jsonb;

import org.fuin.utils4j.TestOmitted;


/** Adapter for AnyLong. */
@TestOmitted("Only a test class")
public class AnyLongJsonbAdapter extends ValueObjectJsonbAdapter<Long, AnyLong> {

    /** Default constructor. */
    public AnyLongJsonbAdapter() {
        super(AnyLong::new);
    }

}
