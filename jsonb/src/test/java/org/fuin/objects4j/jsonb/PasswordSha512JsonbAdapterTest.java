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
import org.fuin.objects4j.core.PasswordSha512;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.fuin.objects4j.jsonb.JsonbHelper.fromJson;
import static org.fuin.objects4j.jsonb.JsonbHelper.toJson;

/**
 * Test for the {@link PasswordSha512JsonbAdapter} class.
 */
public class PasswordSha512JsonbAdapterTest {

    private static final String HASH = "925f43c3cfb956bbe3c6aa8023ba7ad5cfa21d104186fffc69e768e55940d9653b1cd36fba614fba2e1844f4436da20f83750c6ec1db356da154691bdd71a9b1";

    private static final String JSON = "{\"passwordSha512\":\"" + HASH + "\"}";

    @Test
    void testMarshalJsonb() {

        final Data data = new Data();
        data.passwordSha512 = new PasswordSha512(HASH);
        assertThat(toJson(data)).isEqualTo(JSON);

    }

    @Test
    void testMarshalUnmarshalJsonb() {

        final Data data = fromJson(JSON, Data.class);
        assertThat(data.passwordSha512).isEqualTo(new PasswordSha512(HASH));

    }

    @Test
    void testUnmarshalErrorJsonb() {

        final String invalidJsonData = "{\"passwordSha512\":\"1\"}";
        assertThatThrownBy(() -> fromJson(invalidJsonData, Data.class))
                .hasRootCauseInstanceOf(ConstraintViolationException.class)
                .hasRootCauseMessage("The argument 'hexEncodedHash' is not valid");
    }

}

