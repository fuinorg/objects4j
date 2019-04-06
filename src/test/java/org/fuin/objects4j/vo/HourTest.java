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
public class HourTest extends AbstractPersistenceTest {

    public final void testEqualsHashCode() {
        EqualsVerifier.forClass(Hour.class).verify();
    }

    @Test
    public final void testConstruct() {

        assertThat(new Hour("23:59")).isEqualTo(new Hour("23:59"));

        try {
            new Hour("23:");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'hour' does not represent a valid hour like '00:00' or '23:59' or '24:00': '23:'");
        }

    }

    @Test
    public final void testIsValid() {

        assertThat(Hour.isValid(null)).isTrue();
        assertThat(Hour.isValid("00:00")).isTrue();
        assertThat(Hour.isValid("00:01")).isTrue();
        assertThat(Hour.isValid("01:00")).isTrue();
        assertThat(Hour.isValid("11:59")).isTrue();
        assertThat(Hour.isValid("12:00")).isTrue();
        assertThat(Hour.isValid("12:01")).isTrue();
        assertThat(Hour.isValid("23:59")).isTrue();
        assertThat(Hour.isValid("24:00")).isTrue();

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
