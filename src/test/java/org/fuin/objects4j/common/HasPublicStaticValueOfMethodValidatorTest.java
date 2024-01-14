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

public final class HasPublicStaticValueOfMethodValidatorTest {

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
    }

    @Test
    public final void testNotStatic() {
        assertThat(first(validator.validate(new MyClassNotStatic()))).contains("#1");
    }

    @Test
    public final void testNotPublic() {
        assertThat(first(validator.validate(new MyClassNotPublic()))).contains("#2");
    }

    @Test
    public final void testWrongReturnType() {
        assertThat(first(validator.validate(new MyClassWrongReturnType()))).contains("#3");
    }

    @Test
    public final void testWrongReturn() {
        assertThat(first(validator.validate(new MyClassWrongReturnValue()))).contains("#4");
    }

    @Test
    public final void testNoMethod() {
        assertThat(first(validator.validate(new MyClassNoMethod()))).contains("#2");
    }

    private static String first(Set<?> violations) {
        return violations.stream().map(v -> ((ConstraintViolation<?>) v).getMessage()).findFirst().orElse(null);
    }

    @HasPublicStaticValueOfMethod
    public static final class MyClassValid {
        public static MyClassValid valueOf(String str) {
            if (str == null) {
                return null;
            }
            return new MyClassValid();
        }
    }

    @HasPublicStaticValueOfMethod
    public static final class MyClassNotStatic {
        public MyClassNotStatic valueOf(String str) {
            if (str == null) {
                return null;
            }
            return new MyClassNotStatic();
        }
    }

    @HasPublicStaticValueOfMethod
    public static final class MyClassNotPublic {
        protected static MyClassNotPublic valueOf(String str) {
            if (str == null) {
                return null;
            }
            return new MyClassNotPublic();
        }
    }

    @HasPublicStaticValueOfMethod
    public static final class MyClassNoMethod {
    }

    @HasPublicStaticValueOfMethod
    public static final class MyClassWrongReturnType {
        public static Integer valueOf(String str) {
            if (str == null) {
                return null;
            }
            return 0;
        }
    }

    @HasPublicStaticValueOfMethod
    public static final class MyClassWrongReturnValue {
        public static MyClassWrongReturnValue valueOf(String str) {
            return new MyClassWrongReturnValue();
        }
    }


}
