package org.fuin.objects4j.common;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Determines if the annotated class has a <b>public static</b> constant with a given name and type.
 *
 * @param <A> – The annotation type handled by the validator.
 */
public abstract class TypeConstantValidator<A extends Annotation> implements ConstraintValidator<A, Object> {

    private String fieldName;

    private Class<?> fieldType;

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
                error(context, "Field '" + fieldName + "' is not static (#1)");
                return false;
            }
            if (!fieldType.isAssignableFrom(field.getType())) {
                error(context, "Expected constant '" + fieldName + "' to be of type '" + fieldType.getName() + "', but was: " + field.getType().getName() + " (#3)");
                return false;
            }
            final Object value = field.get(obj);
            if (value == null) {
                error(context, "Constant '" + fieldName + "' is expected to be a non-null value (#4)");
                return false;
            }
            if (!Modifier.isFinal(modifiers)) {
                error(context, "Constant '" + fieldName + "' is not not final (#5)");
                return false;
            }
            return true;
        } catch (final NoSuchFieldException ex) {
            error(context, "The field '" + fieldName + "' is undefined or it is not public (#2)");
            return false;
        } catch (final IllegalAccessException ex) {
            throw new IllegalStateException("Failed to execute method", ex);
        }
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
