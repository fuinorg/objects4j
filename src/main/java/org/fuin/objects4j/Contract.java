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
package org.fuin.objects4j;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * Utility class for assertions on objects.
 */
public final class Contract {

    private static final Validator VALIDATOR;

    static {
        VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
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
    public static void requireArgNotNull(final String name, final Object value)
            throws ContractViolationException {
        if (value == null) {
            throw new ContractViolationException("The argument '" + name + "' cannot be null");
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
    public static void requireValid(final Object value) throws ContractViolationException {

        final Set<ConstraintViolation<Object>> constraintViolations = VALIDATOR.validate(value);
        if (constraintViolations.size() > 0) {
            final StringBuffer sb = new StringBuffer();
            for (final ConstraintViolation<Object> constraintViolation : constraintViolations) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append("[" + constraintViolation.getPropertyPath() + "] "
                        + constraintViolation.getMessage() + " {" + constraintViolation.getInvalidValue()
                        + "}");
            }
            throw new ContractViolationException(sb.toString(), constraintViolations);
        }

    }

}
