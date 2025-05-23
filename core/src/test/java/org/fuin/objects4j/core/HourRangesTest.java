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
import org.fuin.objects4j.core.HourRanges.Change;
import org.fuin.objects4j.core.HourRanges.ChangeType;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.fuin.objects4j.core.HourRanges.ChangeType.ADDED;
import static org.fuin.objects4j.core.HourRanges.ChangeType.REMOVED;

// CHECKSTYLE:OFF
public class HourRangesTest {

    @Test
    void testEqualsHashCode() {

        EqualsVerifier.forClass(HourRanges.class).withRedefinedSuperclass().withIgnoredFields("ranges").suppress(Warning.NULL_FIELDS)
                .verify();

        assertThat(h("13:00-14:00")).isEqualTo(h(r("13:00-14:00")));
        assertThat(h("13:00-14:00")).isNotEqualTo(h(r("13:00-14:01")));

        assertThat(h("09:00-12:00+13:00-17:00")).isEqualTo(h(r("09:00-12:00"), r("13:00-17:00")));
        assertThat(h("09:00-12:00+13:00-17:00")).isNotEqualTo(h(r("09:00-12:01"), r("13:00-17:00")));

        assertThat(h("13:00-14:00+16:00-17:00")).isEqualTo(h("16:00-17:00+13:00-14:00"));
        assertThat(h("13:00-14:00+16:00-17:00")).isEqualTo(h(r("13:00-14:00"), r("16:00-17:00")));
        assertThat(h(r("13:00-14:00"), r("16:00-17:00"))).isEqualTo(h("13:00-14:00+16:00-17:00"));
        assertThat(h(r("13:00-14:00"), r("16:00-17:00"))).isEqualTo(h(r("13:00-14:00"), r("16:00-17:00")));

        assertThat(h(r("13:00-14:00"), r("16:00-17:00")).asBaseType()).isEqualTo("13:00-14:00+16:00-17:00");
        assertThat(h("13:00-14:00+16:00-17:00").asBaseType()).isEqualTo("13:00-14:00+16:00-17:00");
        assertThat(h(r("16:00-17:00"), r("13:00-14:00")).asBaseType()).isEqualTo("13:00-14:00+16:00-17:00");
        assertThat(h("16:00-17:00+13:00-14:00").asBaseType()).isEqualTo("13:00-14:00+16:00-17:00");

        assertThat(h("18:00-03:00+03:00-06:00").asBaseType()).isEqualTo("03:00-06:00+18:00-03:00");

    }

