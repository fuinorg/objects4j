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

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.fuin.objects4j.vo.EmailAddressStr;
import org.fuin.objects4j.vo.PasswordStr;
import org.fuin.objects4j.vo.UserNameStr;
import org.junit.Test;

//TESTCODE:BEGIN
public class ContractTest {

    @Test
    public final void testRequireArgNotNull() {

        try {
            Contract.requireArgNotNull("name", null);
        } catch (final ContractViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'name' cannot be null");
        }
    }

    @Test
    public final void testRequireArgNotEmpty() {

        try {
            Contract.requireArgNotNull("name", null);
        } catch (final ContractViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'name' cannot be null");
        }

        try {
            Contract.requireArgNotNull("name", "");
        } catch (final ContractViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'name' cannot be empty");
        }

    }

    @Test
    public final void testRequireValidWithOneInvalidField() {

        try {
            final Child child = new Child();
            Contract.requireValid(child);
            fail();
        } catch (final ContractViolationException ex) {
            assertThat(ex.getMessage()).contains("[password]");
            assertThat(ex.getMessage()).contains("{null}");
            assertThat(ex.getMessage()).doesNotContain("[userName]");
        }

    }

    @Test
    public final void testRequireValidWithTwoInvalidFields() {

        try {
            final Child child = new Child();
            child.userName = "";
            Contract.requireValid(child);
            fail();
        } catch (final ContractViolationException ex) {
            assertThat(ex.getMessage()).contains("[password]");
            assertThat(ex.getMessage()).contains("{null}");
            assertThat(ex.getMessage()).contains("[userName]");
            assertThat(ex.getMessage()).contains("{}");
        }

    }

    @Test
    public final void testRequireValidWithInvalidChild() {

        try {
            final Parent parent = new Parent();
            parent.email = "abc@";
            parent.child = new Child();
            Contract.requireValid(parent);
            fail();
        } catch (final ContractViolationException ex) {
            assertThat(ex.getMessage()).contains("[email]");
            assertThat(ex.getMessage()).contains("{abc@}");
            assertThat(ex.getMessage()).contains("[child.password]");
            assertThat(ex.getMessage()).contains("{null}");
        }

    }

    @Test
    public final void testRequireValidWithValidChild() {

        try {
            final Parent parent = new Parent();
            parent.email = "abc@";
            parent.child = new Child();
            parent.child.password = "verysecret";
            Contract.requireValid(parent);
            fail();
        } catch (final ContractViolationException ex) {
            assertThat(ex.getMessage()).contains("[email]");
            assertThat(ex.getMessage()).contains("{abc@}");
            assertThat(ex.getMessage()).doesNotContain("child");
            assertThat(ex.getMessage()).doesNotContain("userName");
            assertThat(ex.getMessage()).doesNotContain("password");
            assertThat(ex.getMessage()).doesNotContain("{null}");
            assertThat(ex.getMessage()).doesNotContain("{}");
        }

    }

    // CHECKSTYLE:OFF

    // Yes, the classes don't have getters and setters... ;-)

    private static class Parent {

        @NotNull
        @EmailAddressStr
        private String email;

        @Valid
        private Child child;

    }

    private static class Child {

        @UserNameStr
        private String userName;

        @NotNull
        @PasswordStr
        private String password;

    }
    // CHECKSTYLE:ON

}
// TESTCODE:END
