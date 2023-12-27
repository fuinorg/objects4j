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

import nl.jqno.equalsverifier.EqualsVerifier;
import org.assertj.core.api.Assertions;
import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.vo.DayOpeningHours.Change;
import org.fuin.objects4j.vo.HourRanges.ChangeType;
import org.fuin.units4j.AbstractPersistenceTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.fuin.objects4j.vo.DayOfTheWeek.*;
import static org.fuin.objects4j.vo.HourRanges.ChangeType.ADDED;
import static org.fuin.objects4j.vo.HourRanges.ChangeType.REMOVED;

// CHECKSTYLE:OFF
public class WeeklyOpeningHoursTest extends AbstractPersistenceTest {

    public final void testEqualsHashCode() {
        EqualsVerifier.forClass(WeeklyOpeningHours.class).verify();
    }

    @Test
    public final void testConstruct() {

        assertThat(w("Mon/Tue 09:00-17:00")).isNotEqualTo(new WeeklyOpeningHours(d("Mon 09:00-17:00"), d("Tue 09:00-17:00")));
        assertThat(w("Fri 18:00-03:00")).isEqualTo(w("Fri 18:00-03:00"));

        try {
            w("Mon+Tue 13:00-14:00*17:00-19:00");
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "The argument 'weeklyOpeningHours' does not represent valid weekly opening hours like 'Mon-Fri 09:00-12:00+13:00-17:00,Sat/Sun 09:-12:00': 'Mon+Tue 13:00-14:00*17:00-19:00'");
        }

    }

    @Test
    public final void testAsString() {
        assertThat(w("Mon/Tue 09:00-17:00").asString()).isEqualTo("MON/TUE 09:00-17:00");
        assertThat(w("Mon-Fri 09:00-17:00").asString()).isEqualTo("MON-FRI 09:00-17:00");
        assertThat(w("Fri 18:00-03:00").asString()).isEqualTo("FRI 18:00-03:00");
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
        assertThat(WeeklyOpeningHours.isValid("Mon 04:00-18:00,Sun 18:00-03:00")).isTrue();

    }

    @Test
    public final void testIsValidFALSE() {

        assertThat(WeeklyOpeningHours.isValid("")).isFalse();
        assertThat(WeeklyOpeningHours.isValid(" ")).isFalse();
        assertThat(WeeklyOpeningHours.isValid(", ")).isFalse();
        assertThat(WeeklyOpeningHours.isValid("Mon 00:00-24:00,Mon 01:00-02:00")).isFalse();
        assertThat(WeeklyOpeningHours.isValid("Mon-Fri 00:00-24:00,Wed 00:00-24:00")).isFalse();
        assertThat(WeeklyOpeningHours.isValid("Mon 00:00-03:00,Sun 18:00-03:00")).isFalse();

    }

    @Test
    public final void testValueOf() {
        assertThat(WeeklyOpeningHours.valueOf(null)).isNull();
        assertThat(WeeklyOpeningHours.valueOf("Mon/Tue 06:00-18:00,Wed 06:00-12:00"))
                .isEqualTo(new WeeklyOpeningHours("Mon/Tue 06:00-18:00,Wed 06:00-12:00"));
    }

    @Test
    public final void testToString() {

        assertThat(WeeklyOpeningHours.valueOf("Mon 06:00-18:00").toString()).isEqualTo("MON 06:00-18:00");
        assertThat(WeeklyOpeningHours.valueOf("Mon/Tue 06:00-18:00,Wed 06:00-12:00").toString())
                .isEqualTo("MON/TUE 06:00-18:00,WED 06:00-12:00");

    }

    @Test
    public final void testRequireArgValid() {

        try {
            WeeklyOpeningHours.requireArgValid("a", "");
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "The argument 'a' does not represent valid weekly opening hours like 'Mon-Fri 09:00-12:00+13:00-17:00,Sat/Sun 09:-12:00': ''");
        }

        try {
            WeeklyOpeningHours.requireArgValid("b", "Mon 17-18,Tue 19-20");
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "The argument 'b' does not represent valid weekly opening hours like 'Mon-Fri 09:00-12:00+13:00-17:00,Sat/Sun 09:-12:00': 'Mon 17-18,Tue 19-20'");
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
        entity.setWeeklyOpeningHours(w("Mon/Tue 06:00-18:00,Wed 06:00-12:00"));
        commitTransaction();

        // VERIFY
        beginTransaction();
        final WeeklyOpeningHoursParentEntity copy = getEm().find(WeeklyOpeningHoursParentEntity.class, 1L);
        assertThat(copy).isNotNull();
        assertThat(copy.getId()).isEqualTo(1);
        assertThat(copy.getWeeklyOpeningHours()).isNotNull();
        assertThat(copy.getWeeklyOpeningHours()).isEqualTo(w("Mon/Tue 06:00-18:00,Wed 06:00-12:00"));
        commitTransaction();

    }

    @Test
    public void testNormalize() {

        assertThat(w("Fri 18:00-24:00,Sat 09:00-18:00").normalize()).isEqualTo(w("Fri 18:00-24:00,Sat 09:00-18:00"));
        assertThat(w("Fri 18:00-03:00,Sat 06:00-12:00").normalize()).isEqualTo(w("FRI 18:00-24:00,SAT 00:00-03:00+06:00-12:00"));
        assertThat(w("Fri 18:00-03:00,Sat 09:00-18:00").normalize()).isEqualTo(w("Fri 18:00-24:00,Sat 00:00-03:00+09:00-18:00"));

    }

    @Test
    public void testDiff() {

        org.assertj.core.api.Assertions.assertThat(w("Mon-Fri 09:00-18:00,Sat 09:00-12:00").diff(w("Mon-Fri 09:00-18:00,Sat 09:00-12:00")))
                .isEmpty();

        org.assertj.core.api.Assertions.assertThat(w("Mon-Sun 00:00-24:00").diff(w("Mon-Fri 00:00-24:00,Sat/Sun 00:00-24:00"))).isEmpty();

        org.assertj.core.api.Assertions.assertThat(w("Mon-Fri 09:00-18:00,Sat 09:00-12:00").diff(w("Mon-Fri 08:00-18:00,Sat 09:00-13:00")))
                .containsOnly(c(MON, ChangeType.ADDED, "08:00-09:00"), c(TUE, ChangeType.ADDED, "08:00-09:00"),
                        c(WED, ChangeType.ADDED, "08:00-09:00"), c(THU, ChangeType.ADDED, "08:00-09:00"),
                        c(FRI, ChangeType.ADDED, "08:00-09:00"), c(SAT, ChangeType.ADDED, "12:00-13:00"));

        org.assertj.core.api.Assertions.assertThat(w("Mon-Fri 09:00-18:00").diff(w("Mon-Thu 09:00-18:00")))
                .containsOnly(c(FRI, ChangeType.REMOVED, "09:00-18:00"));

        org.assertj.core.api.Assertions.assertThat(w("Mon-Thu 09:00-18:00").diff(w("Mon-Fri 09:00-18:00")))
                .containsOnly(c(FRI, ChangeType.ADDED, "09:00-18:00"));

    }

    @Test
    public void testAsRemovedChanges() {

        org.assertj.core.api.Assertions.assertThat(w("Mon 06:00-12:00,Tue 08:00-13:00").asRemovedChanges())
                .containsOnly(c(MON, REMOVED, "06:00-12:00"), c(TUE, REMOVED, "08:00-13:00"));

        org.assertj.core.api.Assertions.assertThat(w("Mon 06:00-12:00+13:00-17:00").asRemovedChanges())
                .containsOnly(c(MON, REMOVED, "06:00-12:00"), c(MON, REMOVED, "13:00-17:00"));

    }

    @Test
    public void testAsAddedChanges() {

        org.assertj.core.api.Assertions.assertThat(w("Mon 06:00-12:00,Tue 08:00-13:00").asAddedChanges())
                .containsOnly(c(MON, ADDED, "06:00-12:00"), c(TUE, ADDED, "08:00-13:00"));

        org.assertj.core.api.Assertions.assertThat(w("Mon 06:00-12:00+13:00-17:00").asAddedChanges())
                .containsOnly(c(MON, ADDED, "06:00-12:00"), c(MON, ADDED, "13:00-17:00"));

    }

    @Test
    public void testOpenAt() {

        assertThat(w("Mon 00:00-24:00").openAt(d("Mon 00:00-24:00"))).isTrue();
        assertThat(w("Mon 00:00-24:00").openAt(d("Mon 00:00-00:01"))).isTrue();
        assertThat(w("Mon 00:00-24:00").openAt(d("Mon 23:59-24:00"))).isTrue();
        assertThat(w("Mon 08:00-12:00+12:00-18:00").openAt(d("Mon 11:55-12:10"))).isTrue();
        assertThat(w("Mon 08:00-12:00+13:00-17:00").openAt(d("Mon 11:55-12:00"))).isTrue();
        assertThat(w("Mon-Fri 08:00-18:00").openAt(d("Mon 08:00-12:00"))).isTrue();
        assertThat(w("Mon-Fri 08:00-18:00,Sat 09:00-13:00").openAt(d("Fri 08:00-18:00"))).isTrue();
        assertThat(w("Mon-Fri 08:00-18:00,Sat 09:00-13:00").openAt(d("Sat 11:00-12:00"))).isTrue();

        assertThat(w("Mon 08:00-18:00").openAt(d("Mon 07:30-08:00"))).isFalse();
        assertThat(w("Mon 08:00-18:00").openAt(d("Mon 07:55-08:10"))).isFalse();
        assertThat(w("Mon 08:00-12:00+13:00-17:00").openAt(d("Mon 11:59-12:01"))).isFalse();
        assertThat(w("Mon 08:00-12:00+13:00-17:00").openAt(d("Mon 12:00-13:00"))).isFalse();
        assertThat(w("Mon-Fri 08:00-18:00,Sat 09:00-13:00").openAt(d("Fri 08:00-18:01"))).isFalse();
        assertThat(w("Mon-Fri 08:00-18:00,Sat 09:00-13:00").openAt(d("Sat 08:00-14:00"))).isFalse();

    }

    @Test
    public void testIsImilarTo() {

        assertThat(w("Fri 18:00-03:00").isSimilarTo(w("Fri 18:00-24:00,Sat 00:00-03:00"))).isTrue();
        assertThat(w("Fri 18:00-19:00+19:00-20:00").isSimilarTo(w("Fri 18:00-20:00"))).isTrue();
        assertThat(w("Mon/Tue 09:00-18:00").isSimilarTo(w("Mon 09:00-18:00,Tue 09:00-18:00"))).isTrue();
        assertThat(w("Mon-Tue 09:00-18:00").isSimilarTo(w("Mon 09:00-18:00,Tue 09:00-18:00"))).isTrue();

    }

    @Test
    public void compress() {

        assertThat(w("Mon 00:00-24:00").compress()).isEqualTo(w("Mon 00:00-24:00"));
        assertThat(w("Mon 00:00-24:00,Tue 00:00-24:00,Wed 00:00-24:00,Thu 00:00-24:00,Fri 00:00-24:00,Sat 00:00-24:00,Sun 00:00-24:00")
                .compress()).isEqualTo(w("Mon-Sun 00:00-24:00"));
        assertThat(w("Mon/Tue 09:00-18:00,Fri 09:00-18:00").compress()).isEqualTo(w("Mon/Tue/Fri 09:00-18:00"));

    }

    private WeeklyOpeningHours w(final String str) {
        return new WeeklyOpeningHours(str);
    }

    private DayOpeningHours d(final String str) {
        return new DayOpeningHours(str);
    }

    private Change c(final DayOfTheWeek day, final ChangeType type, final String range) {
        return new Change(type, day, new HourRange(range));
    }

}
// CHECKSTYLE:ON
