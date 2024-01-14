package org.fuin.objects4j.common;

import java.lang.annotation.*;

/**
 * A class that provides a public static method that converts a given value into an instance of the class annotated.
 * The default is a method that takes a string as parameter and converts it into an object of the class.
 */
@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ValueOfCapables.class)
public @interface ValueOfCapable {

    /**
     * Returns the name of a public static method in the annotated class.<br>
     * <br>
     * The required method signature is:
     * <ul>
     *   <li>Exactly one argument of type {@link #param()}</li>
     *   <li>Returns an instance of the annotated class</li>
     *   <li>Must return {@literal null} when a {@literal null} instance is passed as argument</li>
     * </ul>
     *
     * @return Name of the public static method.
     */
    String method() default "valueOf";

    /**
     * Returns the type of the parameter of the method named {@link #method()}.
     *
     * @return Parameter type of the public static method.
     */
    Class<?> param() default String.class;

}
