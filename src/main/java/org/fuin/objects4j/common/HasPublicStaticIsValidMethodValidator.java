package org.fuin.objects4j.common;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Function;

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
        final Result result = analyze(obj.getClass(), methodName, paramClass);
        if (result.message() == null) {
            return true;
        }
        error(context, result.message());
        return false;
    }

    private void error(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }

    private static Result analyze(final Class<?> clasz, final String methodName, final Class<?> paramClass) {
        try {
            final Method method = clasz.getMethod(methodName, paramClass);
            final int modifiers = method.getModifiers();
            if (!Modifier.isStatic(modifiers)) {
                return new Result("Method '" + methodName + "' is not static (#1)", null);
            }
            if (method.getReturnType() != boolean.class) {
                return new Result("Method '" + methodName + "' does not return '" + clasz.getName() + "', but: " + method.getReturnType().getName() + " (#3)", null);
            }
            final boolean valid = (boolean) method.invoke(clasz, (Object) null);
            if (!valid) {
                return new Result("Method '" + methodName + "' is expected to return 'true' on 'null' argument, but was 'false' (#4)", null);
            }
            return new Result(null, method);
        } catch (final NoSuchMethodException ex) {
            return new Result("The method '" + methodName + "' is undefined or it is not public (#2)", null);
        } catch (final IllegalAccessException | InvocationTargetException ex) {
            throw new IllegalStateException("Failed to execute method", ex);
        }
    }

    /**
     * Tries to locate a method and tests it for compliance. Throws an {@link IllegalArgumentException}
     * in case there is a problem with the method.
     *
     * @param clasz      Class to inspect.
     * @param methodName Method name.
     * @param paramClass Parameter type.
     * @return Method.
     */
    public static Method findMethod(final Class<?> clasz, final String methodName, final Class<?> paramClass) {
        final Result result = analyze(clasz, methodName, paramClass);
        if (result.message() == null) {
            return result.method();
        }
        throw new IllegalArgumentException(result.message());
    }

    /**
     * Tries to locate a method, tests it for compliance and returns it as a function.
     * Throws an {@link IllegalArgumentException} in case there is a problem with the method.
     *
     * @param clasz      Class to inspect.
     * @param methodName Method name.
     * @param paramClass Parameter type.
     * @return Method.
     * @param <P> Type of the parameter.
     * @param <R> Return type.
     */
    public static <P, R> Function<P, R> findFunction(final Class<?> clasz, final String methodName, final Class<?> paramClass) {
        final Method method = findMethod(clasz,methodName, paramClass);
        return param -> {
            try {
                return (R) method.invoke(clasz, param);
            } catch (final IllegalAccessException | InvocationTargetException ex) {
                throw new RuntimeException("Failed to invoke method", ex);
            }
        };
    }

    private record Result(String message, Method method) {
    }

}
