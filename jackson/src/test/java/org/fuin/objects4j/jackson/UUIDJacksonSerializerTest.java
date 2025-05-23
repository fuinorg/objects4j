/**
 * Copyright (C) 2013 Future Invent Informationsmanagement GmbH. All rights
 * reserved. <http://www.fuin.org/>
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fuin.objects4j.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for the {@link UUIDJacksonSerializer} class.
 */
public class UUIDJacksonSerializerTest {

    private static final UUID ID = UUID.fromString("ec5468bd-9cd9-4a0f-a502-96190f6a0995");

    private static final String JSON = "{\"uuid\":\"ec5468bd-9cd9-4a0f-a502-96190f6a0995\"}";

    @Test
    void testMarshalJsonb() throws JsonProcessingException {

        final Data data = new Data();
        data.uuid = ID;
        assertThat(JacksonHelper.toJson(data)).isEqualTo(JSON);

    }


}
