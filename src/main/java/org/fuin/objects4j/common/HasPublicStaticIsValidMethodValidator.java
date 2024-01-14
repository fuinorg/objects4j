package org.fuin.objects4j.common;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Determines if the annotated class has:
 * - a public static method with the given name
 * - a parameter with the given type
 * defined in the annotation and the return type of that method is {@literal boolean}.
 */
public class HasPublicStaticIsValidMethodValidator implements ConstraintValidator<HasPublicStaticIsValidMethod, Object> {

    private String methodName;

    private Class<?> paramClass;

    @Override
    public void initialize(HasPublicStaticIsValidMethod annotation) {
        this.methodName = annotation.method();
        this.paramClass = annotation.param();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        try {
            final Method method = obj.getClass().getMethod(methodName, paramClass);
            final int modifiers = method.getModifiers();
            if (!Modifier.isStatic(modifiers)) {
                error(context, "Method '" + methodName + "' is not static (#1)");
                return false;
            }
            if (method.getReturnType() != boolean.class) {
                error(context, "Method '" + methodName + "' does not return 'boolean', but: " + method.getReturnType().getName() + " (#3)");
                return false;
            }
            final boolean valid = (boolean) method.invoke(obj, (Object) null);
            if (!valid) {
                error(context, "Method '" + methodName + "' is expected to return 'true' on 'null' argument, but was 'false' (#4)");
                return false;
            }
            return true;
        } catch (final NoSuchMethodException ex) {
            error(context, "The method '" + methodName + "' is undefined or it is not public (#2)");
            return false;
        } catch (final IllegalAccessException | InvocationTargetException ex) {
            throw new IllegalStateException("Failed to execute method", ex);
        }

    }

    private void error(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }

}
