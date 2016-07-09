package org.fuin.objects4j.vo;

/**
 * Test implementation for a {@link ValueObjectStringConverter}.
 */
public class AnyStrConverter extends ValueObjectStringConverter<AnyStr> {

    /**
     * Default constructor.
     */
    public AnyStrConverter() {
        super(AnyStr::valueOf);
    }

}
