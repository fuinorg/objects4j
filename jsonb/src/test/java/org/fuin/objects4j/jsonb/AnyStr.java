package org.fuin.objects4j.jsonb;

import com.tngtech.archunit.junit.ArchIgnore;
import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.core.AbstractStringValueObject;

import java.io.Serial;

/**
 * Test implementation for a {@link AbstractStringValueObject}.
 */
@ArchIgnore
public final class AnyStr extends AbstractStringValueObject {

    @Serial
    private static final long serialVersionUID = 1L;

    private String str;

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