    @Test
    void testConstruct() {

        try {
            new HourRanges("13:00-14:00*17:00-19:00");
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "The argument 'ranges' does not represent a valid hour range like '09:00-12:00+13:00-17:00': '13:00-14:00*17:00-19:00'");
        }

    }

    @Test
    void testIsValidTRUE() {

        assertThat(HourRanges.isValid(null)).isTrue();
        assertThat(HourRanges.isValid("00:00-24:00")).isTrue();
        assertThat(HourRanges.isValid("07:00-09:00+10:00-12:00")).isTrue();
        assertThat(HourRanges.isValid("07:00-09:00+10:00-12:00+13:00-15:00")).isTrue();
        assertThat(HourRanges.isValid("07:00-09:00+")).isTrue();
        assertThat(HourRanges.isValid("+07:00-09:00")).isTrue();
        assertThat(HourRanges.isValid("07:00-09:00++10:00-12:00")).isTrue();

    }

    @Test
    void testIsValidFALSE() {

        assertThat(HourRanges.isValid("")).isFalse();
        assertThat(HourRanges.isValid("+")).isFalse();
        assertThat(HourRanges.isValid("++")).isFalse();
        assertThat(HourRanges.isValid("07:00-09:00+12:00")).isFalse();
        assertThat(HourRanges.isValid("07:00-09:00+1+12:00-13:00")).isFalse();

    }

    @Test
    void testValueOfString() {
        assertThat(HourRanges.valueOf((String) null)).isNull();
        assertThat(HourRanges.valueOf("09:00-12:00+13:00-17:00"))
                .isEqualTo(new HourRanges(new HourRange("09:00-12:00"), new HourRange("13:00-17:00")));
    }

    @Test
    void testValueOfBitSet() {

        assertThat(HourRanges.valueOf(new MinutesBitSetBuilder().fromTo(0, 0, 24, 00).build()))
                .isEqualTo(HourRanges.valueOf("00:00-24:00"));

        assertThat(HourRanges.valueOf(new MinutesBitSetBuilder().fromTo(8, 0, 13, 00).build()))
                .isEqualTo(HourRanges.valueOf("08:00-13:00"));

        assertThat(HourRanges.valueOf(new MinutesBitSetBuilder().fromTo(0, 0, 1, 30).build())).isEqualTo(HourRanges.valueOf("00:00-01:30"));

        assertThat(HourRanges.valueOf(new MinutesBitSetBuilder().fromTo(8, 0, 12, 00).fromTo(13, 0, 17, 00).build()))
                .isEqualTo(HourRanges.valueOf("08:00-12:00+13:00-17:00"));

    }

    @Test
    void testToMinutesTwoDays() {

        try {
            new HourRanges("18:00-03:00").toMinutes();
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).contains("two days");
        }

    }

    @Test
    void testToMinutesSingleDay() {

        assertThat(new HourRanges("00:00-24:00").toMinutes()).isEqualTo(new MinutesBitSetBuilder().fromTo(0, 0, 24, 00).build());
        assertThat(new HourRanges("01:00-23:00").toMinutes()).isEqualTo(new MinutesBitSetBuilder().fromTo(1, 0, 23, 00).build());
        assertThat(new HourRanges("08:00-12:00+13:00-17:00").toMinutes())
                .isEqualTo(new MinutesBitSetBuilder().fromTo(8, 0, 12, 00).fromTo(13, 0, 17, 00).build());

    }

    @Test
    void testRequireArgValid() {

        try {
            HourRanges.requireArgValid("a", "");
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'a' does not represent a valid hour range like '09:00-12:00+13:00-17:00': ''");
        }

        try {
            HourRanges.requireArgValid("b", "17-18+19-20");
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'b' does not represent a valid hour range like '09:00-12:00+13:00-17:00': '17-18+19-20'");
        }

    }

    @Test
    void testIterable() {

        final Iterator<HourRange> itA = new HourRanges("00:00-24:00").iterator();
        assertThat(itA.next()).isEqualTo(new HourRange("00:00-24:00"));
        assertThat(itA.hasNext()).isFalse();

        final Iterator<HourRange> itB = new HourRanges("00:00-05:00+06:00-11:00+12:00-17:00+18:00-23:00").iterator();
        assertThat(itB.next()).isEqualTo(new HourRange("00:00-05:00"));
        assertThat(itB.next()).isEqualTo(new HourRange("06:00-11:00"));
        assertThat(itB.next()).isEqualTo(new HourRange("12:00-17:00"));
        assertThat(itB.next()).isEqualTo(new HourRange("18:00-23:00"));
        assertThat(itB.hasNext()).isFalse();

    }

    @Test
    void testNormalize() {

        org.assertj.core.api.Assertions.assertThat(h("00:00-24:00").normalize()).containsOnly(h("00:00-24:00"));
        org.assertj.core.api.Assertions.assertThat(h("08:00-18:00").normalize()).containsOnly(h("08:00-18:00"));
        org.assertj.core.api.Assertions.assertThat(h("09:00-12:00+13:00-17:00").normalize()).containsOnly(h("09:00-12:00+13:00-17:00"));
        org.assertj.core.api.Assertions.assertThat(h("18:00-03:00").normalize()).containsOnly(h("18:00-24:00"), h("00:00-03:00"));
        org.assertj.core.api.Assertions.assertThat(h("18:00-03:00+06:00-12:00").normalize()).containsOnly(h("06:00-12:00+18:00-24:00"),
                h("00:00-03:00"));
        org.assertj.core.api.Assertions.assertThat(h("09:00-14:00+18:00-03:00").normalize()).containsOnly(h("09:00-14:00+18:00-24:00"),
                h("00:00-03:00"));
        org.assertj.core.api.Assertions.assertThat(h("23:00-22:00").normalize()).containsOnly(h("23:00-24:00"), h("00:00-22:00"));

    }

    @Test
    void testIsNormalized() {

        assertThat(h("00:00-24:00").isNormalized()).isTrue();
        assertThat(h("18:00-03:00").isNormalized()).isFalse();

    }

    @Test
    void testDiffTwoDays() {

        try {
            h("18:00-03:00").diff(h("18:00-23:00"));
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).contains("from=18:00-03:00");
        }

        try {
            h("18:00-23:00").diff(h("18:00-03:00"));
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).contains("to=18:00-03:00");
        }

    }

    @Test
    void testDiffSingleDay() {

        org.assertj.core.api.Assertions.assertThat(h("00:00-24:00").diff(h("00:00-24:00"))).isEmpty();

        test(h("00:00-24:00"), h("00:00-23:00"), c(REMOVED, "23:00-24:00"));
        test(h("00:00-23:00"), h("00:00-24:00"), c(ADDED, "23:00-24:00"));
        test(h("18:00-24:00"), h("18:00-22:00"), c(REMOVED, "22:00-24:00"));
        test(h("00:00-22:00"), h("18:00-24:00"), c(REMOVED, "00:00-18:00"), c(ADDED, "22:00-24:00"));
        test(h("09:00-18:00"), h("08:00-18:00"), c(ADDED, "08:00-09:00"));
        test(h("09:00-17:00"), h("08:00-18:00"), c(ADDED, "08:00-09:00"), c(ADDED, "17:00-18:00"));
        test(h("08:00-17:00"), h("09:00-18:00"), c(REMOVED, "08:00-09:00"), c(ADDED, "17:00-18:00"));
        test(h("09:00-12:00"), h("09:00-12:00+13:00-17:00"), c(ADDED, "13:00-17:00"));
        test(h("09:00-12:00"), h("13:00-17:00"), c(REMOVED, "09:00-12:00"), c(ADDED, "13:00-17:00"));

    }

    @Test
    void testOverlaps() {

        assertThat(h("00:00-24:00").overlaps(h("00:00-24:00"))).isTrue();
        assertThat(h("00:00-12:00").overlaps(h("12:00-24:00"))).isFalse();
        assertThat(h("00:00-00:02").overlaps(h("00:01-00:02"))).isTrue();
        assertThat(h("00:08-17:00").overlaps(h("08:00-12:00+12:00-17:00"))).isTrue();

    }

    @Test
    void testOpenAt() {

        assertThat(h("00:00-24:00").openAt(r("00:00-24:00"))).isTrue();
        assertThat(h("00:00-24:00").openAt(r("00:00-00:01"))).isTrue();
        assertThat(h("00:00-24:00").openAt(r("23:59-24:00"))).isTrue();
        assertThat(h("08:00-12:00+12:00-18:00").openAt(r("11:55-12:10"))).isTrue();
        assertThat(h("08:00-12:00+13:00-17:00").openAt(r("11:55-12:00"))).isTrue();

        assertThat(h("08:00-18:00").openAt(r("07:30-08:00"))).isFalse();
        assertThat(h("08:00-18:00").openAt(r("07:55-08:10"))).isFalse();
        assertThat(h("08:00-12:00+13:00-17:00").openAt(r("11:59-12:01"))).isFalse();
        assertThat(h("08:00-12:00+13:00-17:00").openAt(r("12:00-13:00"))).isFalse();

    }

    @Test
    void testAdd() {

        assertThat(h("00:00-24:00").add(h("00:00-24:00"))).isEqualTo(h("00:00-24:00"));
        assertThat(h("00:00-12:00").add(h("12:00-24:00"))).isEqualTo(h("00:00-24:00"));
        assertThat(h("06:00-12:00").add(h("12:00-18:00"))).isEqualTo(h("06:00-18:00"));
        assertThat(h("06:00-12:00").add(h("10:00-18:00"))).isEqualTo(h("06:00-18:00"));
        assertThat(h("06:00-12:00").add(h("05:00-13:00"))).isEqualTo(h("05:00-13:00"));

    }

    @Test
    void testRemove() {

        assertThat(h("00:00-24:00").remove(h("00:00-24:00"))).isNull();
        assertThat(h("00:00-24:00").remove(h("00:00-00:01"))).isEqualTo(h("00:01-24:00"));
        assertThat(h("00:00-24:00").remove(h("23:59-24:00"))).isEqualTo(h("00:00-23:59"));
        assertThat(h("09:00-17:00").remove(h("12:00-13:00"))).isEqualTo(h("09:00-12:00+13:00-17:00"));
        assertThat(h("09:00-17:00").remove(h("12:00-13:00"))).isEqualTo(h("09:00-12:00+13:00-17:00"));

    }

    @Test
    void testIsSimilarTo() {

        assertThat(h("13:00-14:00+14:00-16:00").isSimilarTo(h("13:00-16:00"))).isTrue();
        assertThat(h("18:00-21:00+21:00-03:00").isSimilarTo(h("18:00-03:00"))).isTrue();
        assertThat(h("12:00-15:00+18:00-21:00+21:00-03:00").isSimilarTo(h("12:00-15:00+18:00-03:00"))).isTrue();

        assertThat(h("15:00-16:00+13:00-14:00").isSimilarTo(h("13:00-16:00"))).isFalse();

    }

    @Test
    void testCompress() {

        assertThat(h("13:00-14:00+14:00-16:00").compress()).isEqualTo(h("13:00-16:00"));
        assertThat(h("18:00-21:00+21:00-03:00").compress()).isEqualTo(h("18:00-03:00"));
        assertThat(h("00:00-06:00+06:00-18:00+18:00-24:00").compress()).isEqualTo(h("00:00-24:00"));
        assertThat(h("12:00-15:00+18:00-21:00+21:00-03:00").compress()).isEqualTo(h("12:00-15:00+18:00-03:00"));

    }

    private void test(HourRanges from, HourRanges to, Change... changes) {
        org.assertj.core.api.Assertions.assertThat(from.diff(to)).containsOnly(changes);
    }

    private Change c(final ChangeType type, final String range) {
        return new Change(type, new HourRange(range));
    }

    private HourRange r(final String str) {
        return new HourRange(str);
    }

    private HourRanges h(final String str) {
        return new HourRanges(str);
    }

    private HourRanges h(final HourRange... ranges) {
        return new HourRanges(ranges);
    }

}
// CHECKSTYLE:ON
