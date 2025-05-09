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

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.assertj.core.api.Assertions;
import org.fuin.utils4j.Utils4J;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.assertj.core.api.Assertions.assertThat;

public final class FileExistsValidatorTest {

    private FileExistsValidator testee;

    private File existingFile;
    private File existingDir;
    private File notExisting;

    @BeforeEach
    public final void setUp() throws IOException {
        testee = new FileExistsValidator();
        existingFile = new File(Utils4J.getTempDir(), "FileExistsValidatorTest_File");
        try (final OutputStream out = new FileOutputStream(existingFile)) {
            out.write("Test".getBytes());
        }
        existingDir = new File(Utils4J.getTempDir(), "FileExistsValidatorTest_Dir");
        existingDir.mkdir();
        notExisting = new File(Utils4J.getTempDir(), "FileExistsValidatorTest_DoesNotExist");
    }

    @AfterEach
    public final void tearDown() {
        testee = null;
        existingFile.delete();
        existingDir.delete();
        existingFile = null;
        existingDir = null;
        notExisting = null;
    }

    @Test
    void testValidation() {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        assertThat(validator.validate(new MyClass(existingFile))).isEmpty();
        assertThat(validator.validate(new MyClass(existingDir))).isEmpty();
        assertThat(validator.validate(new MyClass(null))).isEmpty();
        assertThat(validator.validate(new MyClass(notExisting)))
                .anyMatch(v -> v.getMessage().contains("does not exist"));
    }

    @Test
    void testIsValidTRUE() {

        // PREPARE
        assertThat(existingFile).exists();
        assertThat(existingDir).exists();

        // TEST & VERIFY
        assertThat(testee.isValid(existingFile, null)).isTrue();
        assertThat(testee.isValid(existingDir, null)).isTrue();
        assertThat(testee.isValid(null, null)).isTrue();

    }

    @Test
    void testIsValidFALSE() {

        // PREPARE
        assertThat(notExisting).doesNotExist();

        // TEST & VERIFY
        assertThat(testee.isValid(notExisting, null)).isFalse();

    }

    @Test
    void testRequireArgValidTRUE() {

        // PREPARE
        assertThat(existingFile).exists();
        assertThat(existingDir).exists();

        // TEST & VERIFY
        FileExistsValidator.requireArgValid("x", existingFile);
        FileExistsValidator.requireArgValid("x", existingDir);

    }

    @Test
    void testRequireArgValidFALSE() {

        // PREPARE
        assertThat(notExisting).doesNotExist();

        // TEST & VERIFY
        try {
            FileExistsValidator.requireArgValid("x", notExisting);
            Assertions.fail("Expected exception");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).startsWith("The argument 'x' is not an existing file:");
        }

    }

    public record MyClass(@FileExists File file) {}

}
