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
public class HourRangeTest extends AbstractPersistenceTest {

    public final void testEqualsHashCode() {
        EqualsVerifier.forClass(HourRange.class).verify();
    }

    @Test
    public final void testConstruct() {

        assertThat(new HourRange("13:00-14:00")).isEqualTo(new HourRange("13:00-14:00"));
        assertThat(new HourRange(new Hour("13:00"), new Hour("14:00"))).isEqualTo(new HourRange("13:00-14:00"));

        try {
            new HourRange("13-14:00");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'hourRange' does not represent a valid hour range like '00:00-24:00' or '06:00-21:00': '13-14:00'");
        }

        try {
            new HourRange(null, new Hour("12:00"));
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'from' cannot be null");
        }
        
        try {
            new HourRange(new Hour("12:00"), null);
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'to' cannot be null");
        }
        
    }
    
    @Test
    public final void testOverlaps() {

        assertThat(new HourRange("12:00-18:00").overlaps(new HourRange("12:00-18:00"))).isTrue();
        
        assertThat(new HourRange("12:00-18:00").overlaps(new HourRange("11:00-11:59"))).isFalse();
        assertThat(new HourRange("11:00-11:59").overlaps(new HourRange("12:00-18:00"))).isFalse();
        
        assertThat(new HourRange("12:00-18:00").overlaps(new HourRange("11:59-12:00"))).isTrue();
        assertThat(new HourRange("11:59-12:00").overlaps(new HourRange("12:00-18:00"))).isTrue();
        
        assertThat(new HourRange("12:00-18:00").overlaps(new HourRange("13:00-17:00"))).isTrue();
        assertThat(new HourRange("13:00-17:00").overlaps(new HourRange("12:00-18:00"))).isTrue();
        
        assertThat(new HourRange("12:00-18:00").overlaps(new HourRange("18:00-18:01"))).isTrue();
        assertThat(new HourRange("18:00-18:01").overlaps(new HourRange("12:00-18:00"))).isTrue();
        
        assertThat(new HourRange("12:00-18:00").overlaps(new HourRange("18:01-18:02"))).isFalse();
        assertThat(new HourRange("18:01-18:02").overlaps(new HourRange("12:00-18:00"))).isFalse();
        
    }

    @Test
    public final void testNormalize() {
        
        assertThat(new HourRange("18:00-03:00").normalize()).containsExactly(new HourRange("18:00-24:00"), new HourRange("00:00-03:00"));
        assertThat(new HourRange("09:00-06:00").normalize()).containsExactly(new HourRange("09:00-24:00"), new HourRange("00:00-06:00"));
        assertThat(new HourRange("18:00-19:00").normalize()).containsExactly(new HourRange("18:00-19:00"));
        assertThat(new HourRange("00:00-24:00").normalize()).containsExactly(new HourRange("00:00-24:00"));
        
    }
    

    @Test
    public final void testIsValidTRUE() {

        assertThat(HourRange.isValid(null)).isTrue();
        assertThat(HourRange.isValid("00:00-24:00")).isTrue();
        assertThat(HourRange.isValid("00:01-23:59")).isTrue();
        assertThat(HourRange.isValid("01:00-02:00")).isTrue();
        assertThat(HourRange.isValid("00:00-06:00")).isTrue();
        assertThat(HourRange.isValid("12:00-18:00")).isTrue();
        assertThat(HourRange.isValid("18:00-24:00")).isTrue();
        assertThat(HourRange.isValid("18:00-03:00")).isTrue();

    }

    @Test
    public final void testIsValidFALSE() {

        assertThat(HourRange.isValid("")).isFalse();
        assertThat(HourRange.isValid("-")).isFalse();
        assertThat(HourRange.isValid("12:00-")).isFalse();
        assertThat(HourRange.isValid("-12:00")).isFalse();
        assertThat(HourRange.isValid("12.00-13.00")).isFalse();
        assertThat(HourRange.isValid("12-13")).isFalse();
        assertThat(HourRange.isValid("12:-13:")).isFalse();
        assertThat(HourRange.isValid("12:0")).isFalse();
        assertThat(HourRange.isValid("12:00-13:000")).isFalse();
        assertThat(HourRange.isValid("12:00-13")).isFalse();
        assertThat(HourRange.isValid("24:00-24:00")).isFalse();
        assertThat(HourRange.isValid("24:00-01:00")).isFalse();
        assertThat(HourRange.isValid("17:00-17:00")).isFalse();
        assertThat(HourRange.isValid("01:00-00:00")).isFalse();

    }
    
    @Test
    public final void testValueOf() {
        assertThat(HourRange.valueOf(null)).isNull();
        assertThat(HourRange.valueOf("00:00-24:00")).isEqualTo(new HourRange("00:00-24:00"));
    }

    @Test
    public final void testRequireArgValid() {

        try {
            HourRange.requireArgValid("a", "");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'a' does not represent a valid hour range like '00:00-24:00' or '06:00-21:00': ''");
        }

        try {
            HourRange.requireArgValid("b", "17-18");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("The argument 'b' does not represent a valid hour range like '00:00-24:00' or '06:00-21:00': '17-18'");
        }

    }

    @Test
    public void testJPA() {

        // PREPARE
        beginTransaction();
        getEm().persist(new HourRangeParentEntity(1));
        commitTransaction();

        // TEST UPDATE
        beginTransaction();
        final HourRangeParentEntity entity = getEm().find(HourRangeParentEntity.class, 1L);
        entity.setHourRange(new HourRange("00:00-24:00"));
        commitTransaction();

        // VERIFY
        beginTransaction();
        final HourRangeParentEntity copy = getEm().find(HourRangeParentEntity.class, 1L);
        assertThat(copy).isNotNull();
        assertThat(copy.getId()).isEqualTo(1);
        assertThat(copy.getHourRange()).isNotNull();
        assertThat(copy.getHourRange().toString()).isEqualTo("00:00-24:00");
        commitTransaction();

    }

}
// CHECKSTYLE:ON
