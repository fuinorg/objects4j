package org.fuin.objects4j.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
