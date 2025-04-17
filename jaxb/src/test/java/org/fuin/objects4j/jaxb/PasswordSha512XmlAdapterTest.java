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
package org.fuin.objects4j.jaxb;

import jakarta.xml.bind.JAXBException;
import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.core.PasswordSha512;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fuin.utils4j.jaxb.JaxbUtils.XML_PREFIX;

//CHECKSTYLE:OFF
public class PasswordSha512XmlAdapterTest {

    private static final String HASH = "925f43c3cfb956bbe3c6aa8023ba7ad5cfa21d104186fffc69e768e55940d9653b1cd36fba614fba2e1844f4436da20f83750c6ec1db356da154691bdd71a9b1";

    private static final String XML = XML_PREFIX + "<data passwordSha512=\"" + HASH + "\"/>";

    @Test
    public final void testMarshal() throws JAXBException {

        final Data data = new Data();
        data.passwordSha512 = new PasswordSha512(HASH);
        assertThat(JaxbHelper.marshalData(data)).isEqualTo(XML);

    }

    @Test
    public final void testMarshalUnmarshal() throws JAXBException {

        final Data data = JaxbHelper.unmarshalData(XML);
        assertThat(data.passwordSha512).isEqualTo(new PasswordSha512(HASH));

    }

    @Test
    public final void testUnmarshalError() {

        final String invalidXmlData = XML_PREFIX + "<data passwordSha512=\"1\"/>";
        assertThatThrownBy(() -> JaxbHelper.unmarshalData(invalidXmlData))
                .hasRootCauseInstanceOf(ConstraintViolationException.class)
                .hasRootCauseMessage("The argument 'hexEncodedHash' is not valid");

    }
}
