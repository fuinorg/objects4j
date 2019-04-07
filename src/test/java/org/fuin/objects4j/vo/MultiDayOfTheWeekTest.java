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
public class MultiDayOfTheWeekTest extends AbstractPersistenceTest {

    public final void testEqualsHashCode() {
        EqualsVerifier.forClass(MultiDayOfTheWeek.class).verify();
    }

    @Test
    public final void testConstruct() {

        assertThat(new MultiDayOfTheWeek("Mon/Tue")).isEqualTo(new MultiDayOfTheWeek(DayOfTheWeek.MON, DayOfTheWeek.TUE));
        assertThat(new MultiDayOfTheWeek("Mon")).isEqualTo(new MultiDayOfTheWeek(DayOfTheWeek.MON));

        try {
            new MultiDayOfTheWeek("MON+TUE");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "The argument 'multipleDayOfTheWeek' does not represent valid days of the week like 'Mon/Tue/Wed-Fri': 'MON+TUE'");
        }

    }

    @Test
    public final void testIsValidTRUE() {

        assertThat(MultiDayOfTheWeek.isValid(null)).isTrue();
        assertThat(MultiDayOfTheWeek.isValid("Mon")).isTrue();
        assertThat(MultiDayOfTheWeek.isValid("MON")).isTrue();
        assertThat(MultiDayOfTheWeek.isValid("Mon/Tue")).isTrue();
        assertThat(MultiDayOfTheWeek.isValid("Mon/Wed/Fri")).isTrue();
        assertThat(MultiDayOfTheWeek.isValid("Mon-Wed")).isTrue();
        assertThat(MultiDayOfTheWeek.isValid("Mon-Sun")).isTrue();
        assertThat(MultiDayOfTheWeek.isValid("Mon/Tue/Thu-Fri")).isTrue();

    }

    @Test
    public final void testIsValidFALSE() {

        assertThat(MultiDayOfTheWeek.isValid("")).isFalse();
        assertThat(MultiDayOfTheWeek.isValid("/")).isFalse();
        assertThat(MultiDayOfTheWeek.isValid("//")).isFalse();
        assertThat(MultiDayOfTheWeek.isValid("Mon+Tue")).isFalse();
        assertThat(MultiDayOfTheWeek.isValid("Mon/Tue/Sat+Sun")).isFalse();

    }

    @Test
    public final void testValueOf() {
        assertThat(MultiDayOfTheWeek.valueOf(null)).isNull();
        assertThat(MultiDayOfTheWeek.valueOf("Mon/Tue")).isEqualTo(new MultiDayOfTheWeek(DayOfTheWeek.MON, DayOfTheWeek.TUE));
    }

    @Test
    public final void testToString() {

        assertThat(new MultiDayOfTheWeek(DayOfTheWeek.MON).toString()).isEqualTo("MON");
        assertThat(new MultiDayOfTheWeek(DayOfTheWeek.MON, DayOfTheWeek.TUE).toString()).isEqualTo("MON/TUE");
        assertThat(new MultiDayOfTheWeek(DayOfTheWeek.MON, DayOfTheWeek.TUE, DayOfTheWeek.WED).toString()).isEqualTo("MON/TUE/WED");
        assertThat(new MultiDayOfTheWeek(DayOfTheWeek.MON, DayOfTheWeek.TUE, DayOfTheWeek.WED, DayOfTheWeek.THU).toString())
                .isEqualTo("MON/TUE/WED/THU");
        assertThat(
                new MultiDayOfTheWeek(DayOfTheWeek.MON, DayOfTheWeek.TUE, DayOfTheWeek.WED, DayOfTheWeek.THU, DayOfTheWeek.FRI).toString())
                        .isEqualTo("MON/TUE/WED/THU/FRI");
        assertThat(new MultiDayOfTheWeek(DayOfTheWeek.MON, DayOfTheWeek.TUE, DayOfTheWeek.THU, DayOfTheWeek.FRI).toString())
                .isEqualTo("MON/TUE/THU/FRI");
        assertThat(
                new MultiDayOfTheWeek(DayOfTheWeek.MON, DayOfTheWeek.TUE, DayOfTheWeek.THU, DayOfTheWeek.SAT, DayOfTheWeek.SUN).toString())
                        .isEqualTo("MON/TUE/THU/SAT/SUN");

    }

    @Test
    public final void testRequireArgValid() {

        try {
            MultiDayOfTheWeek.requireArgValid("a", "");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'a' does not represent valid days of the week like 'Mon/Tue/Wed-Fri': ''");
        }

        try {
            MultiDayOfTheWeek.requireArgValid("b", "Mon+Tue");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'b' does not represent valid days of the week like 'Mon/Tue/Wed-Fri': 'Mon+Tue'");
        }

    }

    @Test
    public void testJPA() {

        // PREPARE
        beginTransaction();
        getEm().persist(new MultiDayOfTheWeekParentEntity(1));
        commitTransaction();

        // TEST UPDATE
        beginTransaction();
        final MultiDayOfTheWeekParentEntity entity = getEm().find(MultiDayOfTheWeekParentEntity.class, 1L);
        entity.setMultiDayOfTheWeek(new MultiDayOfTheWeek("MON/TUE"));
        commitTransaction();

        // VERIFY
        beginTransaction();
        final MultiDayOfTheWeekParentEntity copy = getEm().find(MultiDayOfTheWeekParentEntity.class, 1L);
        assertThat(copy).isNotNull();
        assertThat(copy.getId()).isEqualTo(1);
        assertThat(copy.getMultiDayOfTheWeek()).isNotNull();
        assertThat(copy.getMultiDayOfTheWeek().toString()).isEqualTo("MON/TUE");
        commitTransaction();

    }

}
// CHECKSTYLE:ON
