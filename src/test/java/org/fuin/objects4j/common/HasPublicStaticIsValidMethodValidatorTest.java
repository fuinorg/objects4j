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
    public final void testValid() {
        assertThat(validator.validate(new MyClassValid())).isEmpty();
        assertThat(HasPublicStaticIsValidMethodValidator.findMethod(MyClassValid.class, "isValid", String.class)).isNotNull();
        assertThat(HasPublicStaticIsValidMethodValidator.findFunction(MyClassValid.class, "isValid", String.class)).isNotNull();
    }

    @Test
    public final void testNotStatic() {

        assertThat(first(validator.validate(new MyClassNotStatic()))).contains("#1");

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
    public final void testNotPublic() {

        assertThat(first(validator.validate(new MyClassNotPublic()))).contains("#2");

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
    public final void testWrongReturnType() {

        assertThat(first(validator.validate(new MyClassWrongReturnType()))).contains("#3");

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
    public final void testWrongReturn() {

        assertThat(first(validator.validate(new MyClassWrongReturnValue()))).contains("#4");

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
    public final void testNoMethod() {

        assertThat(first(validator.validate(new MyClassNoMethod()))).contains("#2");

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

    @HasPublicStaticIsValidMethod
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
