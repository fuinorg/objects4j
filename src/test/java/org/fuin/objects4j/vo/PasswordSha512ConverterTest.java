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
import static org.junit.Assert.fail;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBException;

import org.fuin.units4j.WeldJUnit4Runner;
import org.junit.Test;
import org.junit.runner.RunWith;

//CHECKSTYLE:OFF
@RunWith(WeldJUnit4Runner.class)
public class PasswordSha512ConverterTest extends ValueObjectConverterTest {

    private static final String HASH = "925f43c3cfb956bbe3c6aa8023ba7ad5cfa21d104186fffc69e768e55940d9653b1cd36fba614fba2e1844f4436da20f83750c6ec1db356da154691bdd71a9b1";

    private static final String XML = XML_PREFIX + "<data passwordSha512=\"" + HASH + "\"/>";

    @Inject
    private ValueObjectConverter<String, PasswordSha512> testee;

    @Test
    public final void testFactoryInjectable() {
        assertThat(testee).isNotNull();
    }

    @Test
    public final void testToVO() {
        assertThat(testee.toVO(HASH)).isEqualTo(new PasswordSha512(HASH));
    }

    @Test
    public final void testIsValid() {
        assertThat(testee.isValid(null)).isTrue();
        assertThat(testee.isValid(HASH)).isTrue();
        assertThat(testee.isValid("abcd123")).isFalse();
        assertThat(testee.isValid("")).isFalse();
    }

    @Test
    public final void testGetSimpleValueObjectClass() {
        assertThat(testee.getValueObjectClass()).isSameAs(PasswordSha512.class);
    }

    @Test
    public final void testMarshal() throws JAXBException {

        final Data data = new Data();
        data.passwordSha512 = new PasswordSha512(HASH);
        assertThat(marshal(data)).isEqualTo(XML);

    }

    @Test
    public final void testMarshalUnmarshal() throws JAXBException {

        final Data data = unmarshal(XML);
        assertThat(data.passwordSha512).isEqualTo(new PasswordSha512(HASH));

    }

    @Test
    public final void testUnmarshalError() {

        final String invalidHashInXmlData = XML_PREFIX + "<data passwordSha512=\"1\"/>";
        try {
            unmarshal(invalidHashInXmlData);
            fail("Expected an exception");
        } catch (final RuntimeException ex) {
            assertCauseCauseMessage(ex, "The argument 'hexEncodedHash' is not valid");
        }

    }

    @Test
    public final void testValidation() {

        final Data data = new Data();
        data.passwordSha512 = new PasswordSha512(HASH);
        // Set intentionally an illegal value
        // This may happen when deserialized
        setPrivateField(data.passwordSha512, "hash", "1");
        final Set<ConstraintViolation<Object>> violations = validate(data);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(
                "is not a valid HEX encoded SHA512 password hash");

    }

}
// CHECKSTYLE:OFF