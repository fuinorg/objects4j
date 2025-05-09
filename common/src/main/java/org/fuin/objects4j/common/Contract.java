/**
 * Copyright (C) 2015 Michael Schnell. All rights reserved.
 * http://www.fuin.org/
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see http://www.gnu.org/licenses/.
 */
package org.fuin.objects4j.common;

import jakarta.annotation.Nullable;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Utility class for assertions on objects.
 */
public final class Contract {

    // According to the specification instances are thread safe
    private static Validator validator;

    /**
     * Sets the validator to use for contract validation. This method is NOT thread safe. It should only be called once per application
     * during the initialization phase.
     *
     * @param newValidator
     *            Set the validator to a new value.
     */
    public static void setValidator(final Validator newValidator) {
        validator = newValidator;
    }

    /**
     * Returns the validator that is used for contract validation. This method is NOT thread safe - This may lead to concurrent
     * initialization of the validator if it's not set yet.
     *
     * @return Current instance - If the validator is not set yet, a new default validator will be created.
     */
    public static Validator getValidator() {
        if (validator == null) {
            validator = Validation.buildDefaultValidatorFactory().getValidator();
        }
        return validator;
    }

    /**
     * Private constructor to avoid instantiation.
     */
    private Contract() {
        throw new UnsupportedOperationException("You cannot create an instance of a utility class!");
    }

    /**
     * Checks if the value is not null.
     *
     * @param name
     *            Name of the value for a possible error message.
     * @param value
     *            Value to check.
     *
     * @throws ConstraintViolationException
     *             The value was null.
     */
    public static void requireArgNotNull(@NotNull final String name, final Object value) throws ConstraintViolationException {
        if (value == null) {
            throw new ConstraintViolationException("The argument '" + name + "' cannot be null");
        }
    }

    /**
     * Checks if the value is not {@literal null} or empty. A single space is considered a valid value.
     *
     * @param name
     *            Name of the value for a possible error message.
     * @param value
     *            Value to check.
     *
     * @throws ConstraintViolationException
     *             The value was null or empty.
     */
    public static void requireArgNotEmpty(@NotNull final String name, final String value) throws ConstraintViolationException {
        requireArgNotNull(name, value);
        if (value.isEmpty()) {
            throw new ConstraintViolationException("The argument '" + name + "' cannot be empty");
        }
    }

    /**
     * Checks if the length of value is not higher than a give maximum.
     *
     * @param name
     *            Name of the value for a possible error message.
     * @param value
     *            Value to check.
     * @param max
     *            Max length (inclusive).
     *
     * @throws ConstraintViolationException
     *             The length was more than <code>max</code>.
     */
    public static void requireArgMaxLength(@NotNull final String name, @NotNull final String value, final int max)
            throws ConstraintViolationException {
        if (value.length() > max) {
            throw new ConstraintViolationException("Max length of argument '" + name + "' is " + max + ", but was: " + value.length());
        }
    }

    /**
     * Checks if the length of value is not less than a give minimum.
     *
     * @param name
     *            Name of the value for a possible error message.
     * @param value
     *            Value to check.
     * @param min
     *            Minimal length.
     *
     * @throws ConstraintViolationException
     *             The length was less than <code>min</code>.
     */
    public static void requireArgMinLength(@NotNull final String name, @NotNull final String value, final int min)
            throws ConstraintViolationException {
        if (value.length() < min) {
            throw new ConstraintViolationException("Min length of argument '" + name + "' is " + min + ", but was: " + value.length());
        }
    }

    /**
     * Checks if the value is not higher than a give maximum.
     *
     * @param name
     *            Name of the value for a possible error message.
     * @param value
     *            Value to check.
     * @param max
     *            Max value (inclusive).
     *
     * @throws ConstraintViolationException
     *             The value was more than <code>max</code>.
     */
    public static void requireArgMax(@NotNull final String name, @NotNull final long value, final long max)
            throws ConstraintViolationException {
        if (value > max) {
            throw new ConstraintViolationException("Max value of argument '" + name + "' is " + max + ", but was: " + value);
        }
    }

    /**
     * Checks if the value is not less than a give minimum.
     *
     * @param name
     *            Name of the value for a possible error message.
     * @param value
     *            Value to check.
     * @param min
     *            Minimal value (inclusive).
     *
     * @throws ConstraintViolationException
     *             The value was less than <code>min</code>.
     */
    public static void requireArgMin(@NotNull final String name, @NotNull final long value, final long min)
            throws ConstraintViolationException {
        if (value < min) {
            throw new ConstraintViolationException("Min value of argument '" + name + "' is " + min + ", but was: " + value);
        }
    }

