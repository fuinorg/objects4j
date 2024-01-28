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

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.HasPublicStaticIsValidMethod;
import org.fuin.objects4j.common.HasPublicStaticValueOfMethod;
import org.fuin.objects4j.common.Immutable;
import org.fuin.objects4j.ui.Label;
import org.fuin.objects4j.ui.ShortLabel;

import java.io.Serial;
import java.util.regex.Pattern;

/**
 * User name with the following rules.
 * <ul>
 * <li>3-20 characters in length</li>
 * <li>Lower case letters (a-z)</li>
 * <li>Numbers (0-9)</li>
 * <li>Hyphens (-)</li>
 * <li>Underscores (_)</li>
 * <li>Starts not with an underscore, hyphen or number</li>
 * </ul>
 */
@Immutable
@ShortLabel("User")
@Label("Username")
@HasPublicStaticIsValidMethod
@HasPublicStaticValueOfMethod
public final class UserName extends AbstractStringValueObject {

    @Serial
    private static final long serialVersionUID = 9055520843135472634L;

    private static final Pattern PATTERN = Pattern.compile("[a-z][0-9a-z_\\-]*");

    @NotNull
    @UserNameStr
    private String str;

    /**
     * Protected default constructor for deserialization.
     */
    protected UserName() {// NOSONAR Ignore JAXB default constructor
        super();
    }

    /**
     * Constructor with user name.
     * 
     * @param userName
     *            User name.
     */
    public UserName(@NotNull @UserNameStr final String userName) {
        super();
        Contract.requireArgNotNull("userName", userName);
        parseArg("userName", userName);
        this.str = userName.trim().toLowerCase();
    }

    @Override
    public final String asBaseType() {
        return str;
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
    public static UserName valueOf(@Nullable final String str) {
        if (str == null) {
            return null;
        }
        return new UserName(str);
    }

    /**
     * Check that a given string is a well-formed user id.
     *
     * @param value
     *            Value to check.
     *
     * @return Returns {@literal true} if it's a valid user id else {@literal false} is returned.
     */
    public static boolean isValid(final String value) {
        if (value == null) {
            return true;
        }
        if ((value.length() < 3) || (value.length() > 20)) {
            return false;
        }
        return PATTERN.matcher(value).matches();
    }

    /**
     * Parses the argument and throws an exception if it's not valid.
     *
     * @param name
     *            Name of the value for a possible error message.
     * @param value
     *            Value to check.
     *
     * @throws ConstraintViolationException
     *             The value was not valid.
     */
    public static void parseArg(@NotNull final String name, @NotNull final String value) throws ConstraintViolationException {
        final String trimmed = value.trim().toLowerCase();
        if (!isValid(trimmed)) {
            throw new ConstraintViolationException("The argument '" + name + "' is not valid: '" + trimmed + "'");
        }
    }

}
