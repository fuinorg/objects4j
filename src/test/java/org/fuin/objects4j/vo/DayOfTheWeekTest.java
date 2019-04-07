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
public class DayOfTheWeekTest extends AbstractPersistenceTest {

    public final void testEqualsHashCode() {
        EqualsVerifier.forClass(DayOfTheWeek.class).verify();
    }

    @Test
    public final void testConstruct() {

        assertThat(new DayOfTheWeek("MON")).isEqualTo(new DayOfTheWeek("Mon"));

        try {
            new DayOfTheWeek("Monday");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'dayOfTheWeek' does not represent a valid day of the week like 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun' or 'PH': 'Monday'");
        }

    }

    @Test
    public void testToString() {
        
        assertThat(new DayOfTheWeek("Mon").toString()).isEqualTo("MON");
        assertThat(new DayOfTheWeek("Tue").toString()).isEqualTo("TUE");
        assertThat(new DayOfTheWeek("wed").toString()).isEqualTo("WED");
        assertThat(new DayOfTheWeek("THU").toString()).isEqualTo("THU");
        assertThat(new DayOfTheWeek("Fri").toString()).isEqualTo("FRI");
        assertThat(new DayOfTheWeek("Sat").toString()).isEqualTo("SAT");
        assertThat(new DayOfTheWeek("SUN").toString()).isEqualTo("SUN");
        assertThat(new DayOfTheWeek("PH").toString()).isEqualTo("PH");
        
    }
    
    @Test
    public final void testIsValidTRUE() {

        assertThat(DayOfTheWeek.isValid(null)).isTrue();
        assertThat(DayOfTheWeek.isValid("Mon")).isTrue();
        assertThat(DayOfTheWeek.isValid("Tue")).isTrue();
        assertThat(DayOfTheWeek.isValid("Wed")).isTrue();
        assertThat(DayOfTheWeek.isValid("Thu")).isTrue();
        assertThat(DayOfTheWeek.isValid("Fri")).isTrue();
        assertThat(DayOfTheWeek.isValid("Sat")).isTrue();
        assertThat(DayOfTheWeek.isValid("Sun")).isTrue();
        assertThat(DayOfTheWeek.isValid("PH")).isTrue();

    }

    @Test
    public final void testIsValidFALSE() {

        assertThat(DayOfTheWeek.isValid("")).isFalse();
        assertThat(DayOfTheWeek.isValid("Saturday")).isFalse();
        assertThat(DayOfTheWeek.isValid("S")).isFalse();
        assertThat(DayOfTheWeek.isValid("Satur")).isFalse();
        assertThat(DayOfTheWeek.isValid("Fr")).isFalse();

    }

    @Test
    public final void testValueOf() {
        assertThat(DayOfTheWeek.valueOf(null)).isNull();
        assertThat(DayOfTheWeek.valueOf("Fri")).isEqualTo(new DayOfTheWeek("FRI"));
    }

    @Test
    public final void testRequireArgValid() {

        try {
            DayOfTheWeek.requireArgValid("a", "");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'a' does not represent a valid day of the week like 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun' or 'PH': ''");
        }

        try {
            DayOfTheWeek.requireArgValid("b", "Friday");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'b' does not represent a valid day of the week like 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun' or 'PH': 'Friday'");
        }

    }

    @Test
    public void testJPA() {

        // PREPARE
        beginTransaction();
        getEm().persist(new DayOfTheWeekParentEntity(1));
        commitTransaction();

        // TEST UPDATE
        beginTransaction();
        final DayOfTheWeekParentEntity entity = getEm().find(DayOfTheWeekParentEntity.class, 1L);
        entity.setDayOfTheWeek(new DayOfTheWeek("Fri"));
        commitTransaction();

        // VERIFY
        beginTransaction();
        final DayOfTheWeekParentEntity copy = getEm().find(DayOfTheWeekParentEntity.class, 1L);
        assertThat(copy).isNotNull();
        assertThat(copy.getId()).isEqualTo(1);
        assertThat(copy.getDayOfTheWeek()).isNotNull();
        assertThat(copy.getDayOfTheWeek().toString()).isEqualTo("FRI");
        commitTransaction();

    }

}
// CHECKSTYLE:ON
