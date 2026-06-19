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

import jakarta.validation.constraints.NotEmpty;
import org.fuin.objects4j.common.Contract;
import org.jspecify.annotations.Nullable;

import java.io.Serial;
import java.util.Arrays;
import java.util.Objects;

/**
 * Default immutable implementation of {@link EncryptedData}. Equals and hash code are based on all data.
 */
public final class SimpleEncryptedData implements EncryptedData {

    @Serial
    private static final long serialVersionUID = 1000L;

    private final String keyId;

    private final String keyVersion;

    private final String dataType;

    private final String contentType;

    private final byte[] encryptedData;

    /**
     * Constructor with all mandatory data.
     *
     * @param keyId         Unique identifier of the private key used.
     * @param keyVersion    Version of the private key used.
     * @param dataType      Unique type of the data like "UserPersonalData" or even a fully qualified class name.
     * @param contentType   Content/Mime type like "application/json; encoding=UTF-8; version=1".
     * @param encryptedData Encrypted data.
     */
    public SimpleEncryptedData(@NotEmpty final String keyId,
                               @NotEmpty final String keyVersion,
                               @NotEmpty final String dataType,
                               @NotEmpty final String contentType,
                               @NotEmpty final byte[] encryptedData) {
        super();
        Contract.requireArgNotEmpty("keyId", keyId);
        Contract.requireArgNotEmpty("keyVersion", keyVersion);
        Contract.requireArgNotEmpty("dataType", dataType);
        Contract.requireArgNotEmpty("contentType", contentType);
        Contract.requireArgNotNull("encryptedData", encryptedData);
        this.keyId = keyId;
        this.keyVersion = keyVersion;
        this.dataType = dataType;
        this.contentType = contentType;
        this.encryptedData = encryptedData.clone();
    }

    @Override
    public String getKeyId() {
        return keyId;
    }

    @Override
    public String getKeyVersion() {
        return keyVersion;
    }

    @Override
    public String getDataType() {
        return dataType;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public byte[] getEncryptedData() {
        return encryptedData.clone();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + contentType.hashCode();
        result = prime * result + dataType.hashCode();
        result = prime * result + Arrays.hashCode(encryptedData);
        result = prime * result + keyId.hashCode();
        result = prime * result + keyVersion.hashCode();
        return result;
    }

    @Override
    public boolean equals(@Nullable final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SimpleEncryptedData other = (SimpleEncryptedData) obj;
        return Objects.equals(contentType, other.contentType)
                && Objects.equals(dataType, other.dataType)
                && Arrays.equals(encryptedData, other.encryptedData)
                && Objects.equals(keyId, other.keyId)
                && Objects.equals(keyVersion, other.keyVersion);
    }

    @Override
    public String toString() {
        return "SimpleEncryptedData [keyId=" + keyId
                + ", keyVersion=" + keyVersion
                + ", dataType=" + dataType
                + ", contentType=" + contentType + "]";
    }

}
