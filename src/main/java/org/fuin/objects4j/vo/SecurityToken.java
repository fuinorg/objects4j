/**
 * Copyright (C) 2015 Michael Schnell. All rights reserved. 
 * http://www.fuin.org/
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
 * along with this library. If not, see http://www.gnu.org/licenses/.
 */
package org.fuin.objects4j.vo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.NotNull;

/**
 * Creates a BASE64 encoded string based on a SHA1PRNG {@link SecureRandom}.
 */
@Immutable
public final class SecurityToken extends AbstractStringValueObject {

    private static final long serialVersionUID = 8737032520847641569L;

    private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    private static final SecureRandom SECURE_RANDOM;

    static {
        try {
            SECURE_RANDOM = SecureRandom.getInstance("SHA1PRNG");
            SECURE_RANDOM.setSeed(System.currentTimeMillis());
        } catch (final NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    @NotNull
    private String token;

    /**
     * Constructor that creates a new random token.
     */
    public SecurityToken() {
        super();
        this.token = createSecureRandom();
    }

    @Override
    public final String asBaseType() {
        return token;
    }

    @Override
    public final String toString() {
        return asBaseType();
    }

    /**
     * Encodes a byte array base64.
     * 
     * @param buffer
     *            Bytes to encode.
     * 
     * @return Base64 encoded string.
     */
    private static String encodeBase64(final byte[] buffer) {
        final int l = buffer.length;
        final char[] out = new char[l << 1];
        int j = 0;
        for (int i = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & buffer[i]) >>> 4];
            out[j++] = DIGITS[0x0F & buffer[i]];
        }
        return String.copyValueOf(out);
    }

    /**
     * Creates a encoded secure random string.
     * 
     * @return Base64 encoded string.
     */
    private static String createSecureRandom() {
        try {
            final String no = "" + SECURE_RANDOM.nextInt();
            final MessageDigest md = MessageDigest.getInstance("SHA-1");
            final byte[] digest = md.digest(no.getBytes());
            return encodeBase64(digest);
        } catch (final NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

}
