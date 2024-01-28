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
package org.fuin.objects4j.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fuin.utils4j.Utils4J.deserialize;
import static org.fuin.utils4j.Utils4J.serialize;

public class SecurityTokenTest {

    @Test
    void testSerialize() {
        final SecurityToken original = new SecurityToken();
        final SecurityToken copy = deserialize(serialize(original));
        assertThat(original).isEqualTo(copy);
    }

    @Test
    void testCreate() {
        final SecurityToken token = new SecurityToken();
        assertThat(token.length()).isEqualTo(28);
    }

    @Test
    void testValueOf() {
        assertThat(SecurityToken.valueOf("7SeU+ciLCVmchagrknCYMf7/GD4=").toString()).isEqualTo("7SeU+ciLCVmchagrknCYMf7/GD4=");
    }

    @Test
    void testValueIsValid() {
        assertThat(SecurityToken.isValid("7SeU+ciLCVmchagrknCYMf7/GD4=")).isTrue();
        assertThat(SecurityToken.isValid("7SeU+ciLCVmchagrknCYMf7/GD4")).isFalse();
    }

}
