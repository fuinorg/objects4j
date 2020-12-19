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

import java.util.Locale;

import org.fuin.objects4j.common.ConstraintViolationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.validation.ConstraintValidatorContext;

//CHECKSTYLE:OFF
public final class LocaleStrValidatorTest {

    private static final ConstraintValidatorContext CONTEXT = null;

    private LocaleStrValidator testee;

    @Before
    public final void setUp() {
        testee = new LocaleStrValidator();
    }

    @After
    public final void tearDown() {
        testee = null;
    }

    @Test
    public final void testValid() {

        final Locale[] locales = Locale.getAvailableLocales();
        for (final Locale locale : locales) {
            final String value = locale.toString();
            // Skip locales like ja_JP_JP_#u-ca-japanese as we cannot handle it
            if (!value.contains("#") && !value.equals("")) {
                assertThat(testee.isValid(value, CONTEXT)).describedAs(value).isTrue();
                assertThat(LocaleStrValidator.parseArg("a", value)).describedAs(value).isEqualTo(locale);
            }
        }
    }

    @Test
    public final void testValidNullValue() {
        assertThat(testee.isValid(null, CONTEXT)).isTrue();
    }

    @Test
    public final void testInvalidEmpty() {

        assertThat(testee.isValid("", CONTEXT)).isFalse();
        try {
            LocaleStrValidator.parseArg("a", "");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'a' is not valid: ''");
        }

    }

    @Test
    public final void testInvalid() {

        assertThat(testee.isValid("d", CONTEXT)).isFalse();
        try {
            LocaleStrValidator.parseArg("a", "d");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'a' is not valid: 'd'");
        }

        assertThat(testee.isValid("xx_XX", CONTEXT)).isFalse();
        try {
            LocaleStrValidator.parseArg("a", "xx_XX");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'a' is not valid: 'xx_XX'");
        }

        assertThat(testee.isValid("xx_YY_zz", CONTEXT)).isFalse();
        try {
            LocaleStrValidator.parseArg("a", "xx_YY_zz");
            fail();
        } catch (final ConstraintViolationException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'a' is not valid: 'xx_YY_zz'");
        }

    }

}
// CHECKSTYLE:ON
