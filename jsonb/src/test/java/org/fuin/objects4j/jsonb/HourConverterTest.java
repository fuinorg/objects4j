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

import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.core.Hour;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fuin.objects4j.jsonb.JsonbHelper.fromJson;
import static org.fuin.objects4j.jsonb.JsonbHelper.toJson;

/**
 * Test for the {@link HourConverter} class.
 */
class HourConverterTest {

    private static final String JSON = "{\"hour\":\"23:59\"}";

    @Test
    void testMarshalJsonb() {

        final Data data = new Data();
        data.hour = new Hour("23:59");
        assertThat(toJson(data, new HourConverter())).isEqualTo(JSON);

    }

    @Test
    void testMarshalUnmarshalJsonb() {

        final Data data = fromJson(JSON, Data.class, new HourConverter());
        assertThat(data.hour).isEqualTo(new Hour("23:59"));

    }

    @Test
    void testUnmarshalErrorJsonb() {

        final String invalidJsonData = "{\"hour\":\"23:\"}";
        assertThatThrownBy(() -> fromJson(invalidJsonData, Data.class, new HourConverter()))
                .hasRootCauseInstanceOf(ConstraintViolationException.class)
                .hasRootCauseMessage("The argument 'hour' does not represent a valid hour like '00:00' or '23:59' or '24:00': '23:'");

    }

}

