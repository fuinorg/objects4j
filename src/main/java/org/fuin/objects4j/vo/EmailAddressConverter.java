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
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.fuin.objects4j.common.ThreadSafe;

/**
 * Creates a {@link EmailAddress}.
 */
@ThreadSafe
@ApplicationScoped
@Converter(autoApply = true)
public final class EmailAddressConverter extends XmlAdapter<String, EmailAddress> implements
        AttributeConverter<EmailAddress, String>, ValueObjectConverter<String, EmailAddress> {

    @Override
    public Class<String> getBaseTypeClass() {
        return String.class;
    }

    @Override
    public final Class<EmailAddress> getValueObjectClass() {
        return EmailAddress.class;
    }

    @Override
    public final boolean isValid(final String value) {
        return EmailAddressStrValidator.isValid(value);
    }

    @Override
    public final EmailAddress toVO(final String value) {
        if (value == null) {
            return null;
        }
        return new EmailAddress(value);
    }

    @Override
    public final String fromVO(final EmailAddress value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    @Override
    public final String marshal(final EmailAddress value) throws Exception {
        return fromVO(value);
    }

    @Override
    public final EmailAddress unmarshal(final String value) throws Exception {
        return toVO(value);
    }

    @Override
    public final String convertToDatabaseColumn(final EmailAddress value) {
        return fromVO(value);
    }

    @Override
    public final EmailAddress convertToEntityAttribute(final String value) {
        return toVO(value);
    }

}
