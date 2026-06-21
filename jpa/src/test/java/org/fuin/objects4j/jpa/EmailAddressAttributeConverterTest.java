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
package org.fuin.objects4j.jpa;

import org.fuin.objects4j.core.EmailAddress;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public final class EmailAddressAttributeConverterTest {

    /**
     * The {@link EmailAddress} value object holds a {@code jakarta.mail.internet.InternetAddress} field, so the class can only be loaded
     * when the (optional) {@code jakarta.mail} implementation is on the runtime class path. The JPA test module does not declare it, so the
     * tests are skipped instead of failing in that case.
     *
     * @return {@code true} if {@link EmailAddress} can be loaded.
     */
    private static boolean emailAddressLoadable() {
        try {
            Class.forName("jakarta.mail.internet.InternetAddress", false, EmailAddressAttributeConverterTest.class.getClassLoader());
            return true;
        } catch (final ClassNotFoundException ex) {
            return false;
        }
    }

    @Test
    public void testNull() {
        assumeTrue(emailAddressLoadable());
        final EmailAddressAttributeConverter testee = new EmailAddressAttributeConverter();
        assertThat(testee.convertToDatabaseColumn(null)).isNull();
        assertThat(testee.convertToEntityAttribute(null)).isNull();
    }

    @Test
    public void testRoundTrip() {
        assumeTrue(emailAddressLoadable());
        final EmailAddressAttributeConverter testee = new EmailAddressAttributeConverter();
        final EmailAddress value = new EmailAddress("a@b.c");
        assertThat(testee.convertToEntityAttribute(testee.convertToDatabaseColumn(value))).isEqualTo(value);
    }

}
