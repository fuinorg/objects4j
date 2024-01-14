package org.fuin.objects4j.common;

import java.lang.annotation.*;

/**
 * Collection of {@link ValueOfCapable} annotations.
 */
@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueOfCapables {

    /**
     * Returns a list of annotations.
     *
     * @return Array of annotations.
     */
    ValueOfCapable[] value();

}
