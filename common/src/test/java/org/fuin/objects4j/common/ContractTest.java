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

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ContractTest {

    @Test
    void testRequireArgNotNull() {

        // TEST
        Contract.requireArgNotNull("name", "Whatever");

        // TEST
        try {
            Contract.requireArgNotNull("name", null);
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'name' cannot be null");
        }
    }

    @Test
    void testRequireArgNotEmpty() {

        // TEST
        Contract.requireArgNotNull("name", "Whatever");

        // TEST
        try {
            Contract.requireArgNotNull("name", null);
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'name' cannot be null");
        }

        // TEST
        try {
            Contract.requireArgNotNull("name", "");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'name' cannot be empty");
        }

    }

    @Test
    void testValidateWithValidator() {

        final Child child = new Child();
        child.password = "abc";
        final Set<ConstraintViolation<Child>> violations = Contract.validate(Validation.buildDefaultValidatorFactory().getValidator(),
                child);
        assertThat(violations).isNotNull();
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessageTemplate()).isEqualTo("{jakarta.validation.constraints.NotEmpty.message}");

    }

    @Test
    void testValidateWithDefaultValidator() {

        final Child child = new Child();
        child.userName = "peter";
        final Set<ConstraintViolation<Child>> violations = Contract.validate(child);
        assertThat(violations).isNotNull();
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessageTemplate()).isEqualTo("{jakarta.validation.constraints.NotNull.message}");

    }

    @Test
    void testRequireValidWithValidator() {

        try {
            final Child child = new Child();
            child.userName = "peter";
            Contract.requireValid(Validation.buildDefaultValidatorFactory().getValidator(), child);
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).contains("[password]");
            assertThat(ex.getMessage()).contains("{null}");
            assertThat(ex.getMessage()).doesNotContain("[userName]");
        }

    }

    @Test
    void testRequireValidWithOneInvalidField() {

        try {
            final Child child = new Child();
            child.userName = "peter";
            Contract.requireValid(child);
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).contains("[password]");
            assertThat(ex.getMessage()).contains("{null}");
            assertThat(ex.getMessage()).doesNotContain("[userName]");
        }

    }

    @Test
    void testRequireValidWithTwoInvalidFields() {

        try {
            final Child child = new Child();
            child.userName = "";
            Contract.requireValid(child);
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).contains("[password]");
            assertThat(ex.getMessage()).contains("{null}");
            assertThat(ex.getMessage()).contains("[userName]");
            assertThat(ex.getMessage()).contains("{}");
        }

    }

    @Test
    void testRequireValidWithInvalidChild() {

        try {
            final Parent parent = new Parent();
            parent.child = new Child();
            Contract.requireValid(parent);
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).contains("[email] must not be empty");
            assertThat(ex.getMessage()).contains("[child.password] must not be null");
            assertThat(ex.getMessage()).contains("[child.userName] must not be empty");
        }

    }

    @Test
    void testRequireValidWithValidChild() {

        try {
            final Parent parent = new Parent();
            parent.child = new Child();
            parent.child.userName = "peter";
            parent.child.password = "verysecret";
            Contract.requireValid(parent);
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).contains("[email] must not be empty");
        }

    }

    @Test
    public void testRequireArgMaxLength() {

        // TEST
        Contract.requireArgMaxLength("name", "123", 3);

        // TEST
        Contract.requireArgMaxLength("name", "12", 3);

        // TEST
        try {
            Contract.requireArgMaxLength("name", "123", 2);
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("Max length of argument 'name' is 2, but was: 3");
        }

    }

    @Test
    public void testRequireArgMinLength() {

        // TEST
        Contract.requireArgMinLength("name", "1234", 4);

        // TEST
        try {
            Contract.requireArgMinLength("name", "123", 4);
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("Min length of argument 'name' is 4, but was: 3");
        }

    }

    @Test
    public void testRequireArgMax() {

        // TEST
        Contract.requireArgMax("name", 4, 5);

        // TEST
        Contract.requireArgMax("name", 5, 5);

        // TEST
        try {
            Contract.requireArgMax("name", 6, 5);
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("Max value of argument 'name' is 5, but was: 6");
        }

    }

    @Test
    public void testRequireArgMin() {

        // TEST
        Contract.requireArgMin("name1", 5, 4);
        Contract.requireArgMin("name3", 4, 4);
        Contract.requireArgMin("name3", 5, 4);
        Contract.requireArgMin("name4", 5L, 4L);
        Contract.requireArgMin("name5", 5L, 4L);
        try {
            Contract.requireArgMin("name6", 3, 4);
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("Min value of argument 'name6' is 4, but was: 3");
        }

    }

    @Test
    public void testAsStringListSetConstraintViolation() {

        // PREPARE
        final Parent parent = new Parent();
        parent.child = new Child();
        final Set<ConstraintViolation<Parent>> violations = Contract.validate(parent);

        // TEST
        final List<String> result = Contract.asString(violations);

        // VERIFY
        assertThat(result).containsOnly("Parent.email must not be empty",
                "Parent.child.userName must not be empty",
                "Parent.child.password must not be null");

        // TEST & VERIFY
        assertThat(Contract.asString((Set<ConstraintViolation<Parent>>) null)).isEmpty();
        assertThat(Contract.asString(new HashSet<>())).isEmpty();

    }

    @Test
    public void testAsStringSetConstraintViolation() {

        // PREPARE
        final Parent parent = new Parent();
        parent.child = new Child();
        final Set<ConstraintViolation<Parent>> violations = Contract.validate(parent);

        // TEST & VERIFY
        final String str1 = Contract.asString(violations, " / ");
        assertThat(str1).contains("Parent.email must not be empty");
        assertThat(str1).contains("Parent.child.password must not be null");
        assertThat(str1).contains("Parent.child.userName must not be empty");
        assertThat(str1).contains(" / ");

        final String str2 = Contract.asString(violations, null);
        assertThat(str2).contains("Parent.email must not be empty");
        assertThat(str2).contains("Parent.child.password must not be null");
        assertThat(str2).contains(", ");

        // TEST & VERIFY
        assertThat(Contract.asString((Set<ConstraintViolation<Parent>>) null, ",")).isEqualTo("");
        assertThat(Contract.asString((Set<ConstraintViolation<Parent>>) null, null)).isEqualTo("");
        assertThat(Contract.asString(new HashSet<>(), ", ")).isEqualTo("");
        assertThat(Contract.asString(new HashSet<>(), null)).isEqualTo("");

    }

    @Test
    public void testAsStringConstraintViolation() {

        // PREPARE
        final Child child = new Child();
        child.userName = "peter";
        final Set<ConstraintViolation<Child>> violations = Contract.validate(Validation.buildDefaultValidatorFactory().getValidator(),
                child);

        // TEST
        final String result = Contract.asString(violations.iterator().next());

        // VERIFY
        assertThat(result).isEqualTo("Child.password must not be null");

    }

    // Yes, the classes don't have getters and setters... ;-)

    private static class Parent {

        @NotEmpty
        private String email;

        @Valid
        private Child child;

    }

    private static class Child {

        @NotEmpty
        private String userName;

        @NotNull
        private String password;

    }

}
