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

import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.Immutable;
import org.fuin.objects4j.ui.Label;
import org.fuin.objects4j.ui.ShortLabel;
import org.fuin.objects4j.ui.Tooltip;

/**
 * A password with a length between 8 and 20 characters.
 */
@Immutable
@ShortLabel("PW")
@Label("Password")
@Tooltip("Secret password")
@XmlJavaTypeAdapter(PasswordConverter.class)
public final class Password extends AbstractStringValueObject {

    private static final long serialVersionUID = -7745110729063955842L;

    @NotNull
    @PasswordStr
    private String str;

    /**
     * Protected default constructor for deserialization.
     */
    protected Password() {// NOSONAR Ignore JAXB default constructor
        super();
    }

    /**
     * Constructor with password.
     * 
     * @param password
     *            Password.
     */
    public Password(@NotNull @PasswordStr final String password) {
        super();
        Contract.requireArgNotEmpty("password", password);
        PasswordStrValidator.requireArgValid("password", password);
        this.str = password.trim();
    }

    @Override
    public final String asBaseType() {
        return str;
    }

    @Override
    public final String toString() {
        return asBaseType();
    }

}
