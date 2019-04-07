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
public class WeeklyOpeningHoursTest extends AbstractPersistenceTest {

    public final void testEqualsHashCode() {
        EqualsVerifier.forClass(WeeklyOpeningHours.class).verify();
    }

    @Test
    public final void testConstruct() {

        assertThat(new WeeklyOpeningHours("Mon/Tue 09:00-17:00")).isEqualTo(new WeeklyOpeningHours(new DayOpeningHours("Mon 09:00-17:00"), new DayOpeningHours("Tue 09:00-17:00")));

        try {
            new WeeklyOpeningHours("Mon+Tue 13:00-14:00*17:00-19:00");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'weeklyOpeningHours' does not represent valid weekly opening hours like 'Mon-Fri 09:00-12:00+13:00-17:00,Sat/Sun 09:-12:00': 'Mon+Tue 13:00-14:00*17:00-19:00'");
        }
        
    }
    
    @Test
    public final void testIsValidTRUE() {

        assertThat(WeeklyOpeningHours.isValid(null)).isTrue();
        assertThat(WeeklyOpeningHours.isValid("Mon 00:00-24:00")).isTrue();
        assertThat(WeeklyOpeningHours.isValid("Mon 07:00-09:00+10:00-12:00")).isTrue();
        assertThat(WeeklyOpeningHours.isValid("Mon/Tue 07:00-09:00+10:00-12:00+13:00-15:00")).isTrue();
        assertThat(WeeklyOpeningHours.isValid("Mon-Tue 07:00-09:00")).isTrue();
        assertThat(WeeklyOpeningHours.isValid("Mon-Sun 06:00-18:00")).isTrue();
        assertThat(WeeklyOpeningHours.isValid("Mon-Fri 06:00-18:00,Sat/Sun 06:00-12:00")).isTrue();

    }

    @Test
    public final void testIsValidFALSE() {

        assertThat(WeeklyOpeningHours.isValid("")).isFalse();
        assertThat(WeeklyOpeningHours.isValid(" ")).isFalse();
        assertThat(WeeklyOpeningHours.isValid(", ")).isFalse();
        assertThat(WeeklyOpeningHours.isValid("Mon 00:00-24:00,Mon 01:00-02:00")).isFalse();
        assertThat(WeeklyOpeningHours.isValid("Mon-Fri 00:00-24:00,Wed 00:00-24:00")).isFalse();

    }
    
    @Test
    public final void testValueOf() {
        assertThat(WeeklyOpeningHours.valueOf(null)).isNull();
        assertThat(WeeklyOpeningHours.valueOf("Mon/Tue 06:00-18:00,Wed 06:00-12:00")).isEqualTo(
                new WeeklyOpeningHours(new DayOpeningHours("Mon 06:00-18:00"),
                        new DayOpeningHours("Tue 06:00-18:00"),
                        new DayOpeningHours("Wed 06:00-12:00")));
    }

    @Test
    public final void testToString() {
        
        assertThat(WeeklyOpeningHours.valueOf("Mon 06:00-18:00").toString()).isEqualTo("MON 06:00-18:00");
        assertThat(WeeklyOpeningHours.valueOf("Mon/Tue 06:00-18:00,Wed 06:00-12:00").toString()).isEqualTo("MON 06:00-18:00,TUE 06:00-18:00,WED 06:00-12:00");
        
    }
    
    @Test
    public final void testRequireArgValid() {

        try {
            WeeklyOpeningHours.requireArgValid("a", "");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'a' does not represent valid weekly opening hours like 'Mon-Fri 09:00-12:00+13:00-17:00,Sat/Sun 09:-12:00': ''");
        }

        try {
            WeeklyOpeningHours.requireArgValid("b", "Mon 17-18,Tue 19-20");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'b' does not represent valid weekly opening hours like 'Mon-Fri 09:00-12:00+13:00-17:00,Sat/Sun 09:-12:00': 'Mon 17-18,Tue 19-20'");
        }

    }

    @Test
    public void testJPA() {

        // PREPARE
        beginTransaction();
        getEm().persist(new WeeklyOpeningHoursParentEntity(1));
        commitTransaction();

        // TEST UPDATE
        beginTransaction();
        final WeeklyOpeningHoursParentEntity entity = getEm().find(WeeklyOpeningHoursParentEntity.class, 1L);
        entity.setWeeklyOpeningHours(new WeeklyOpeningHours("Mon/Tue 06:00-18:00,Wed 06:00-12:00"));
        commitTransaction();

        // VERIFY
        beginTransaction();
        final WeeklyOpeningHoursParentEntity copy = getEm().find(WeeklyOpeningHoursParentEntity.class, 1L);
        assertThat(copy).isNotNull();
        assertThat(copy.getId()).isEqualTo(1);
        assertThat(copy.getWeeklyOpeningHours()).isNotNull();
        assertThat(copy.getWeeklyOpeningHours().toString()).isEqualTo("MON 06:00-18:00,TUE 06:00-18:00,WED 06:00-12:00");
        commitTransaction();

    }

}
// CHECKSTYLE:ON
