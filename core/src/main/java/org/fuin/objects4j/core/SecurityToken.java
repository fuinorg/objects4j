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
package org.fuin.objects4j.core;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.fuin.objects4j.common.HasPublicStaticIsValidMethod;
import org.fuin.objects4j.common.HasPublicStaticValueOfMethod;
import org.fuin.objects4j.common.Immutable;

import java.io.Serial;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.regex.Pattern;

/**
 * Creates a BASE64 encoded string based on a SHA1PRNG {@link SecureRandom}.
 */
@Immutable
@HasPublicStaticIsValidMethod
@HasPublicStaticValueOfMethod
public final class SecurityToken extends AbstractStringValueObject {

    @Serial
    private static final long serialVersionUID = 8737032520847641569L;

    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static final Pattern PATTERN = Pattern.compile("^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$");

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

    private SecurityToken(final String token) {
        super();
        this.token = token;
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
     * Creates a encoded secure random string.
     *
     * @return Base64 encoded string.
     */
    private static String createSecureRandom() {
        try {
            final String no = "" + SECURE_RANDOM.nextInt();
            final MessageDigest md = MessageDigest.getInstance("SHA-1");
            final byte[] digest = md.digest(no.getBytes());
            return Base64.getEncoder().encodeToString(digest);
        } catch (final NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Converts a given string into an instance of this class.
     *
     * @param str String to convert.
     * @return New instance.
     */
    @Nullable
    public static SecurityToken valueOf(@Nullable final String str) {
        if (str == null) {
            return null;
        }
        return new SecurityToken(str);
    }

    /**
     * Check that a given string is an HEX encoded SHA512 password.
     *
     * @param value Value to check.
     * @return Returns {@literal true} if it's an allowed value else {@literal false} is returned.
     */
    public static boolean isValid(final String value) {
        if (value == null) {
            return true;
        }
        if (value.length() != 28) {
            return false;
        }
        return PATTERN.matcher(value).matches();
    }

}
