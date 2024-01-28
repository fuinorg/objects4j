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
package org.fuin.objects4j.core;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.assertj.core.api.Assertions;
import org.fuin.objects4j.common.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for the {@link DayOfTheWeek} class.
 */
class DayOfTheWeekTest {

    @Test
    void testEqualsHashCode() {
        EqualsVerifier.forClass(DayOfTheWeek.class).withIgnoredFields("value").suppress(Warning.NULL_FIELDS).verify();
    }

    @Test
    void testToString() {

        assertThat(DayOfTheWeek.MON.toString()).isEqualTo("MON");
        assertThat(DayOfTheWeek.TUE.toString()).isEqualTo("TUE");
        assertThat(DayOfTheWeek.WED.toString()).isEqualTo("WED");
        assertThat(DayOfTheWeek.THU.toString()).isEqualTo("THU");
        assertThat(DayOfTheWeek.FRI.toString()).isEqualTo("FRI");
        assertThat(DayOfTheWeek.SAT.toString()).isEqualTo("SAT");
        assertThat(DayOfTheWeek.SUN.toString()).isEqualTo("SUN");
        assertThat(DayOfTheWeek.PH.toString()).isEqualTo("PH");

    }

    @Test
    void testIsValidTRUE() {

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
    void testIsValidFALSE() {

        assertThat(DayOfTheWeek.isValid("")).isFalse();
        assertThat(DayOfTheWeek.isValid("Saturday")).isFalse();
        assertThat(DayOfTheWeek.isValid("S")).isFalse();
        assertThat(DayOfTheWeek.isValid("Satur")).isFalse();
        assertThat(DayOfTheWeek.isValid("Fr")).isFalse();

    }

    @Test
    void testValueOfString() {
        assertThat(DayOfTheWeek.valueOf((String) null)).isNull();
        assertThat(DayOfTheWeek.valueOf("Fri")).isEqualTo(DayOfTheWeek.FRI);
    }

    @Test
    void testToDayOfWeek() {

        assertThat(DayOfTheWeek.MON.toDayOfWeek()).isEqualTo(DayOfWeek.MONDAY);
        assertThat(DayOfTheWeek.TUE.toDayOfWeek()).isEqualTo(DayOfWeek.TUESDAY);
        assertThat(DayOfTheWeek.WED.toDayOfWeek()).isEqualTo(DayOfWeek.WEDNESDAY);
        assertThat(DayOfTheWeek.THU.toDayOfWeek()).isEqualTo(DayOfWeek.THURSDAY);
        assertThat(DayOfTheWeek.FRI.toDayOfWeek()).isEqualTo(DayOfWeek.FRIDAY);
        assertThat(DayOfTheWeek.SAT.toDayOfWeek()).isEqualTo(DayOfWeek.SATURDAY);
        assertThat(DayOfTheWeek.SUN.toDayOfWeek()).isEqualTo(DayOfWeek.SUNDAY);

    }

    @Test
    void testValueOfDayOfWeek() {

        assertThat(DayOfTheWeek.valueOf((DayOfWeek) null)).isNull();
        assertThat(DayOfTheWeek.valueOf(DayOfWeek.MONDAY)).isEqualTo(DayOfTheWeek.MON);
        assertThat(DayOfTheWeek.valueOf(DayOfWeek.TUESDAY)).isEqualTo(DayOfTheWeek.TUE);
        assertThat(DayOfTheWeek.valueOf(DayOfWeek.WEDNESDAY)).isEqualTo(DayOfTheWeek.WED);
        assertThat(DayOfTheWeek.valueOf(DayOfWeek.THURSDAY)).isEqualTo(DayOfTheWeek.THU);
        assertThat(DayOfTheWeek.valueOf(DayOfWeek.FRIDAY)).isEqualTo(DayOfTheWeek.FRI);
        assertThat(DayOfTheWeek.valueOf(DayOfWeek.SATURDAY)).isEqualTo(DayOfTheWeek.SAT);
        assertThat(DayOfTheWeek.valueOf(DayOfWeek.SUNDAY)).isEqualTo(DayOfTheWeek.SUN);

    }

    @Test
    void testRequireArgValid() {

        try {
            DayOfTheWeek.requireArgValid("a", "");
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "The argument 'a' does not represent a valid day of the week like 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun' or 'PH': ''");
        }

        try {
            DayOfTheWeek.requireArgValid("b", "Friday");
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "The argument 'b' does not represent a valid day of the week like 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun' or 'PH': 'Friday'");
        }

    }

    @Test
    void testFollows() {

        DayOfTheWeek last = null;
        for (final DayOfTheWeek dow : DayOfTheWeek.getAll()) {
            if (last != null) {
                assertThat(dow.follows(last));
            }
            assertThat(DayOfTheWeek.PH.follows(dow)).isFalse();
            assertThat(dow.follows(dow)).isFalse();
            last = dow;
        }

        assertThat(DayOfTheWeek.MON.follows(DayOfTheWeek.SUN)).isFalse();
        assertThat(DayOfTheWeek.PH.follows(DayOfTheWeek.PH)).isFalse();
        assertThat(DayOfTheWeek.MON.follows(DayOfTheWeek.PH)).isFalse();
        assertThat(DayOfTheWeek.PH.follows(DayOfTheWeek.MON)).isFalse();
    }

    @Test
    void testAfter() {

        DayOfTheWeek current = null;
        for (final DayOfTheWeek dow : DayOfTheWeek.getAll()) {
            if (current != null) {
                for (final DayOfTheWeek dow2 : DayOfTheWeek.getPart(dow, DayOfTheWeek.SUN)) {
                    assertThat(dow2.after(current));
                }
            }
            current = dow;
        }

    }

    @Test
    void testNext() {
        assertThat(DayOfTheWeek.PH.next()).isNull();
        assertThat(DayOfTheWeek.MON.next()).isEqualTo(DayOfTheWeek.TUE);
        assertThat(DayOfTheWeek.TUE.next()).isEqualTo(DayOfTheWeek.WED);
        assertThat(DayOfTheWeek.WED.next()).isEqualTo(DayOfTheWeek.THU);
        assertThat(DayOfTheWeek.THU.next()).isEqualTo(DayOfTheWeek.FRI);
        assertThat(DayOfTheWeek.FRI.next()).isEqualTo(DayOfTheWeek.SAT);
        assertThat(DayOfTheWeek.SAT.next()).isEqualTo(DayOfTheWeek.SUN);
        assertThat(DayOfTheWeek.SUN.next()).isEqualTo(DayOfTheWeek.MON);
    }

    @Test
    void testPrevious() {
        assertThat(DayOfTheWeek.PH.previous()).isNull();
        assertThat(DayOfTheWeek.MON.previous()).isEqualTo(DayOfTheWeek.SUN);
        assertThat(DayOfTheWeek.TUE.previous()).isEqualTo(DayOfTheWeek.MON);
        assertThat(DayOfTheWeek.WED.previous()).isEqualTo(DayOfTheWeek.TUE);
        assertThat(DayOfTheWeek.THU.previous()).isEqualTo(DayOfTheWeek.WED);
        assertThat(DayOfTheWeek.FRI.previous()).isEqualTo(DayOfTheWeek.THU);
        assertThat(DayOfTheWeek.SAT.previous()).isEqualTo(DayOfTheWeek.FRI);
        assertThat(DayOfTheWeek.SUN.previous()).isEqualTo(DayOfTheWeek.SAT);
    }

}

