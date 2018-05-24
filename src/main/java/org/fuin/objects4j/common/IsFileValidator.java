/**
 * Copyright (C) 2015 Michael Schnell. All rights reserved. 
 * http://www.fuin.org/
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
 * along with this library. If not, see http://www.gnu.org/licenses/.
 */
package org.fuin.objects4j.common;

import java.io.File;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;

/**
 * Checks if a given file is a file (and not a directory).
 */
public final class IsFileValidator implements ConstraintValidator<IsFile, File> {

    @Override
    public final void initialize(final IsFile constraintAnnotation) {
        // Not used
    }

    @Override
    public final boolean isValid(final File file, final ConstraintValidatorContext context) {
        if (file == null) {
            return true;
        }
        return file.isFile();
    }

    /**
     * Checks if the file exists and is a file (not a directory).
     * 
     * @param name
     *            Name of the value for a possible error message.
     * @param value
     *            Value to check.
     * 
     * @throws ConstraintViolationException
     *             The file does not exist.
     */
    // CHECKSTYLE:OFF:RedundantThrows
    public static void requireArgValid(@NotNull final String name, @FileExists @NotNull final File value)
            throws ConstraintViolationException {
        // CHECKSTYLE:ON
        if (!value.isFile()) {
            throw new ConstraintViolationException("The argument '" + name + "' is not a file: '" + value + "'");
        }
    }

}
