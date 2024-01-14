package org.fuin.objects4j.common;

import java.lang.annotation.*;

/**
 * Collection of {@link HasPublicStaticValueOfMethod} annotations.
 */
@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface HasPublicStaticValueOfMethods {

    /**
     * Returns a list of annotations.
     *
     * @return Array of annotations.
     */
    HasPublicStaticValueOfMethod[] value();

}
