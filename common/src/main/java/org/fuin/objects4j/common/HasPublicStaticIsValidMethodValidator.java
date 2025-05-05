package org.fuin.objects4j.common;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.fuin.utils4j.Utils4J;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;

/**
 * Determines if the annotated class has:
 * - a public static method with the given name
 * - a parameter with the given type
 * defined in the annotation and the return type of that method is {@literal boolean}.
 */
public class HasPublicStaticIsValidMethodValidator implements ConstraintValidator<HasPublicStaticIsValidMethod, Object> {

    private static final String PREFIX = HasPublicStaticIsValidMethod.class.getName() + ".";

    private static final String POSTFIX = ".message";

    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("ValidationMessages", Locale.getDefault());

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
                return new Result(msg("1", Map.of("method", methodName)), null);
            }
            if (method.getReturnType() != boolean.class) {
                return new Result(msg("3", Map.of("method", methodName, "expectedType", clasz.getName(), "actualType", method.getReturnType().getName())), null);
            }
            final boolean valid = (boolean) method.invoke(clasz, (Object) null);
            if (!valid) {
                return new Result(msg("4", Map.of("method", methodName)), null);
            }
            return new Result(null, method);
        } catch (final NoSuchMethodException ex) {
            return new Result(msg("2", Map.of("method", methodName)), null);
        } catch (final IllegalAccessException | InvocationTargetException ex) {
            throw new IllegalStateException("Failed to execute method", ex);
        }
    }

    private static String msg(String key, Map<String, String> vars) {
        return Utils4J.replaceVars(MESSAGES.getString(PREFIX + key + POSTFIX), vars);
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
        throw new IllegalArgumentException(result.message() + " (" + clasz.getName() + ")");
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
