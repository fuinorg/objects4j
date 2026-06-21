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
import org.fuin.objects4j.common.ExceptionShortIdentifable;
import org.fuin.objects4j.common.NotThreadSafe;

import java.io.Serial;

import static org.fuin.objects4j.crypto.Objects4JCryptoUtils.SHORT_ID_PREFIX;

/**
 * Signals that the requested version of the encryption key is unknown.
 */
@NotThreadSafe
public final class EncryptionKeyVersionUnknownException extends Exception implements ExceptionShortIdentifable {

    /**
     * Unique name of the element to use for XML and JSON marshalling/unmarshalling.
     */
    public static final String ELEMENT_NAME = "encryption-key-version-unknown-exception";

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Unique short identifier of this exception.
     */
    public static final String SHORT_ID = SHORT_ID_PREFIX + "-ENCRYPTION_KEY_VERSION_UNKNOWN";

    private final String keyVersion;

    /**
     * Constructor with all data.
     *
     * @param keyVersion The key version that caused the problem.
     */
    public EncryptionKeyVersionUnknownException(@NotEmpty final String keyVersion) {
        super("Unknown keyVersion: " + keyVersion);
        Contract.requireArgNotEmpty("keyVersion", keyVersion);
        this.keyVersion = keyVersion;
    }

    @Override
    public final String getShortId() {
        return SHORT_ID;
    }

    /**
     * Returns the key version that caused the problem.
     *
     * @return Key version.
     */
    @NotEmpty
    public final String getKeyVersion() {
        return keyVersion;
    }

}
