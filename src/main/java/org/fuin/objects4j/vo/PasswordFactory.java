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

import javax.enterprise.context.ApplicationScoped;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.fuin.objects4j.common.ThreadSafe;

/**
 * Creates a {@link Password}.
 */
@ThreadSafe
@ApplicationScoped
public class PasswordFactory extends XmlAdapter<String, Password> implements
        SimpleValueObjectFactory<String, Password> {

    @Override
    public final Class<Password> getSimpleValueObjectClass() {
        return Password.class;
    }

    @Override
    public final boolean isValid(final String value) {
        return PasswordStrValidator.isValid(value);
    }

    @Override
    public final Password create(final String value) {
        return new Password(value);
    }

    @Override
    public final String marshal(final Password pw) throws Exception {
        return pw.asBaseType();
    }

    @Override
    public final Password unmarshal(final String value) throws Exception {
        return create(value);
    }

}
