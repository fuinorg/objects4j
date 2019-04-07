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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.units4j.AbstractPersistenceTest;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

// CHECKSTYLE:OFF
public class DayOpeningHoursTest extends AbstractPersistenceTest {

    public final void testEqualsHashCode() {
        EqualsVerifier.forClass(DayOpeningHours.class).verify();
    }

    @Test
    public final void testConstruct() {

        assertThat(new DayOpeningHours("Mon 13:00-14:00")).isEqualTo(new DayOpeningHours(DayOfTheWeek.valueOf("Mon"), new HourRanges("13:00-14:00")));

        try {
            new DayOpeningHours("Monday 13-14:00");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'dayOpeningHours' does not represent a valid hour range like 'Mon 09:00-12:00+13:00-17:00': 'Monday 13-14:00'");
        }

    }
    
    @Test
    public final void testIsValidTRUE() {

        assertThat(DayOpeningHours.isValid(null)).isTrue();
        assertThat(DayOpeningHours.isValid("Mon 00:00-24:00")).isTrue();
        assertThat(DayOpeningHours.isValid("Mon 09:00-12:00+13:00-17:00")).isTrue();

    }

    @Test
    public final void testIsValidFALSE() {

        assertThat(DayOpeningHours.isValid("")).isFalse();
        assertThat(DayOpeningHours.isValid("-")).isFalse();
        assertThat(DayOpeningHours.isValid("Monday 12:00-")).isFalse();
        assertThat(DayOpeningHours.isValid("Monday")).isFalse();
        assertThat(DayOpeningHours.isValid("Mon")).isFalse();
        assertThat(DayOpeningHours.isValid("Mon 12-13")).isFalse();
        assertThat(DayOpeningHours.isValid(" 12:00-13:00")).isFalse();
        assertThat(DayOpeningHours.isValid("Mon 12:00-13:00+18-19")).isFalse();
        assertThat(DayOpeningHours.isValid("Mon09:00-12:00")).isFalse();

    }
    
    @Test
    public final void testValueOf() {
        assertThat(DayOpeningHours.valueOf(null)).isNull();
        assertThat(DayOpeningHours.valueOf("Mon 00:00-24:00")).isEqualTo(new DayOpeningHours(DayOfTheWeek.valueOf("Mon"), new HourRanges("13:00-14:00")));
    }

    @Test
    public final void testRequireArgValid() {

        try {
            DayOpeningHours.requireArgValid("a", "");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'a' does not represent a valid hour range like 'Mon 09:00-12:00+13:00-17:00': ''");
        }

        try {
            DayOpeningHours.requireArgValid("b", "Monday 17-18");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'b' does not represent a valid hour range like 'Mon 09:00-12:00+13:00-17:00': 'Monday 17-18'");
        }

    }

    @Test
    public void testJPA() {

        // PREPARE
        beginTransaction();
        getEm().persist(new DayOpeningHoursParentEntity(1));
        commitTransaction();

        // TEST UPDATE
        beginTransaction();
        final DayOpeningHoursParentEntity entity = getEm().find(DayOpeningHoursParentEntity.class, 1L);
        entity.setDayOpeningHours(new DayOpeningHours("Mon 00:00-24:00"));
        commitTransaction();

        // VERIFY
        beginTransaction();
        final DayOpeningHoursParentEntity copy = getEm().find(DayOpeningHoursParentEntity.class, 1L);
        assertThat(copy).isNotNull();
        assertThat(copy.getId()).isEqualTo(1);
        assertThat(copy.getDayOpeningHours()).isNotNull();
        assertThat(copy.getDayOpeningHours().toString()).isEqualTo("MON 00:00-24:00");
        commitTransaction();

    }

}
// CHECKSTYLE:ON
