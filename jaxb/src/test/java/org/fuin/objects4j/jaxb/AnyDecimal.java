package org.fuin.objects4j.jaxb;

import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.ValueObjectWithBaseType;
import org.fuin.utils4j.TestOmitted;

import java.math.BigDecimal;
/**
 * Test value object with a BigDecimal base type - the case a string based adapter cannot handle.
 */
@TestOmitted("Only a test class")
public final class AnyDecimal implements ValueObjectWithBaseType<BigDecimal> {

    private BigDecimal value;

    /**
     * Constructor with mandatory data.
     *
     * @param value Value.
     */
    public AnyDecimal(final BigDecimal value) {
        super();
        Contract.requireArgNotNull("value", value);
        this.value = value;
    }

    @Override
    public Class<BigDecimal> getBaseType() {
        return BigDecimal.class;
    }

    @Override
    public BigDecimal asBaseType() {
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
    public static AnyDecimal valueOf(final BigDecimal value) {
        return new AnyDecimal(value);
    }

}
