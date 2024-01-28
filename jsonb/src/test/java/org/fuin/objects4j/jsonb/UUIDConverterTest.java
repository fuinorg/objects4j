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
package org.fuin.objects4j.jsonb;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fuin.objects4j.jsonb.JsonbHelper.fromJson;
import static org.fuin.objects4j.jsonb.JsonbHelper.toJson;

/**
 * Test for the {@link UUIDConverter} class.
 */
class UUIDConverterTest {
    
    private static final UUID ID = UUID.fromString("ec5468bd-9cd9-4a0f-a502-96190f6a0995");

    private static final String JSON = "{\"uuid\":\"ec5468bd-9cd9-4a0f-a502-96190f6a0995\"}";

    @Test
    void testMarshalJsonb() {

        final Data data = new Data();
        data.uuid = ID;
        assertThat(toJson(data, new UUIDConverter())).isEqualTo(JSON);

    }

    @Test
    void testMarshalUnmarshalJsonb() {

        final Data data = fromJson(JSON, Data.class, new UUIDConverter());
        assertThat(data.uuid).isEqualTo(ID);

    }

    @Test
    void testUnmarshalErrorJsonb() {

        final String invalidJsonData = "{\"uuid\":\"foo\"}";
        assertThatThrownBy(() -> fromJson(invalidJsonData, Data.class, new UUIDConverter()))
                .hasRootCauseInstanceOf(IllegalArgumentException.class)
                .hasRootCauseMessage("Invalid UUID string: foo");

    }

}

