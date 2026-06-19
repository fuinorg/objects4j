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

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.validation.constraints.NotEmpty;
import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.crypto.DecryptionFailedException;
import org.fuin.objects4j.crypto.DuplicateEncryptionKeyIdException;
import org.fuin.objects4j.crypto.EncryptedData;
import org.fuin.objects4j.crypto.EncryptedDataService;
import org.fuin.objects4j.crypto.EncryptionKeyIdUnknownException;
import org.fuin.objects4j.crypto.EncryptionKeyVersionUnknownException;
import org.fuin.objects4j.crypto.SimpleEncryptedData;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/**
 * Implementation of {@link EncryptedDataService} that uses the
 * <a href="https://github.com/openbao/openbao">OpenBao</a> (or Vault compatible) <b>Transit</b> secrets engine.
 * The Transit engine provides "encryption as a service": key material never leaves the server,
 * keys are versioned and can be rotated, and the ciphertext (format {@code vault:vN:base64})
 * embeds the key version used.
 * <p>
 * The caller provides the server base URL and an authentication token.
 * The Transit engine has to be mounted once via {@link #createTransitEngine()}
 * before keys can be created.
 */
public final class BaoEncryptedDataService implements EncryptedDataService {

    /** Default mount path of the Transit secrets engine. */
    public static final String DEFAULT_MOUNT_PATH = "transit";

    private final HttpClient httpClient;

    private final String baseUrl;

    private final String token;

    private final String mountPath;

    /**
     * Constructor using the {@link #DEFAULT_MOUNT_PATH}.
     *
     * @param baseUrl Base URL of the OpenBao server like "http://localhost:8200".
     * @param token   Authentication token sent as "X-Vault-Token" header.
     */
    public BaoEncryptedDataService(@NotEmpty final String baseUrl,
                                   @NotEmpty final String token) {
        this(baseUrl, token, DEFAULT_MOUNT_PATH);
    }

    /**
     * Constructor with all data.
     *
     * @param baseUrl   Base URL of the OpenBao server like "http://localhost:8200".
     * @param token     Authentication token sent as "X-Vault-Token" header.
     * @param mountPath Mount path of the Transit secrets engine.
     */
    public BaoEncryptedDataService(@NotEmpty final String baseUrl,
                                   @NotEmpty final String token,
                                   @NotEmpty final String mountPath) {
        super();
        Contract.requireArgNotEmpty("baseUrl", baseUrl);
        Contract.requireArgNotEmpty("token", token);
        Contract.requireArgNotEmpty("mountPath", mountPath);
        this.httpClient = HttpClient.newHttpClient();
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        this.token = token;
        this.mountPath = mountPath;
    }

    /**
     * Mounts the Transit secrets engine at the configured mount path.
     * The operation is idempotent: if the engine is already mounted
     * the call is silently ignored.
     */
    public void createTransitEngine() {
        final JsonObject body = Json.createObjectBuilder().add("type", "transit").build();
        final Response resp = send("POST", "sys/mounts/" + mountPath, body);
        // 204/200 = created; 400 = "path is already in use" (already mounted)
        if (resp.status() != 204 && resp.status() != 200 && resp.status() != 400) {
            throw new IllegalStateException("Failed to enable transit engine: " + resp);
        }
    }

    @Override
    public boolean keyExists(@NotEmpty final String keyId) {
        final Response resp = send("GET", keyPath(keyId), null);
        if (resp.status() == 200) {
            return true;
        }
        if (resp.status() == 404) {
            return false;
        }
        throw unexpected(resp);
    }

    @Override
    public void createKey(@NotEmpty final String keyId) throws DuplicateEncryptionKeyIdException {
        if (keyExists(keyId)) {
            throw new DuplicateEncryptionKeyIdException(keyId);
        }
        final Response resp = send("POST", keyPath(keyId), Json.createObjectBuilder().build());
        if (resp.status() != 200 && resp.status() != 204) {
            throw unexpected(resp);
        }
    }

