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
package org.fuin.objects4j.vo;

import static org.fest.assertions.Assertions.assertThat;

import org.fuin.objects4j.common.AbstractPersistenceTest;
import org.fuin.objects4j.common.ContractViolationException;
import org.junit.Test;

//TESTCODE:BEGIN
public final class EmailAddressTest extends AbstractPersistenceTest {

    @Test
    public final void testConstructValid() {
        final String emailAddress = "abc@def.com";
        assertThat(new EmailAddress(emailAddress).toString()).isEqualTo(emailAddress);
        assertThat(new EmailAddress(emailAddress).length()).isEqualTo(emailAddress.length());
    }

    @Test(expected = ContractViolationException.class)
    public final void testConstructEmpty() {
        new EmailAddress("");
    }

    @Test(expected = ContractViolationException.class)
    public final void testConstructIllegal() {
        new EmailAddress("abc@");
    }

    @Test
    public final void testEqualsLowerUpperCase() {
        assertThat(new EmailAddress("Abc@DeF.Com")).isEqualTo(new EmailAddress("aBc@deF.cOM"));
    }

    @Test
    public void testJPA() {

        // PREPARE
        beginTransaction();
        getEm().persist(new EmailAddressParentEntity(1));
        commitTransaction();

        // TEST UPDATE
        beginTransaction();
        final EmailAddressParentEntity entity = getEm().find(EmailAddressParentEntity.class, 1L);
        entity.setEmailAddress(new EmailAddress("a@b.c"));
        commitTransaction();

        // VERIFY
        beginTransaction();
        final EmailAddressParentEntity copy = getEm().find(EmailAddressParentEntity.class, 1L);
        assertThat(copy).isNotNull();
        assertThat(copy.getId()).isEqualTo(1);
        assertThat(copy.getEmailAddress()).isNotNull();
        assertThat(copy.getEmailAddress().toString()).isEqualTo("a@b.c");
        commitTransaction();

    }

}
// TESTCODE:END
