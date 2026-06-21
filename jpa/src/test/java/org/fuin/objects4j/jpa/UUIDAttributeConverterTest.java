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
package org.fuin.objects4j.jpa;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public final class UUIDAttributeConverterTest {

    @Test
    public void testNull() {
        final UUIDAttributeConverter testee = new UUIDAttributeConverter();
        assertThat(testee.convertToDatabaseColumn(null)).isNull();
        // The converter intentionally does not guard 'convertToEntityAttribute' against null
        // (it delegates straight to UUID.fromString), so a null database value fails fast.
        assertThatThrownBy(() -> testee.convertToEntityAttribute(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void testRoundTrip() {
        final UUIDAttributeConverter testee = new UUIDAttributeConverter();
        final UUID value = UUID.fromString("f628dbf4-a3f4-4f4a-9c1e-2f8c2b3d4e5f");
        assertThat(testee.convertToEntityAttribute(testee.convertToDatabaseColumn(value))).isEqualTo(value);
    }

}
