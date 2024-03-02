package org.fuin.objects4j.core;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.NotEmpty;
import org.fuin.objects4j.common.Contract;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * On or more constraint violations are considered an error.
 */
public final class ConstraintViolationException extends Exception {

    private final Set<ConstraintViolation<Object>> violations;

    /**
     * Constructor with mandatory data.
     *
     * @param violations Violations that caused the error.
     */
    public ConstraintViolationException(@NotEmpty final Set<ConstraintViolation<Object>> violations) {
        super(Contract.asString(violations, ", "));
        this.violations = Objects.requireNonNull(violations, "violations==null)");
        if (violations.isEmpty()) {
            throw new IllegalArgumentException("List of violations is empty");
        }
    }

    /**
     * Returns the violations that caused the error.
     *
     * @return Immutable set of violations.
     */
    @NotEmpty
    public Set<ConstraintViolation<Object>> getViolations() {
        return Collections.unmodifiableSet(violations);
    }
}
