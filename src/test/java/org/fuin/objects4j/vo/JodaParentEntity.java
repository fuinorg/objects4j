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

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.fuin.objects4j.common.DateTimeAdapter;
import org.fuin.objects4j.common.LocalDateAdapter;
import org.fuin.objects4j.common.LocalDateTimeAdapter;
import org.fuin.objects4j.common.LocalTimeAdapter;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

// CHECKSTYLE:OFF
@Entity(name = "JODA_PARENT")
public class JodaParentEntity {

	@Id
	@Column(name = "ID")
	private long id;

	@Column(name = "LOCAL_DATE", nullable = true)
	@Convert(converter = LocalDateAdapter.class)
	private LocalDate localDate;

	@Column(name = "LOCAL_DATE_TIME", nullable = true)
        @Convert(converter = LocalDateTimeAdapter.class)
	private LocalDateTime localDateTime;

	@Column(name = "LOCAL_TIME", nullable = true)
        @Convert(converter = LocalTimeAdapter.class)
	private LocalTime localTime;

	@Column(name = "DATE_TIME", nullable = true)
        @Convert(converter = DateTimeAdapter.class)
	private DateTime dateTime;

	public JodaParentEntity() {
		super();
	}

	public JodaParentEntity(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getLocalDate() {
		return localDate;
	}

	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	public LocalTime getLocalTime() {
		return localTime;
	}

	public void setLocalTime(LocalTime localTime) {
		this.localTime = localTime;
	}

	public DateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}

}
// CHECKSTYLE:ON
