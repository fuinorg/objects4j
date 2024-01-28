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

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fuin.objects4j.jsonb.JsonbHelper.fromJson;
import static org.fuin.objects4j.jsonb.JsonbHelper.toJson;

/**
 * Test for the {@link LocaleConverter} class.
 */
class LocaleConverterTest {

    private static final String LOCALE_STR = "en_US_NY";

    private static final Locale LOCALE = new Locale("en", "US", "NY");

    private static final String JSON = "{\"locale\":\"" +LOCALE_STR + "\"}";

    @Test
    void testMarshalJsonb() {

        final Data data = new Data();
        data.locale = LOCALE;
        assertThat(toJson(data, new LocaleConverter())).isEqualTo(JSON);

    }

    @Test
    void testMarshalUnmarshalJsonb() {

        final Data data = fromJson(JSON, Data.class, new LocaleConverter());
        assertThat(data.locale).isEqualTo(LOCALE);

    }

    @Test
    void testUnmarshalErrorJsonb() {

        final String invalidJsonData = "{\"locale\":\"\"}";
        assertThatThrownBy(() -> fromJson(invalidJsonData, Data.class, new LocaleConverter()))
                .hasRootCauseInstanceOf(IllegalArgumentException.class)
                .hasRootCauseMessage("Cannot convert: ''");

    }

}

