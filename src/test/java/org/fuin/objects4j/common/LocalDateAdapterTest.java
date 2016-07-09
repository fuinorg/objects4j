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

import static org.assertj.core.api.Assertions.assertThat;

import org.fuin.objects4j.vo.JodaParentEntity;
import org.fuin.units4j.AbstractPersistenceTest;
import org.joda.time.LocalDate;
import org.junit.Test;

//TESTCODE:BEGIN
public final class LocalDateAdapterTest extends AbstractPersistenceTest {

	@Test
	public final void testMarshalUnmarshal() {

		// PREPARE
		final LocalDateAdapter testee = new LocalDateAdapter();
		final LocalDate original = new LocalDate();

		// TEST
		final String str = testee.marshal(original);
		final LocalDate copy = testee.unmarshal(str);

		// VERIFY
		assertThat(copy).isEqualTo(original);

	}

	@Test
	public final void testConvert() {

		// PREPARE
		final LocalDateAdapter testee = new LocalDateAdapter();
		final LocalDate original = new LocalDate();

		// TEST
		final String str = testee.convertToDatabaseColumn(original);
		final LocalDate copy = testee.convertToEntityAttribute(str);

		// VERIFY
		assertThat(copy).isEqualTo(original);

	}

	@Test
	public void testJPA() {

		// PREPARE
		final LocalDate localDate = new LocalDate();

		beginTransaction();
		getEm().persist(new JodaParentEntity(1));
		commitTransaction();

		// TEST UPDATE
		beginTransaction();
		final JodaParentEntity entity = getEm()
				.find(JodaParentEntity.class, 1L);
		entity.setLocalDate(localDate);
		commitTransaction();

		// VERIFY
		beginTransaction();
		final JodaParentEntity copy = getEm().find(JodaParentEntity.class, 1L);
		assertThat(copy).isNotNull();
		assertThat(copy.getId()).isEqualTo(1);
		assertThat(copy.getLocalDate()).isEqualTo(localDate);
		commitTransaction();

	}

}
// TESTCODE:END