    /**
     * Checks if the given value is valid.
     *
     * @param validator
     *            Validator to use.
     * @param value
     *            Value to check.
     * @param groups
     *            Group or list of groups targeted for validation (defaults to {@link Default})
     *
     * @throws ConstraintViolationException
     *             The value is invalid.
     */
    public static void requireValid(@NotNull final Validator validator, @NotNull final Object value, @Nullable final Class<?>... groups)
            throws ConstraintViolationException {

        final Set<ConstraintViolation<Object>> constraintViolations = validator.validate(value, groups);
        if (!constraintViolations.isEmpty()) {
            final StringBuilder sb = new StringBuilder();
            for (final ConstraintViolation<Object> constraintViolation : constraintViolations) {
                if (!sb.isEmpty()) {
                    sb.append(", ");
                }
                sb.append("[")
                        .append(constraintViolation.getPropertyPath())
                        .append("] ")
                        .append(constraintViolation.getMessage())
                        .append(" {").append(constraintViolation.getInvalidValue())
                        .append("}");
            }
            throw new ConstraintViolationException(sb.toString(), constraintViolations);
        }

    }

    /**
     * Checks if the given value is valid using a default validator.
     *
     * @param value
     *            Value to check.
     * @param groups
     *            Group or list of groups targeted for validation (defaults to {@link Default})
     *
     * @throws ConstraintViolationException
     *             The value is invalid.
     */
    public static void requireValid(@NotNull final Object value, @Nullable final Class<?>... groups) throws ConstraintViolationException {
        requireValid(getValidator(), value, groups);
    }

    /**
     * Validates the given object.
     *
     * @param validator
     *            Validator to use.
     * @param value
     *            Value to validate.
     * @param groups
     *            Group or list of groups targeted for validation (defaults to {@link Default})
     *
     * @return List of constraint violations.
     *
     * @param <TYPE>
     *            Type of the validated object.
     */
    @NotNull
    public static <TYPE> Set<ConstraintViolation<TYPE>> validate(@NotNull final Validator validator, @Nullable final TYPE value,
                                                                 @Nullable final Class<?>... groups) {
        if (value == null) {
            return new HashSet<>();
        }
        return validator.validate(value, groups);
    }

    /**
     * Validates the given object using a default validator.
     *
     * @param value
     *            Value to validate.
     * @param groups
     *            Group or list of groups targeted for validation (defaults to {@link Default})
     *
     * @return List of constraint violations.
     *
     * @param <TYPE>
     *            Type of the validated object.
     */
    @NotNull
    public static <TYPE> Set<ConstraintViolation<TYPE>> validate(@Nullable final TYPE value, @Nullable final Class<?>... groups) {
        return validate(getValidator(), value, groups);
    }

    /**
     * Converts a set of constraint violation into a string list.
     *
     * @param constraintViolations
     *            Violations to convert to a string.
     *
     * @return List of string representations for all violations.
     *
     * @param <T> Type of list content.
     */
    public static <T> List<String> asString(@Nullable final Set<ConstraintViolation<T>> constraintViolations) {
        if (constraintViolations == null || constraintViolations.isEmpty()) {
            return Collections.emptyList();
        }
        final List<String> list = new ArrayList<>();
        for (final ConstraintViolation<T> constraintViolation : constraintViolations) {
            list.add(asString(constraintViolation));
        }
        return list;
    }

    /**
     * Converts a set of constraint violation into a string.
     *
     * @param constraintViolations
     *            Violations to convert to a string.
     * @param separator
     *            Separator to use between violations. Defaults to ', ' in case of {@literal null} argument.
     *
     * @return String representation of all violations.
     *
     * @param <T> Type of the root bean.
     */
    public static <T> String asString(@Nullable final Set<ConstraintViolation<T>> constraintViolations, @Nullable final String separator) {
        if (constraintViolations == null || constraintViolations.isEmpty()) {
            return "";
        }
        final String sepStr;
        if (separator == null) {
            sepStr = ", ";
        } else {
            sepStr = separator;
        }
        final StringBuilder sb = new StringBuilder();
        for (final ConstraintViolation<T> constraintViolation : constraintViolations) {
            if (!sb.isEmpty()) {
                sb.append(sepStr);
            }
            sb.append(asString(constraintViolation));
        }
        return sb.toString();
    }

    /**
     * Returns a constraint violation as string.
     *
     * @param violation
     *            Violation to convert to a string.
     *
     * @return Text like "SIMPLE_CLASS_NAME.PROPERTY_PATH MESSAGE" or "SIMPLE_CLASS_NAME.PROPERTY_PATH MESSAGE (INVALID_VALUE)".
     *
     * @param <T> Type of the validated root object.
     */
    public static <T> String asString(@Nullable final ConstraintViolation<T> violation) {
        if (violation == null) {
            return "";
        }
        final String className = violation.getRootBeanClass().getSimpleName();
        final String propertyPath = violation.getPropertyPath().toString();
        final String message = violation.getMessage();
        final Object invalidValue = violation.getInvalidValue();
        if (invalidValue == null) {
            return className + "." + propertyPath + " " + message;
        }
        return className + "." + propertyPath + " " + message + " (" + invalidValue + ")";
    }

}
