package org.fuin.objects4j.jaxb;

import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.ValueObjectWithBaseType;
import org.fuin.utils4j.TestOmitted;


/**
 * Test value object with a Long base type - the case a string based adapter cannot handle.
 */
@TestOmitted("Only a test class")
public final class AnyLong implements ValueObjectWithBaseType<Long> {

    private Long value;

    /**
     * Constructor with mandatory data.
     *
     * @param value Value.
     */
    public AnyLong(final Long value) {
        super();
        Contract.requireArgNotNull("value", value);
        this.value = value;
    }

    @Override
    public Class<Long> getBaseType() {
        return Long.class;
    }

    @Override
    public Long asBaseType() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * Converts the base value into an instance of this class.
     *
     * @param value Value to convert.
     *
     * @return New instance.
     */
    public static AnyLong valueOf(final Long value) {
        return new AnyLong(value);
    }

}
