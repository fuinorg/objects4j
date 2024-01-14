package org.fuin.objects4j.common;

import java.lang.annotation.*;

/**
 * Collection of {@link HasPublicStaticIsValidMethod} annotations.
 */
@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface HasPublicStaticIsValidMethods {

    /**
     * Returns a list of {@link HasPublicStaticIsValidMethod} instances.
     *
     * @return Array of annotations.
     */
    HasPublicStaticIsValidMethod[] value();

}
