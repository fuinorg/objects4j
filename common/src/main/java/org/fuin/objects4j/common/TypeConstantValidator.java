package org.fuin.objects4j.common;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.fuin.utils4j.Utils4J;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Determines if the annotated class has a <b>public static</b> constant with a given name and type.
 *
 * @param <A> – The annotation type handled by the validator.
 */
public abstract class TypeConstantValidator<A extends Annotation> implements ConstraintValidator<A, Object> {

    private static final String POSTFIX = ".message";

    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("ValidationMessages", Locale.getDefault());

    private final String prefix;

    private String fieldName;

    private Class<?> fieldType;

    public TypeConstantValidator(final Class<A> annotationType) {
        prefix = annotationType.getName() + ".";
    }

    @Override
    public void initialize(A annotation) {
        this.fieldName = value(annotation, "name", String.class);
        this.fieldType = value(annotation, "value", Class.class);
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        try {
            final Field field = obj.getClass().getField(fieldName);
            final int modifiers = field.getModifiers();
            if (!Modifier.isStatic(modifiers)) {
                error(context, msg("1", Map.of("field", fieldName)));
                return false;
            }
            if (!fieldType.isAssignableFrom(field.getType())) {
                error(context, msg("3", Map.of("field", fieldName, "expectedType", fieldType.getName(), "actualType", field.getType().getName())));
                return false;
            }
            final Object value = field.get(obj);
            if (value == null) {
                error(context, msg("4", Map.of("field", fieldName)));
                return false;
            }
            if (!Modifier.isFinal(modifiers)) {
                error(context, msg("5", Map.of("field", fieldName)));
                return false;
            }
            return true;
        } catch (final NoSuchFieldException ex) {
            error(context, msg("2", Map.of("field", fieldName)));
            return false;
        } catch (final IllegalAccessException ex) {
            throw new IllegalStateException("Failed to execute method", ex);
        }
    }

    private String msg(String key, Map<String, String> vars) {
        final String k;
        if (MESSAGES.containsKey(prefix + key + POSTFIX)) {
            k = MESSAGES.getString(prefix + key + POSTFIX);
        } else {
            k = MESSAGES.getString(TypeConstantValidator.class.getName() + "." + key + POSTFIX);
        }
        return Utils4J.replaceVars(k, vars);
    }

    private void error(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }

    private static <V> Result<V> analyze(final Class<?> claszWithStaticField,
                                         final Class<V> expectedTypeOfField,
                                         final String name) {
        try {
            final Field field = claszWithStaticField.getField(name);
            final int modifiers = field.getModifiers();
            if (!Modifier.isStatic(modifiers)) {
                return new Result<>("Field '" + name + "' is not static (#1)", null);
            }
            if (!expectedTypeOfField.isAssignableFrom(field.getType())) {
                return new Result<>("Expected constant '" + name + "' to be of type '" + expectedTypeOfField.getName() + "', but was: " + field.getType().getName() + " (#3)", null);
            }
            final Object value = field.get(claszWithStaticField);
            if (value == null) {
                return new Result<>("Constant '" + name + "' is expected to be a non-null value (#4)", null);
            }
            if (!Modifier.isFinal(modifiers)) {
                return new Result<>("Constant '" + name + "' is not not final (#5)", null);
            }
            return new Result<>(null, (V) value);
        } catch (final NoSuchFieldException ex) {
            return new Result<>("The field '" + name + "' is undefined or it is not public (#2)", null);
        } catch (final IllegalAccessException ex) {
            throw new IllegalStateException("Failed to execute method", ex);
        }
    }

    /**
     * Returns a constant of a given type in a class. Throws an {@link IllegalArgumentException}
     * in case there is a problem with the field.
     *
     * @param claszWithStaticField Class to inspect.
     * @param expectedTypeOfField  Expected field type.
     * @param fieldName            Name of the public static field.
     * @return Value of the constant.
     */
    public static <V> V extractValue(final Class<?> claszWithStaticField,
                                     final Class<V> expectedTypeOfField,
                                     final String fieldName) {
        final Result<V> result = analyze(claszWithStaticField, expectedTypeOfField, fieldName);
        if (result.message() == null) {
            return result.value();
        }
        throw new IllegalArgumentException(result.message());
    }

    static <T> T value(Annotation annotation, String name, Class<T> type) {
        try {
            final Method method = annotation.getClass().getMethod(name);
            final Object value = method.invoke(annotation);
            if (type.isAssignableFrom(value.getClass())) {
                return (T) value;
            }
            throw new IllegalStateException("Expected field of type '" + type.getSimpleName() + "', but was: " + method.getReturnType().getSimpleName());
        } catch (final NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            throw new IllegalStateException("Failed to extract value of type '" + type.getSimpleName() + "' for field '" + name + "' in annotation '" + annotation.getClass().getSimpleName() + "'", ex);
        }
    }

    private record Result<V>(String message, V value) {
    }


}
