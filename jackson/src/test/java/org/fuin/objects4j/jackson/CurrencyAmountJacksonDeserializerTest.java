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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.fuin.objects4j.core.CurrencyAmount;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test for {@link CurrencyAmount} deserialization.
 */
class CurrencyAmountJacksonDeserializerTest {

    private static final String JSON = "{\"ca\":\"1234.56 EUR\"}";

    @Test
    void testSerializeDeserializeJackson() throws JsonProcessingException {

        final Data data = JacksonHelper.fromJson(JSON, Data.class);
        assertThat(data.currencyAmount).isEqualTo(new CurrencyAmount("1234.56", "EUR"));

    }

    @Test
    void testDeserializeErrorJackson() {

        final String invalidJsonData = "{\"ca\":\"1234.56\"}";
        assertThatThrownBy(() -> JacksonHelper.fromJson(invalidJsonData, Data.class))
                .hasRootCauseInstanceOf(IllegalArgumentException.class)
                .hasRootCauseMessage("No space character found in '1234.56'");

    }

}
