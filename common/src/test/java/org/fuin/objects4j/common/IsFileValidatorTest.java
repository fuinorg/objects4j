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

public final class IsFileValidatorTest {

    private IsFileValidator testee;

    private Validator validator;

    private File existingFile;
    private File existingDir;

    @BeforeEach
    public final void setUp() throws IOException {
        testee = new IsFileValidator();
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        existingFile = new File(Utils4J.getTempDir(), "IsFileValidatorTest_File");
        try (final OutputStream out = new FileOutputStream(existingFile)) {
            out.write("Test".getBytes());
        }
        existingDir = new File(Utils4J.getTempDir(), "IsFileValidatorTest_Dir");
        existingDir.mkdir();
    }

    @AfterEach
    public final void tearDown() {
        testee = null;
        existingFile.delete();
        existingDir.delete();
        existingFile = null;
        existingDir = null;
    }

    @Test
    void testIsValidTRUE() {

        // PREPARE
        assertThat(existingFile).exists();
        assertThat(existingFile).isFile();

        // TEST & VERIFY
        assertThat(testee.isValid(existingFile, null)).isTrue();
        assertThat(testee.isValid(null, null)).isTrue();

        // TEST & VERIFY
        assertThat(validator.validate(new MyClass(existingFile))).isEmpty();
        assertThat(validator.validate(new MyClass(null))).isEmpty();

    }

    @Test
    void testIsValidFALSE() {

        // PREPARE
        assertThat(existingDir).exists();
        assertThat(existingDir).isDirectory();

        // TEST & VERIFY
        assertThat(testee.isValid(existingDir, null)).isFalse();

        // TEST & VERIFY
        assertThat(validator.validate(new MyClass(existingDir)))
                .anyMatch(v -> v.getMessage().contains("Not a file"));

    }

    @Test
    void testRequireArgValidTRUE() {

        // PREPARE
        assertThat(existingFile).exists();
        assertThat(existingFile).isFile();

        // TEST & VERIFY
        IsFileValidator.requireArgValid("x", existingFile);

    }

    @Test
    void testRequireArgValidFALSE() {

        // PREPARE
        assertThat(existingDir).exists();
        assertThat(existingDir).isDirectory();

        // TEST & VERIFY
        try {
            IsFileValidator.requireArgValid("x", existingDir);
            Assertions.fail("Expected exception");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).startsWith("The argument 'x' is not a file:");
        }

    }

    private record MyClass(@IsFile File file) {}

}
