/**
 * Copyright (C) 2013 Future Invent Informationsmanagement GmbH. All rights
 * reserved. <http://www.fuin.org/>
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
 * along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fuin.objects4j.common;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public final class HasPublicStaticIsValidMethodValidatorTest {

    private static Validator validator;

    @BeforeAll
    static void beforeAll() {
        try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            validator = validatorFactory.getValidator();
        }
    }

    @Test
    void testValid() {
        assertThat(validator.validate(new MyClassValid())).isEmpty();
        assertThat(HasPublicStaticIsValidMethodValidator.findMethod(MyClassValid.class, "isValid", String.class)).isNotNull();
        assertThat(HasPublicStaticIsValidMethodValidator.findFunction(MyClassValid.class, "isValid", String.class)).isNotNull();
    }

    @Test
    void testNotStatic() {

        assertThat(first(validator.validate(new MyClassNotStatic()))).isEqualTo("Method 'isValid' is not static (#1)");

        assertThatThrownBy(
                () -> HasPublicStaticIsValidMethodValidator.findMethod(MyClassNotStatic.class, "isValid", String.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("#1");

        assertThatThrownBy(
                () -> HasPublicStaticIsValidMethodValidator.findFunction(MyClassNotStatic.class, "isValid", String.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("#1");

    }

    @Test
    void testNotPublic() {

        assertThat(first(validator.validate(new MyClassNotPublic()))).isEqualTo("The method 'isValid' is undefined or it is not public (#2)");

        assertThatThrownBy(
                () -> HasPublicStaticIsValidMethodValidator.findMethod(MyClassNotPublic.class, "isValid", String.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("#2");

        assertThatThrownBy(
                () -> HasPublicStaticIsValidMethodValidator.findFunction(MyClassNotPublic.class, "isValid", String.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("#2");

    }

    @Test
    void testWrongReturnType() {

        assertThat(first(validator.validate(new MyClassWrongReturnType()))).isEqualTo("Method 'isValid' does not return 'org.fuin.objects4j.common.HasPublicStaticIsValidMethodValidatorTest$MyClassWrongReturnType', but: java.lang.Boolean (#3)");

        assertThatThrownBy(
                () -> HasPublicStaticIsValidMethodValidator.findMethod(MyClassWrongReturnType.class, "isValid", String.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("#3");

        assertThatThrownBy(
                () -> HasPublicStaticIsValidMethodValidator.findFunction(MyClassWrongReturnType.class, "isValid", String.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("#3");

    }

    @Test
    void testWrongReturn() {

        assertThat(first(validator.validate(new MyClassWrongReturnValue()))).isEqualTo("Method 'isValid' is expected to return 'true' on 'null' argument, but was 'false' (#4)");

        assertThatThrownBy(
                () -> HasPublicStaticIsValidMethodValidator.findMethod(MyClassWrongReturnValue.class, "isValid", String.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("#4");

        assertThatThrownBy(
                () -> HasPublicStaticIsValidMethodValidator.findFunction(MyClassWrongReturnValue.class, "isValid", String.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("#4");


    }

    @Test
    void testNoMethod() {

        assertThat(first(validator.validate(new MyClassNoMethod()))).isEqualTo("The method 'isValid' is undefined or it is not public (#2)");

        assertThatThrownBy(
                () -> HasPublicStaticIsValidMethodValidator.findMethod(MyClassNoMethod.class, "isValid", String.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("#2");

        assertThatThrownBy(
                () -> HasPublicStaticIsValidMethodValidator.findFunction(MyClassNoMethod.class, "isValid", String.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("#2");

    }

    private static String first(Set<?> violations) {
        return violations.stream().map(v -> ((ConstraintViolation<?>) v).getMessage()).findFirst().orElse(null);
    }

    @HasPublicStaticIsValidMethod(method = "isValid", param = String.class)
    public static final class MyClassValid {
        public static boolean isValid(String str) {
            if (str == null) {
                return true;
            }
            return true;
        }
    }

    @HasPublicStaticIsValidMethod
    public static final class MyClassNotStatic {
        @SuppressWarnings("java:S1172") // Unused method parameter is ok here
        public boolean isValid(String str) {
            return true;
        }
    }

    @HasPublicStaticIsValidMethod
    public static final class MyClassNotPublic {
        protected static boolean isValid(String str) {
            if (str == null) {
                return true;
            }
            return true;
        }
    }

    @HasPublicStaticIsValidMethod
    public static final class MyClassNoMethod {
    }

    @HasPublicStaticIsValidMethod
    public static final class MyClassWrongReturnType {
        public static Boolean isValid(String str) {
            if (str == null) {
                return false;
            }
            return true;
        }
    }
    @HasPublicStaticIsValidMethod
    public static final class MyClassWrongReturnValue {
        public static boolean isValid(String str) {
            if (str == null) {
                return false;
            }
            return true;
        }
    }


}
