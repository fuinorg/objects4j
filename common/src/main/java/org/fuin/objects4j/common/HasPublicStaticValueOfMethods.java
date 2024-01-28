package org.fuin.objects4j.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
