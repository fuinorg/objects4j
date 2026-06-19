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
package org.fuin.objects4j.openbao;

import org.fuin.objects4j.crypto.DecryptionFailedException;
import org.fuin.objects4j.crypto.DuplicateEncryptionKeyIdException;
import org.fuin.objects4j.crypto.EncryptedData;
import org.fuin.objects4j.crypto.EncryptionKeyIdUnknownException;
import org.fuin.objects4j.crypto.SimpleEncryptedData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Integration test for {@link BaoEncryptedDataService} that boots a real OpenBao server (Transit engine) in a Docker container.
 * The test is skipped automatically when no Docker environment is available.
 */
@Testcontainers(disabledWithoutDocker = true)
class BaoEncryptedDataServiceTest {

    private static final String ROOT_TOKEN = "root";

    private static final DockerImageName IMAGE = DockerImageName.parse("openbao/openbao:2.5.5");

    @Container
    private static final GenericContainer<?> OPENBAO = new GenericContainer<>(IMAGE)
            .withExposedPorts(8200)
            .withEnv("BAO_DEV_ROOT_TOKEN_ID", ROOT_TOKEN)
            .withEnv("BAO_DEV_LISTEN_ADDRESS", "0.0.0.0:8200")
            .withCommand("server", "-dev")
            .waitingFor(Wait.forHttp("/v1/sys/health").forPort(8200).forStatusCode(200));

    private static BaoEncryptedDataService service;

    @BeforeAll
    static void setup() {
        final String baseUrl = "http://" + OPENBAO.getHost() + ":" + OPENBAO.getMappedPort(8200);
        service = new BaoEncryptedDataService(baseUrl, ROOT_TOKEN);
        service.createTransitEngine();
    }

    @Test
    void testCreateAndKeyExists() throws Exception {
        service.createKey("k-exist");
        assertThat(service.keyExists("k-exist")).isTrue();
        assertThat(service.keyExists("k-does-not-exist")).isFalse();
    }

    @Test
    void testCreateDuplicateKey() throws Exception {
        service.createKey("k-dup");
        assertThatThrownBy(() -> service.createKey("k-dup"))
                .isInstanceOf(DuplicateEncryptionKeyIdException.class);
    }

    @Test
    void testEncryptDecryptRoundTrip() throws Exception {
        service.createKey("k-roundtrip");
        final byte[] clear = "Some secret payload".getBytes(StandardCharsets.UTF_8);

        final EncryptedData encrypted = service.encrypt("k-roundtrip", "Greeting", "text/plain", clear);

        assertThat(encrypted.getKeyId()).isEqualTo("k-roundtrip");
        assertThat(encrypted.getKeyVersion()).isEqualTo("1");
        assertThat(encrypted.getDataType()).isEqualTo("Greeting");
        assertThat(encrypted.getContentType()).isEqualTo("text/plain");
        assertThat(new String(encrypted.getEncryptedData(), StandardCharsets.UTF_8)).startsWith("vault:v1:");

        assertThat(service.decrypt(encrypted)).isEqualTo(clear);
    }

    @Test
    void testKeyRotation() throws Exception {
        service.createKey("k-rotate");
        final byte[] clear = "rotate me".getBytes(StandardCharsets.UTF_8);

        final EncryptedData v1 = service.encrypt("k-rotate", "Data", "text/plain", clear);
        assertThat(service.getKeyVersion("k-rotate")).isEqualTo("1");

        assertThat(service.rotateKey("k-rotate")).isEqualTo("2");
        assertThat(service.getKeyVersion("k-rotate")).isEqualTo("2");

        final EncryptedData v2 = service.encrypt("k-rotate", "Data", "text/plain", clear);
        assertThat(v2.getKeyVersion()).isEqualTo("2");

        // Both the old (v1) and the new (v2) ciphertext must still decrypt correctly
        assertThat(service.decrypt(v1)).isEqualTo(clear);
        assertThat(service.decrypt(v2)).isEqualTo(clear);
    }

    @Test
    void testEncryptUnknownKey() {
        assertThatThrownBy(() -> service.encrypt("k-missing", "Data", "text/plain", new byte[]{1, 2, 3}))
                .isInstanceOf(EncryptionKeyIdUnknownException.class);
    }

    @Test
    void testGetKeyVersionUnknownKey() {
        assertThatThrownBy(() -> service.getKeyVersion("k-missing"))
                .isInstanceOf(EncryptionKeyIdUnknownException.class);
    }

    @Test
    void testRotateUnknownKey() {
        assertThatThrownBy(() -> service.rotateKey("k-missing"))
                .isInstanceOf(EncryptionKeyIdUnknownException.class);
    }

    @Test
    void testDecryptTamperedCiphertext() throws Exception {
        service.createKey("k-tamper");
        final EncryptedData tampered = new SimpleEncryptedData("k-tamper", "1", "Data", "text/plain",
                "vault:v1:bm90LWEtdmFsaWQtY2lwaGVydGV4dA==".getBytes(StandardCharsets.UTF_8));
        assertThatThrownBy(() -> service.decrypt(tampered))
                .isInstanceOf(DecryptionFailedException.class);
    }

}
