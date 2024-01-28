package org.fuin.objects4j.core;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for the {@link LocaleHelper} class.
 */
class LocaleHelperTest {

    @Test
    void testAsLocale() {
        assertThat(LocaleHelper.asLocale(null)).isNull();
        assertThat(LocaleHelper.asLocale("en")).isEqualTo(new Locale("en"));
        assertThat(LocaleHelper.asLocale("en_US")).isEqualTo(new Locale("en", "US"));
        assertThat(LocaleHelper.asLocale("en_US_NY")).isEqualTo(new Locale("en", "US", "NY"));
    }

    @Test
    void testValidLocale() {
        assertThat(LocaleHelper.validLocale(new Locale("xx"))).isFalse();
        assertThat(LocaleHelper.validLocale(Locale.getAvailableLocales()[0])).isTrue();
    }

}
