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
import org.fuin.units4j.AbstractPersistenceTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// CHECKSTYLE:OFF
public class HourTest extends AbstractPersistenceTest {

    public final void testEqualsHashCode() {
        EqualsVerifier.forClass(Hour.class).verify();
    }

    @Test
    public final void testConstruct() {

        assertThat(new Hour("23:59")).isEqualTo(new Hour("23:59"));
        assertThat(new Hour(23, 59)).isEqualTo(new Hour("23:59"));
        assertThat(new Hour(0, 0)).isEqualTo(new Hour("00:00"));
        assertThat(new Hour(24, 0)).isEqualTo(new Hour("24:00"));

        try {
            new Hour("23:");
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'hour' does not represent a valid hour like '00:00' or '23:59' or '24:00': '23:'");
        }

        try {
            new Hour(-1, 0);
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'hour' is not a valid hour (0-24): '-1'");
        }

        try {
            new Hour(0, -1);
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'minute' is not a valid minute (0-59): '-1'");
        }

        try {
            new Hour(24, 59);
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'minute' must be '0' if the hour is '24': '59'");
        }

    }

    @Test
    public void testToString() {

        assertThat(new Hour(0, 0).toString()).isEqualTo("00:00");
        assertThat(new Hour(12, 0).toString()).isEqualTo("12:00");
        assertThat(new Hour(0, 59).toString()).isEqualTo("00:59");
        assertThat(new Hour(1, 2).toString()).isEqualTo("01:02");
        assertThat(new Hour(23, 59).toString()).isEqualTo("23:59");

    }

    @Test
    public void testToMinutes() {

        assertThat(new Hour(0, 0).toMinutes()).isEqualTo(0);
        assertThat(new Hour(0, 1).toMinutes()).isEqualTo(1);
        assertThat(new Hour(0, 59).toMinutes()).isEqualTo(59);
        assertThat(new Hour(1, 0).toMinutes()).isEqualTo(60);
        assertThat(new Hour(3, 0).toMinutes()).isEqualTo(180);
        assertThat(new Hour(6, 0).toMinutes()).isEqualTo(360);
        assertThat(new Hour(9, 0).toMinutes()).isEqualTo(540);
        assertThat(new Hour(12, 0).toMinutes()).isEqualTo(720);
        assertThat(new Hour(15, 0).toMinutes()).isEqualTo(900);
        assertThat(new Hour(18, 0).toMinutes()).isEqualTo(1080);
        assertThat(new Hour(21, 0).toMinutes()).isEqualTo(1260);
        assertThat(new Hour(23, 59).toMinutes()).isEqualTo(1439);
        assertThat(new Hour(24, 0).toMinutes()).isEqualTo(1440);

    }

    @Test
    public final void testIsValidTRUE() {

        assertThat(Hour.isValid(null)).isTrue();
        assertThat(Hour.isValid("00:00")).isTrue();
        assertThat(Hour.isValid("00:01")).isTrue();
        assertThat(Hour.isValid("01:00")).isTrue();
        assertThat(Hour.isValid("11:59")).isTrue();
        assertThat(Hour.isValid("12:00")).isTrue();
        assertThat(Hour.isValid("12:01")).isTrue();
        assertThat(Hour.isValid("23:59")).isTrue();
        assertThat(Hour.isValid("24:00")).isTrue();

    }

    @Test
    public final void testIsValidFALSE() {

        assertThat(Hour.isValid("")).isFalse();
        assertThat(Hour.isValid("1")).isFalse();
        assertThat(Hour.isValid("12")).isFalse();
        assertThat(Hour.isValid("12:")).isFalse();
        assertThat(Hour.isValid("12:0")).isFalse();
        assertThat(Hour.isValid("12:000")).isFalse();
        assertThat(Hour.isValid("123:000")).isFalse();
        assertThat(Hour.isValid("23.59")).isFalse();
        assertThat(Hour.isValid("24:01")).isFalse();

    }

    @Test
    public final void testValueOf() {
        assertThat(Hour.valueOf(null)).isNull();
        assertThat(Hour.valueOf("23:59")).isEqualTo(new Hour("23:59"));
    }

    @Test
    public final void testRequireArgValid() {

        try {
            Hour.requireArgValid("a", "");
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'a' does not represent a valid hour like '00:00' or '23:59' or '24:00': ''");
        }

        try {
            Hour.requireArgValid("b", "23:");
            Assertions.fail("");
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'b' does not represent a valid hour like '00:00' or '23:59' or '24:00': '23:'");
        }

    }

    @Test
    public void testJPA() {

        // PREPARE
        beginTransaction();
        getEm().persist(new HourParentEntity(1));
        commitTransaction();

        // TEST UPDATE
        beginTransaction();
        final HourParentEntity entity = getEm().find(HourParentEntity.class, 1L);
        entity.setHour(new Hour("23:59"));
        commitTransaction();

        // VERIFY
        beginTransaction();
        final HourParentEntity copy = getEm().find(HourParentEntity.class, 1L);
        assertThat(copy).isNotNull();
        assertThat(copy.getId()).isEqualTo(1);
        assertThat(copy.getHour()).isNotNull();
        assertThat(copy.getHour().toString()).isEqualTo("23:59");
        commitTransaction();

    }

}
// CHECKSTYLE:ON
