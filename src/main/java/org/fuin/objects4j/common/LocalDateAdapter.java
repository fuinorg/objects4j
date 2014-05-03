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
package org.fuin.objects4j.common;

import javax.persistence.AttributeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.LocalDate;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Joda local date JAXB adapter and JPA converter.
 */
public final class LocalDateAdapter extends XmlAdapter<String, LocalDate>
		implements AttributeConverter<LocalDate, String> {

	@Override
	public final LocalDate unmarshal(final String str) {
		if (str == null) {
			return null;
		}
		return LocalDate.parse(str, ISODateTimeFormat.localDateParser());
	}

	@Override
	public final String marshal(final LocalDate value) {
		if (value == null) {
			return null;
		}
		return ISODateTimeFormat.date().print(value);
	}

	@Override
	public final String convertToDatabaseColumn(final LocalDate value) {
		if (value == null) {
			return null;
		}
		return ISODateTimeFormat.date().print(value);
	}

	@Override
	public final LocalDate convertToEntityAttribute(final String str) {
		if (str == null) {
			return null;
		}
		return LocalDate.parse(str, ISODateTimeFormat.localDateParser());
	}

}
