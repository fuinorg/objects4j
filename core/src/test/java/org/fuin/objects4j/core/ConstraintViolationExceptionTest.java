package org.fuin.objects4j.core;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Test for the {@link ConstraintViolationException} class.
 */
class ConstraintViolationExceptionTest {

    @Test
    void testCreate() {

        // GIVEN
        try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<Object>> violations = validator.validate(new Example());

            // WHEN
            final ConstraintViolationException testee = new ConstraintViolationException(violations);

            // THEN
            assertThat(testee.getViolations()).containsOnly(violations.toArray(new ConstraintViolation[0]));
            assertThat(testee.getMessage()).contains("Example.name");
            assertThat(testee.getMessage()).contains("Example.id");

        }

    }

    private static final class Example {

        @NotNull
        private Integer id;

        @NotEmpty
        private String name;

    }


}
