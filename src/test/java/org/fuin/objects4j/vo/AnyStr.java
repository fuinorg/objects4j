package org.fuin.objects4j.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.fuin.objects4j.common.Contract;

/**
 * Test implementation for a {@link AbstractStringValueObject}.
 */
@XmlJavaTypeAdapter(AnyStrConverter.class)
public final class AnyStr extends AbstractStringValueObject {

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
