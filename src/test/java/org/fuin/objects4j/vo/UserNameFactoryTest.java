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

import javax.inject.Inject;
import javax.xml.bind.JAXBException;

import org.fuin.units4j.WeldJUnit4Runner;
import org.junit.Test;
import org.junit.runner.RunWith;

//CHECKSTYLE:OFF
@RunWith(WeldJUnit4Runner.class)
public class UserNameFactoryTest extends SimpleValueObjectFactoryTest {

    private static final String USER_NAME = "michael-1_a";

    private static final String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + "<data userName=\"" + USER_NAME + "\"/>";

    @Inject
    private SimpleValueObjectFactory<String, UserName> testee;

    @Test
    public final void testFactoryInjectable() {
        assertThat(testee).isNotNull();
    }

    @Test
    public final void testCreate() {
        assertThat(testee.create(USER_NAME)).isEqualTo(new UserName(USER_NAME));
    }

    @Test
    public final void testIsValid() {
        assertThat(testee.isValid(null)).isTrue();
        assertThat(testee.isValid(USER_NAME)).isTrue();
        assertThat(testee.isValid("")).isFalse();
        assertThat(testee.isValid("a12345678901234567890")).isFalse();
    }

    @Test
    public final void testGetSimpleValueObjectClass() {
        assertThat(testee.getSimpleValueObjectClass()).isSameAs(UserName.class);
    }

    @Test
    public final void testMarshal() throws JAXBException {

        final Data data = new Data();
        data.userName = new UserName(USER_NAME);
        assertThat(marshal(data)).isEqualTo(XML);

    }

    @Test
    public final void testMarshalUnmarshal() throws JAXBException {

        final Data data = unmarshal(XML);
        assertThat(data.userName).isEqualTo(new UserName(USER_NAME));

    }

}
// CHECKSTYLE:OFF
