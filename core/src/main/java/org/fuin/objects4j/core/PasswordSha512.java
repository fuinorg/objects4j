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
package org.fuin.objects4j.core;

import jakarta.validation.constraints.NotNull;
import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.HasPublicStaticIsValidMethod;
import org.fuin.objects4j.common.HasPublicStaticValueOfMethod;
import org.fuin.objects4j.common.Immutable;
import org.fuin.objects4j.common.Nullable;

import java.io.Serial;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 * SHA-512 hashed password that is HEX encoded.
 */
@Immutable
@HasPublicStaticIsValidMethod
@HasPublicStaticValueOfMethod
public final class PasswordSha512 extends AbstractStringValueObject {

    @Serial
    private static final long serialVersionUID = -6285061339408965704L;

    private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    private static final Pattern PATTERN = Pattern.compile("[0-9a-fA-F]*", java.util.regex.Pattern.CASE_INSENSITIVE);

    @NotNull
    @PasswordSha512Str
    private final String hash;

    /**
     * Constructor with HEX encoded hash string.
     *
     * @param hexEncodedHash
     *            Hash code as HEX encoded string.
     */
    public PasswordSha512(@NotNull @PasswordSha512Str final String hexEncodedHash) {
        super();
        this.hash = hexEncodedHash;
        Contract.requireArgNotEmpty("hexEncodedHash", hexEncodedHash);
        if (!isValid(hexEncodedHash)) {
            throw new ConstraintViolationException("The argument 'hexEncodedHash' is not valid");
        }
    }

    /**
     * Constructor with password.
     *
     * @param password
     *            Clear text password.
     */
    public PasswordSha512(@NotNull final Password password) {// NOSONAR False "hash ... not initialized in this
                                                             // constructor"
        super();
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(password.toString().getBytes());
            this.hash = toHex(md.digest());
        } catch (final NoSuchAlgorithmException ex) {
            throw new RuntimeException("Failed to create instance", ex);
        }
    }

    private static String toHex(final byte[] hash) {
        final int l = hash.length;
        final char[] out = new char[l << 1];
        int j = 0;
        for (int i = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & hash[i]) >>> 4];
            out[j++] = DIGITS[0x0F & hash[i]];
        }
        return String.copyValueOf(out);
    }

    @Override
    public final String asBaseType() {
        return hash;
    }

    @Override
    public final String toString() {
        return asBaseType();
    }

    /**
     * Converts a given string into an instance of this class.
     *
     * @param str
     *            String to convert.
     *
     * @return New instance.
     */
    @Nullable
    public static PasswordSha512 valueOf(@Nullable final String str) {
        if (str == null) {
            return null;
        }
        return new PasswordSha512(str);
    }

    /**
     * Check that a given string is an HEX encoded SHA512 password.
     *
     * @param value
     *            Value to check.
     *
     * @return Returns {@literal true} if it's an allowed value else {@literal false} is returned.
     */
    public static boolean isValid(final String value) {
        if (value == null) {
            return true;
        }
        if (value.length() != 128) {
            return false;
        }
        return PATTERN.matcher(value).matches();
    }

    /**
     * Checks if the argument is valid and throws an exception if this is not the case.
     *
     * @param name
     *            Name of the value for a possible error message.
     * @param value
     *            Value to check.
     *
     * @throws ConstraintViolationException
     *             The value was not valid.
     */
    public static void requireArgValid(@NotNull final String name, @NotNull final String value) throws ConstraintViolationException {
        if (!isValid(value)) {
            throw new ConstraintViolationException("The argument '" + name + "' is not valid: '" + value + "'");
        }
    }

}
