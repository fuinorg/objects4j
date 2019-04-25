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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.fuin.objects4j.vo.DayOfTheWeek.FRI;
import static org.fuin.objects4j.vo.DayOfTheWeek.MON;
import static org.fuin.objects4j.vo.DayOfTheWeek.SAT;
import static org.fuin.objects4j.vo.HourRanges.ChangeType.ADDED;
import static org.fuin.objects4j.vo.HourRanges.ChangeType.REMOVED;
import static org.junit.Assert.fail;

import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.vo.DayOpeningHours.Change;
import org.fuin.objects4j.vo.HourRanges.ChangeType;
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

        assertThat(new DayOpeningHours("Mon 13:00-14:00"))
                .isEqualTo(new DayOpeningHours(DayOfTheWeek.valueOf("Mon"), new HourRanges("13:00-14:00")));

        try {
            new DayOpeningHours("Monday 13-14:00");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "The argument 'dayOpeningHours' does not represent a valid hour range like 'Mon 09:00-12:00+13:00-17:00': 'Monday 13-14:00'");
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
        assertThat(DayOpeningHours.valueOf("Mon 00:00-24:00"))
                .isEqualTo(new DayOpeningHours(DayOfTheWeek.valueOf("Mon"), new HourRanges("13:00-14:00")));
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

    @Test
    public void testNormalize() {

        org.assertj.core.api.Assertions.assertThat(d("MON 00:00-24:00").normalize()).containsOnly(d("MON 00:00-24:00"));
        org.assertj.core.api.Assertions.assertThat(d("WED 08:00-18:00").normalize()).containsOnly(d("WED 08:00-18:00"));
        org.assertj.core.api.Assertions.assertThat(d("WED 09:00-12:00+13:00-17:00").normalize())
                .containsOnly(d("WED 09:00-12:00+13:00-17:00"));
        org.assertj.core.api.Assertions.assertThat(d("FRI 18:00-03:00").normalize()).containsOnly(d("FRI 18:00-24:00"),
                d("SAT 00:00-03:00"));
        org.assertj.core.api.Assertions.assertThat(d("FRI 18:00-03:00+06:00-12:00").normalize())
                .containsOnly(d("FRI 06:00-12:00+18:00-24:00"), d("SAT 00:00-03:00"));
        org.assertj.core.api.Assertions.assertThat(d("FRI 09:00-14:00+18:00-03:00").normalize())
                .containsOnly(d("FRI 09:00-14:00+18:00-24:00"), d("SAT 00:00-03:00"));
        org.assertj.core.api.Assertions.assertThat(d("FRI 23:00-22:00").normalize()).containsOnly(d("FRI 23:00-24:00"),
                d("SAT 00:00-22:00"));

    }

    @Test
    public void testIsNormalized() {

        assertThat(d("MON 00:00-24:00").isNormalized()).isTrue();
        assertThat(d("FRI 18:00-03:00").isNormalized()).isFalse();

    }

    @Test
    public void testDiffDifferentDays() {

        try {
            d("MON 00:00-24:00").diff(d("TUE 00:00-24:00"));
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("Expected same day (MON) for argument 'toOther', but was: TUE");
        }

    }

    @Test
    public void testDiffSameDay() {

        // Both equal
        org.assertj.core.api.Assertions.assertThat(d("MON 00:00-24:00").diff(d("MON 00:00-24:00"))).isEmpty();

        // From = 1 day / To = 1 day
        test(d("MON 00:00-24:00"), d("MON 00:00-23:00"), c(MON, REMOVED, "23:00-24:00"));
        test(d("MON 09:00-18:00"), d("MON 08:00-18:00"), c(MON, ADDED, "08:00-09:00"));
        test(d("MON 09:00-17:00"), d("MON 08:00-18:00"), c(MON, ADDED, "08:00-09:00"), c(MON, ADDED, "17:00-18:00"));
        test(d("MON 08:00-17:00"), d("MON 09:00-18:00"), c(MON, REMOVED, "08:00-09:00"), c(MON, ADDED, "17:00-18:00"));
        test(d("MON 09:00-12:00"), d("MON 09:00-12:00+13:00-17:00"), c(MON, ADDED, "13:00-17:00"));
        test(d("MON 09:00-12:00"), d("MON 13:00-17:00"), c(MON, REMOVED, "09:00-12:00"), c(MON, ADDED, "13:00-17:00"));

        // From = 1 day / To = 2 days
        test(d("FRI 18:00-23:00"), d("FRI 17:00-02:00"), c(FRI, ADDED, "17:00-18:00"), c(FRI, ADDED, "23:00-24:00"),
                c(SAT, ADDED, "00:00-02:00"));

        // From = 2 days / To = 1 day
        test(d("FRI 18:00-03:00"), d("FRI 17:00-23:00"), c(FRI, ADDED, "17:00-18:00"), c(FRI, REMOVED, "23:00-24:00"),
                c(SAT, REMOVED, "00:00-03:00"));

        // From = 2 days / To = 2 days
        test(d("FRI 18:00-03:00"), d("FRI 17:00-02:00"), c(FRI, ADDED, "17:00-18:00"), c(SAT, REMOVED, "02:00-03:00"));

    }

    @Test
    public void testOverlaps() {

        assertThat(d("Mon 00:00-24:00").overlaps(d("Mon 00:00-24:00"))).isTrue();
        assertThat(d("Mon 00:00-12:00").overlaps(d("Mon 12:00-24:00"))).isFalse();
        assertThat(d("Mon 00:00-00:02").overlaps(d("Mon 00:01-00:02"))).isTrue();
        assertThat(d("Mon 00:08-17:00").overlaps(d("Mon 08:00-12:00+12:00-17:00"))).isTrue();

    }

    @Test
    public void testAsRemovedChanges() {

        org.assertj.core.api.Assertions.assertThat(d("Mon 06:00-12:00").asRemovedChanges()).containsOnly(c(MON, REMOVED, "06:00-12:00"));
        org.assertj.core.api.Assertions.assertThat(d("Mon 06:00-12:00+13:00-17:00").asRemovedChanges())
                .containsOnly(c(MON, REMOVED, "06:00-12:00"), c(MON, REMOVED, "13:00-17:00"));

    }

    @Test
    public void testAsAddedChanges() {

        org.assertj.core.api.Assertions.assertThat(d("Mon 06:00-12:00").asAddedChanges()).containsOnly(c(MON, ADDED, "06:00-12:00"));
        org.assertj.core.api.Assertions.assertThat(d("Mon 06:00-12:00+13:00-17:00").asAddedChanges())
                .containsOnly(c(MON, ADDED, "06:00-12:00"), c(MON, ADDED, "13:00-17:00"));

    }

    @Test
    public void testOpenAtHourRange() {

        assertThat(d("Mon 00:00-24:00").openAt(r("00:00-24:00"))).isTrue();
        assertThat(d("Mon 00:00-24:00").openAt(r("00:00-00:01"))).isTrue();
        assertThat(d("Mon 00:00-24:00").openAt(r("23:59-24:00"))).isTrue();
        assertThat(d("Mon 08:00-12:00+12:00-18:00").openAt(r("11:55-12:10"))).isTrue();
        assertThat(d("Mon 08:00-12:00+13:00-17:00").openAt(r("11:55-12:00"))).isTrue();

        assertThat(d("Mon 08:00-18:00").openAt(r("07:30-08:00"))).isFalse();
        assertThat(d("Mon 08:00-18:00").openAt(r("07:55-08:10"))).isFalse();
        assertThat(d("Mon 08:00-12:00+13:00-17:00").openAt(r("11:59-12:01"))).isFalse();
        assertThat(d("Mon 08:00-12:00+13:00-17:00").openAt(r("12:00-13:00"))).isFalse();

    }

    @Test
    public void testOpenAtDayOpeningHours() {

        assertThat(d("Mon 00:00-24:00").openAt(d("Mon 00:00-24:00"))).isTrue();
        assertThat(d("Mon 00:00-24:00").openAt(d("Mon 00:00-00:01"))).isTrue();
        assertThat(d("Mon 00:00-24:00").openAt(d("Mon 23:59-24:00"))).isTrue();
        assertThat(d("Mon 08:00-12:00+12:00-18:00").openAt(d("Mon 11:55-12:10"))).isTrue();
        assertThat(d("Mon 08:00-12:00+13:00-17:00").openAt(d("Mon 11:55-12:00"))).isTrue();
        assertThat(d("Mon 08:00-18:00").openAt(d("Mon 10:00-11:00+12:00-13:00+17:00-18:00"))).isTrue();

        assertThat(d("Mon 08:00-18:00").openAt(d("Tue 08:00-18:00"))).isFalse();
        assertThat(d("Mon 08:00-18:00").openAt(d("Mon 07:30-08:00+17:30-18:30"))).isFalse();
        assertThat(d("Mon 08:00-18:00").openAt(d("Mon 07:55-08:10"))).isFalse();
        assertThat(d("Mon 08:00-12:00+13:00-17:00").openAt(d("Mon 11:59-12:01"))).isFalse();
        assertThat(d("Mon 08:00-12:00+13:00-17:00").openAt(d("Mon 12:00-13:00"))).isFalse();

    }

    @Test
    public void testAddHourRanges() {

        assertThat(d("Mon 00:00-24:00").add(h("00:00-24:00"))).isEqualTo(d("Mon 00:00-24:00"));
        assertThat(d("Mon 00:00-12:00").add(h("12:00-24:00"))).isEqualTo(d("Mon 00:00-24:00"));
        assertThat(d("Mon 06:00-12:00").add(h("12:00-18:00"))).isEqualTo(d("Mon 06:00-18:00"));
        assertThat(d("Mon 06:00-12:00").add(h("10:00-18:00"))).isEqualTo(d("Mon 06:00-18:00"));
        assertThat(d("Mon 06:00-12:00").add(h("05:00-13:00"))).isEqualTo(d("Mon 05:00-13:00"));

    }

    @Test
    public void testAddDayOpeningHours() {

        assertThat(d("Mon 00:00-24:00").add(d("Mon 00:00-24:00"))).isEqualTo(d("Mon 00:00-24:00"));
        assertThat(d("Mon 00:00-12:00").add(d("Mon 12:00-24:00"))).isEqualTo(d("Mon 00:00-24:00"));
        assertThat(d("Mon 06:00-12:00").add(d("Mon 12:00-18:00"))).isEqualTo(d("Mon 06:00-18:00"));
        assertThat(d("Mon 06:00-12:00").add(d("Mon 10:00-18:00"))).isEqualTo(d("Mon 06:00-18:00"));
        assertThat(d("Mon 06:00-12:00").add(d("Mon 05:00-13:00"))).isEqualTo(d("Mon 05:00-13:00"));

    }

    @Test
    public void testRemoveHourRanges() {

        assertThat(d("Mon 00:00-24:00").remove(h("00:00-24:00"))).isNull();
        assertThat(d("Mon 00:00-24:00").remove(h("00:00-00:01"))).isEqualTo(d("Mon 00:01-24:00"));
        assertThat(d("Mon 00:00-24:00").remove(h("23:59-24:00"))).isEqualTo(d("Mon 00:00-23:59"));
        assertThat(d("Mon 09:00-17:00").remove(h("12:00-13:00"))).isEqualTo(d("Mon 09:00-12:00+13:00-17:00"));
        assertThat(d("Mon 09:00-17:00").remove(h("12:00-13:00"))).isEqualTo(d("Mon 09:00-12:00+13:00-17:00"));

    }

    @Test
    public void testRemoveDayOpeningHours() {

        assertThat(d("Mon 00:00-24:00").remove(d("Mon 00:00-24:00"))).isNull();
        assertThat(d("Mon 00:00-24:00").remove(d("Mon 00:00-00:01"))).isEqualTo(d("Mon 00:01-24:00"));
        assertThat(d("Mon 00:00-24:00").remove(d("Mon 23:59-24:00"))).isEqualTo(d("Mon 00:00-23:59"));
        assertThat(d("Mon 09:00-17:00").remove(d("Mon 12:00-13:00"))).isEqualTo(d("Mon 09:00-12:00+13:00-17:00"));
        assertThat(d("Mon 09:00-17:00").remove(d("Mon 12:00-13:00"))).isEqualTo(d("Mon 09:00-12:00+13:00-17:00"));

    }

    private void test(DayOpeningHours from, DayOpeningHours to, Change... changes) {
        org.assertj.core.api.Assertions.assertThat(from.diff(to)).containsOnly(changes);
    }

    private HourRange r(final String str) {
        return new HourRange(str);
    }

    private HourRanges h(final String str) {
        return new HourRanges(str);
    }

    private DayOpeningHours d(final String str) {
        return new DayOpeningHours(str);
    }

    private Change c(final DayOfTheWeek day, final ChangeType type, final String range) {
        return new Change(type, day, new HourRange(range));
    }

}
// CHECKSTYLE:ON
