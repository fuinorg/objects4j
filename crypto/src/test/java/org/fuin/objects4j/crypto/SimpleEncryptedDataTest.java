/**
 * Copyright (C) 2015 Michael Schnell. All rights reserved.
 * http://www.fuin.org/
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
 * along with this library. If not, see http://www.gnu.org/licenses/.
 */
package org.fuin.objects4j.crypto;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SimpleEncryptedData}.
 */
public class SimpleEncryptedDataTest {

    @Test
    public void testGetters() {
        final byte[] data = "cipher".getBytes(StandardCharsets.UTF_8);
        final SimpleEncryptedData sut = new SimpleEncryptedData("key-1", "2", "MyData", "application/json", data);
        assertThat(sut.getKeyId()).isEqualTo("key-1");
        assertThat(sut.getKeyVersion()).isEqualTo("2");
        assertThat(sut.getDataType()).isEqualTo("MyData");
        assertThat(sut.getContentType()).isEqualTo("application/json");
        assertThat(sut.getEncryptedData()).isEqualTo(data);
    }

    @Test
    public void testEqualsHashCode() {
        final SimpleEncryptedData a = new SimpleEncryptedData("key-1", "1", "MyData", "application/json", new byte[]{1, 2, 3});
        final SimpleEncryptedData b = new SimpleEncryptedData("key-1", "1", "MyData", "application/json", new byte[]{1, 2, 3});
        final SimpleEncryptedData c = new SimpleEncryptedData("key-1", "1", "MyData", "application/json", new byte[]{9, 9});
        assertThat(a).isEqualTo(b).hasSameHashCodeAs(b);
        assertThat(a).isNotEqualTo(c);
    }

    @Test
    public void testImmutableArray() {
        final byte[] data = {1, 2, 3};
        final SimpleEncryptedData sut = new SimpleEncryptedData("key-1", "1", "MyData", "application/json", data);
        data[0] = 42;
        assertThat(sut.getEncryptedData()).containsExactly(1, 2, 3);
    }

}
