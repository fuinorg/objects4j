/**
 * Copyright (C) 2013 Future Invent Informationsmanagement GmbH. All rights
 * reserved. <http://www.fuin.org/>
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fuin.objects4j.common;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

/**
 * Utility class for assertions on objects.
 */
public final class Contract {

    // According to the specification instances are thread safe
    private static Validator validator;

    /**
     * Sets the validator to use for contract validation. This method is NOT
     * thread safe. It should only be called once per application during the
     * initialization phase.
     * 
     * @param newValidator
     *            Set the validator to a new value.
     */
    public static void setValidator(final Validator newValidator) {
        validator = newValidator;
    }

    /**
     * Returns the validator that is used for contract validation. This method
     * is NOT thread safe - This may lead to concurrent initialization of the
     * validator if it's not set yet.
     * 
     * @return Current instance - If the validator is not set yet, a new default
     *         validator will be created.
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
     * @throws ContractViolationException
     *             The value was null.
     */
    // CHECKSTYLE:OFF:RedundantThrows
    public static void requireArgNotNull(@NotNull final String name, final Object value)
            throws ContractViolationException {
        // CHECKSTYLE:ON
        if (value == null) {
            throw new ContractViolationException("The argument '" + name + "' cannot be null");
        }
    }

    /**
     * Checks if the value is not <code>null</code> or empty. A single space is
     * considered a valid value.
     * 
     * @param name
     *            Name of the value for a possible error message.
     * @param value
     *            Value to check.
     * 
     * @throws ContractViolationException
     *             The value was null or empty.
     */
    // CHECKSTYLE:OFF:RedundantThrows
    public static void requireArgNotEmpty(@NotNull final String name, final String value)
            throws ContractViolationException {
        // CHECKSTYLE:ON
        requireArgNotNull(name, value);
        if (value.length() < 1) {
            throw new ContractViolationException("The argument '" + name + "' cannot be empty");
        }
    }

    /**
     * Checks if the given value is valid.
     * 
     * @param value
     *            Value to check.
     * 
     * @throws ContractViolationException
     *             The value is invalid.
     */
    // CHECKSTYLE:OFF:RedundantThrows
    public static void requireValid(@NotNull final Object value) throws ContractViolationException {
        // CHECKSTYLE:ON

        final Set<ConstraintViolation<Object>> constraintViolations = getValidator()
                .validate(value);
        if (constraintViolations.size() > 0) {
            final StringBuffer sb = new StringBuffer();
            for (final ConstraintViolation<Object> constraintViolation : constraintViolations) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append("[" + constraintViolation.getPropertyPath() + "] "
                        + constraintViolation.getMessage() + " {"
                        + constraintViolation.getInvalidValue() + "}");
            }
            throw new ContractViolationException(sb.toString(), constraintViolations);
        }

    }

    /**
     * Validates the given object using a default validator.
     * 
     * @param value
     *            Value to validate or NULL.
     * 
     * @return List of constraint violations - Never NULL, but may be empty.
     * 
     * @param <TYPE>
     *            Type of the validated object.
     */
    public static <TYPE> Set<ConstraintViolation<TYPE>> validate(final TYPE value) {
        if (value == null) {
            return new HashSet<ConstraintViolation<TYPE>>();
        }
        return getValidator().validate(value);
    }

}
