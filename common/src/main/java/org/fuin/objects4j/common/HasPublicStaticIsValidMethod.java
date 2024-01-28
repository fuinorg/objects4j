package org.fuin.objects4j.common;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A class that provides a public static method to determine if a given value can be converted into an instance of the class annotated.
 * The default is a method that takes a string as parameter and verifies if it is valid.
 */
@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(HasPublicStaticIsValidMethods.class)
@Constraint(validatedBy = { HasPublicStaticIsValidMethodValidator.class })
public @interface HasPublicStaticIsValidMethod {

    /**
     * Returns the name of a public static method in the annotated class.<br>
     * <br>
     * The required method signature is:
     * <ul>
     *   <li>Exactly one argument of type {@link #param()}</li>
     *   <li>Returns an instance of the annotated class</li>
     *   <li>Must return {@literal true} when a {@literal null} instance is passed as argument</li>
     * </ul>
     *
     * @return Name of the public static method.
     */
    String method() default "isValid";

    /**
     * Returns the type of the parameter of the method named {@link #method()}.
     *
     * @return Parameter type of the public static method.
     */
    Class<?> param() default String.class;

    String message() default "Does not define a public static method with the given argument type and returns boolean";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
