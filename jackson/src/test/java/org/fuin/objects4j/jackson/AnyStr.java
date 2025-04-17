package org.fuin.objects4j.jackson;

import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.core.AbstractStringValueObject;
import org.fuin.utils4j.TestOmitted;

import java.io.Serial;

/**
 * Test implementation for a {@link AbstractStringValueObject}.
 */
@TestOmitted("Only a test class")
public final class AnyStr extends AbstractStringValueObject {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String str;

    /**
     * Constructor with manadatory data.
     *
     * @param str
     *            String.
     */
    public AnyStr(final String str) {
        super();
        Contract.requireArgNotNull("str", str);
        this.str = str;
    }

    @Override
    public String asBaseType() {
        return str;
    }

    /**
     * Converts a given string into an instance of this class.
     *
     * @param str
     *            String to convert.
     *
     * @return New instance.
     */
    public static AnyStr valueOf(String str) {
        return new AnyStr(str);
    }

}