    @Override
    public String rotateKey(@NotEmpty final String keyId) throws EncryptionKeyIdUnknownException {
        if (!keyExists(keyId)) {
            throw new EncryptionKeyIdUnknownException(keyId);
        }
        final Response resp = send("POST", keyPath(keyId) + "/rotate", Json.createObjectBuilder().build());
        if (resp.status() != 200 && resp.status() != 204) {
            throw unexpected(resp);
        }
        return getKeyVersion(keyId);
    }

    @Override
    public String getKeyVersion(@NotEmpty final String keyId) throws EncryptionKeyIdUnknownException {
        final Response resp = send("GET", keyPath(keyId), null);
        if (resp.status() == 404) {
            throw new EncryptionKeyIdUnknownException(keyId);
        }
        if (resp.status() != 200) {
            throw unexpected(resp);
        }
        return String.valueOf(data(resp).getInt("latest_version"));
    }

    @Override
    public EncryptedData encrypt(@NotEmpty final String keyId,
                                 @NotEmpty final String dataType,
                                 @NotEmpty final String contentType,
                                 @NotEmpty final byte[] data)
            throws EncryptionKeyIdUnknownException {
        if (!keyExists(keyId)) {
            throw new EncryptionKeyIdUnknownException(keyId);
        }
        final JsonObject body = Json.createObjectBuilder()
                .add("plaintext", Base64.getEncoder().encodeToString(data))
                .build();
        final Response resp = send("POST", mountPath + "/encrypt/" + keyId, body);
        if (resp.status() != 200) {
            throw unexpected(resp);
        }
        final JsonObject responseData = data(resp);
        final String ciphertext = responseData.getString("ciphertext");
        final int keyVersion = responseData.getInt("key_version", 1);
        return new SimpleEncryptedData(keyId, String.valueOf(keyVersion), dataType, contentType,
                ciphertext.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public byte[] decrypt(final EncryptedData encryptedData)
            throws EncryptionKeyIdUnknownException, EncryptionKeyVersionUnknownException, DecryptionFailedException {
        Contract.requireArgNotNull("encryptedData", encryptedData);
        final String keyId = encryptedData.getKeyId();
        final String ciphertext = new String(encryptedData.getEncryptedData(), StandardCharsets.UTF_8);
        final JsonObject body = Json.createObjectBuilder().add("ciphertext", ciphertext).build();
        final Response resp = send("POST", mountPath + "/decrypt/" + keyId, body);
        if (resp.status() == 404) {
            throw new EncryptionKeyIdUnknownException(keyId);
        }
        if (resp.status() != 200) {
            throw new DecryptionFailedException("Failed to decrypt data for key '" + keyId + "': " + resp);
        }
        return Base64.getDecoder().decode(data(resp).getString("plaintext"));
    }

    private String keyPath(final String keyId) {
        return mountPath + "/keys/" + keyId;
    }

    private Response send(final String method, final String path, @Nullable final JsonObject body) {
        try {
            final HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/v1/" + path))
                    .header("X-Vault-Token", token);
            if (body == null) {
                builder.method(method, HttpRequest.BodyPublishers.noBody());
            } else {
                builder.header("Content-Type", "application/json")
                        .method(method, HttpRequest.BodyPublishers.ofString(body.toString(), StandardCharsets.UTF_8));
            }
            final HttpResponse<String> response = httpClient.send(builder.build(),
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            return new Response(response.statusCode(), response.body());
        } catch (final IOException ex) {
            throw new IllegalStateException("HTTP call failed: " + method + " " + path, ex);
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("HTTP call interrupted: " + method + " " + path, ex);
        }
    }

    private static JsonObject data(final Response resp) {
        try (JsonReader reader = Json.createReader(new StringReader(resp.body()))) {
            return Objects.requireNonNull(reader.readObject().getJsonObject("data"), "data");
        }
    }

    private static IllegalStateException unexpected(final Response resp) {
        return new IllegalStateException("Unexpected OpenBao response: " + resp);
    }

    /**
     * Minimal HTTP response holder.
     *
     * @param status HTTP status code.
     * @param body   Response body.
     */
    private record Response(int status, String body) {
        @Override
        public String toString() {
            return "HTTP " + status + " " + body;
        }
    }

}
