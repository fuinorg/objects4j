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
import org.fuin.objects4j.core.UserName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fuin.utils4j.jaxb.JaxbUtils.XML_PREFIX;

//CHECKSTYLE:OFF
public class UserNameXmlAdapterTest {

    private static final String USER_NAME = "michael-1_a";

    private static final String XML = XML_PREFIX + "<data userName=\"" + USER_NAME + "\"/>";

    @Test
    public final void testMarshal() throws JAXBException {

        final Data data = new Data();
        data.userName = new UserName(USER_NAME);
        assertThat(JaxbHelper.marshalData(data)).isEqualTo(XML);

    }

    @Test
    public final void testMarshalUnmarshal() throws JAXBException {

        final Data data = JaxbHelper.unmarshalData(XML);
        assertThat(data.userName).isEqualTo(new UserName(USER_NAME));

    }

    @Test
    public final void testUnmarshalError() {

        final String invalidXmlData = XML_PREFIX + "<data userName=\"x\"/>";
        assertThatThrownBy(() -> JaxbHelper.unmarshalData(invalidXmlData))
                .hasRootCauseInstanceOf(ConstraintViolationException.class)
                .hasRootCauseMessage("The argument 'userName' is not valid: 'x'");

    }

}
