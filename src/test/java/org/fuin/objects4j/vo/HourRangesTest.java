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
public class HourRangesTest extends AbstractPersistenceTest {

    public final void testEqualsHashCode() {
        EqualsVerifier.forClass(HourRanges.class).verify();
    }

    @Test
    public final void testConstruct() {

        assertThat(new HourRanges("13:00-14:00")).isEqualTo(new HourRanges(new HourRange("13:00-14:00")));
        assertThat(new HourRanges("09:00-12:00+13:00-17:00")).isEqualTo(new HourRanges(new HourRange("09:00-12:00"), new HourRange("13:00-17:00")));

        try {
            new HourRanges("13:00-14:00*17:00-19:00");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'ranges' does not represent a valid hour range like '09:00-12:00+13:00-17:00': '13:00-14:00*17:00-19:00'");
        }
        
    }
    
    @Test
    public final void testIsValidTRUE() {

        assertThat(HourRanges.isValid(null)).isTrue();
        assertThat(HourRanges.isValid("00:00-24:00")).isTrue();
        assertThat(HourRanges.isValid("07:00-09:00+10:00-12:00")).isTrue();
        assertThat(HourRanges.isValid("07:00-09:00+10:00-12:00+13:00-15:00")).isTrue();
        assertThat(HourRanges.isValid("07:00-09:00+")).isTrue();
        assertThat(HourRanges.isValid("+07:00-09:00")).isTrue();
        assertThat(HourRanges.isValid("07:00-09:00++10:00-12:00")).isTrue();

    }

    @Test
    public final void testIsValidFALSE() {

        assertThat(HourRanges.isValid("")).isFalse();
        assertThat(HourRanges.isValid("+")).isFalse();
        assertThat(HourRanges.isValid("++")).isFalse();
        assertThat(HourRanges.isValid("07:00-09:00+12:00")).isFalse();
        assertThat(HourRanges.isValid("07:00-09:00+1+12:00-13:00")).isFalse();

    }
    
    @Test
    public final void testValueOf() {
        assertThat(HourRanges.valueOf(null)).isNull();
        assertThat(HourRanges.valueOf("09:00-12:00+13:00-17:00")).isEqualTo(new HourRanges(new HourRange("09:00-12:00"), new HourRange("13:00-17:00")));
    }

    @Test
    public final void testRequireArgValid() {

        try {
            HourRanges.requireArgValid("a", "");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'a' does not represent a valid hour range like '09:00-12:00+13:00-17:00': ''");
        }

        try {
            HourRanges.requireArgValid("b", "17-18+19-20");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'b' does not represent a valid hour range like '09:00-12:00+13:00-17:00': '17-18+19-20'");
        }

    }

    @Test
    public void testJPA() {

        // PREPARE
        beginTransaction();
        getEm().persist(new HourRangesParentEntity(1));
        commitTransaction();

        // TEST UPDATE
        beginTransaction();
        final HourRangesParentEntity entity = getEm().find(HourRangesParentEntity.class, 1L);
        entity.setHourRanges(new HourRanges("00:00-24:00"));
        commitTransaction();

        // VERIFY
        beginTransaction();
        final HourRangesParentEntity copy = getEm().find(HourRangesParentEntity.class, 1L);
        assertThat(copy).isNotNull();
        assertThat(copy.getId()).isEqualTo(1);
        assertThat(copy.getHourRanges()).isNotNull();
        assertThat(copy.getHourRanges().toString()).isEqualTo("00:00-24:00");
        commitTransaction();

    }

}
// CHECKSTYLE:ON
