package org.fuin.objects4j.jsonb;

import org.fuin.utils4j.TestOmitted;

/**
 * Test implementation for a {@link ValueObjectStringJsonbAdapter}.
 */
@TestOmitted("Only a test class")
public class AnyStrJsonbAdapter extends ValueObjectStringJsonbAdapter<AnyStr> {

    /**
     * Default constructor.
     */
    public AnyStrJsonbAdapter() {
        super(AnyStr::valueOf);
    }

}