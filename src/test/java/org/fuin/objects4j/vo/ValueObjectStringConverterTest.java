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

import static org.assertj.core.api.Assertions.assertThat;
import static org.fuin.objects4j.vo.JsonbHelper.fromJson;
import static org.fuin.objects4j.vo.JsonbHelper.toJson;
import static org.fuin.utils4j.JaxbUtils.XML_PREFIX;
import static org.fuin.utils4j.JaxbUtils.marshal;
import static org.fuin.utils4j.JaxbUtils.unmarshal;

import javax.xml.bind.JAXBException;

import org.fuin.units4j.AbstractPersistenceTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// CHECKSTYLE:OFF
public class ValueObjectStringConverterTest extends AbstractPersistenceTest {

    private static final String XML = XML_PREFIX + "<data any-str=\"abcd1234\"/>";

    private static final String JSON = "{\"any-str\":\"abcd1234\"}";
    
    private ValueObjectStringConverter<AnyStr> testee;

    @Before
    public void setup() {
        testee = new AnyStrConverter();
    }

    @After
    public void teardown() {
        testee = null;
    }

    @Test
    public final void testFactoryInjectable() {
        assertThat(testee).isNotNull();
    }

    @Test
    public final void testMarshal() throws JAXBException {

        final Data data = new Data();
        data.anyStr = new AnyStr("abcd1234");
        assertThat(marshal(data, Data.class)).isEqualTo(XML);

    }

    @Test
    public final void testMarshalUnmarshal() throws JAXBException {

        final Data data = unmarshal(XML, Data.class);
        assertThat(data.anyStr).isNotNull();
        assertThat(data.anyStr).isEqualTo(new AnyStr("abcd1234"));

    }

    @Test
    public final void testMarshalJsonb() {

        final Data data = new Data();
        data.anyStr = new AnyStr("abcd1234");
        assertThat(toJson(data, new AnyStrConverter())).isEqualTo(JSON);

    }

    @Test
    public final void testMarshalUnmarshalJsonb() {

        final Data data = fromJson(JSON, Data.class, new AnyStrConverter());
        assertThat(data.anyStr).isEqualTo(new AnyStr("abcd1234"));

    }

    @Test
    public final void testJPA() {

        // PREPARE
        beginTransaction();
        getEm().persist(new AnyStrParentEntity(1));
        commitTransaction();

        // TEST UPDATE
        final AnyStr anyStr = new AnyStr("Whatever");
        beginTransaction();
        final AnyStrParentEntity entity = getEm().find(AnyStrParentEntity.class, 1L);
        entity.setAnyStr(anyStr);
        commitTransaction();

        // VERIFY
        beginTransaction();
        final AnyStrParentEntity copy = getEm().find(AnyStrParentEntity.class, 1L);
        assertThat(copy).isNotNull();
        assertThat(copy.getId()).isEqualTo(1);
        assertThat(copy.getAnyStr()).isNotNull();
        assertThat(copy.getAnyStr()).isEqualTo(anyStr);
        commitTransaction();

    }

}
// CHECKSTYLE:OFF
