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

import java.io.File;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;

/**
 * Checks if the file exists.
 */
public class FileExistsValidator implements ConstraintValidator<FileExists, File> {

    @Override
    public final void initialize(final FileExists constraintAnnotation) {
        // Not used
    }

    @Override
    public final boolean isValid(final File file, final ConstraintValidatorContext context) {
        if (file == null) {
            return true;
        }
        return file.exists();
    }

    /**
     * Checks if the file exists.
     * 
     * @param name
     *            Name of the value for a possible error message.
     * @param value
     *            Value to check.
     * 
     * @throws ContractViolationException
     *             The file does not exist.
     */
    // CHECKSTYLE:OFF:RedundantThrows
    public static void requireArgValid(@NotNull final String name, @NotNull final File value)
            throws ContractViolationException {
        // CHECKSTYLE:ON
        if (!value.exists()) {
            throw new ContractViolationException("The argument '" + name
                    + "' is not an existing file: '" + value + "'");
        }
    }

}
