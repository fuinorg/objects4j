package org.fuin.objects4j.common;

import java.lang.annotation.*;

/**
 * Collection of {@link IsValidCapable} annotations.
 */
@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface IsValidCapables {

    /**
     * Returns a list of {@link IsValidCapable} instances.
     *
     * @return Array of annotations.
     */
    IsValidCapable[] value();

}
