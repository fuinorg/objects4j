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
import org.fuin.objects4j.core.UserName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fuin.objects4j.jsonb.JsonbHelper.fromJson;
import static org.fuin.objects4j.jsonb.JsonbHelper.toJson;

/**
 * Test for the {@link UserNameJsonbAdapter} class.
 */
class UserNameJsonbAdapterTest {

    private static final String USER_NAME = "michael-1_a";

    private static final String JSON = "{\"userName\":\"" + USER_NAME + "\"}";

    @Test
    void testMarshalJsonb() {

        final Data data = new Data();
        data.userName = new UserName(USER_NAME);
        assertThat(toJson(data)).isEqualTo(JSON);

    }

    @Test
    void testMarshalUnmarshalJsonb() {

        final Data data = fromJson(JSON, Data.class);
        assertThat(data.userName).isEqualTo(new UserName(USER_NAME));

    }

    @Test
    void testUnmarshalErrorJsonb() {

        final String invalidJsonData = "{\"userName\":\"x\"}";
        assertThatThrownBy(() -> fromJson(invalidJsonData, Data.class))
                .hasRootCauseInstanceOf(ConstraintViolationException.class)
                .hasRootCauseMessage("The argument 'userName' is not valid: 'x'");
    }

}
