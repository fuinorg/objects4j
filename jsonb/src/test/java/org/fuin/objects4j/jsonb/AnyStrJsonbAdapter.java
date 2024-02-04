package org.fuin.objects4j.jsonb;

import com.tngtech.archunit.junit.ArchIgnore;

/**
 * Test implementation for a {@link ValueObjectStringJsonbAdapter}.
 */
@ArchIgnore
public class AnyStrJsonbAdapter extends ValueObjectStringJsonbAdapter<AnyStr> {

    /**
     * Default constructor.
     */
    public AnyStrJsonbAdapter() {
        super(AnyStr::valueOf);
    }

}