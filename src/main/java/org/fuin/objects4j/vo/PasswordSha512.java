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
package org.fuin.objects4j.vo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.ContractViolationException;
import org.fuin.objects4j.common.Immutable;

/**
 * SHA-512 hashed password that is HEX encoded.
 */
@Immutable
@XmlJavaTypeAdapter(PasswordSha512Converter.class)
public final class PasswordSha512 extends AbstractStringValueObject<PasswordSha512> {

    private static final long serialVersionUID = -6285061339408965704L;

    private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
            'b', 'c', 'd', 'e', 'f' };

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
        if (!PasswordSha512StrValidator.isValid(hexEncodedHash)) {
            throw new ContractViolationException("The argument 'hexEncodedHash' is not valid");
        }
    }

    /**
     * Constructor with password.
     * 
     * @param password
     *            Clear text password.
     */
    public PasswordSha512(@NotNull final Password password) {
        super();
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(password.toString().getBytes());
            this.hash = toHex(md.digest());
        } catch (final NoSuchAlgorithmException ex) {
            throw new RuntimeException("", ex);
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
    public final String toString() {
        return hash;
    }

}
