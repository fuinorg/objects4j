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
@XmlJavaTypeAdapter(UserNameConverter.class)
public final class UserName extends AbstractStringValueObject {

    private static final long serialVersionUID = 9055520843135472634L;

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
        UserNameStrValidator.parseArg("userName", userName);
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

}
