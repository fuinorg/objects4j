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
package org.fuin.objects4j.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

//TESTCODE:BEGIN
public final class FontSizeTest {

    private static final float[] POINTS = new float[] { 6f, 7f, 7.5f, 8f, 9f,
            10f, 10.5f, 11f, 12f, 13f, 13.5f, 14f, 14.5f, 15f, 16f, 17f, 18f,
            20f, 22f, 24f, 26f, 27f, 28f, 29f, 30f };

    private static final int[] PIXELS = new int[] { 8, 9, 10, 11, 12, 13, 14,
            15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 26, 29, 32, 35, 36, 37, 38,
            40 };

    private static final float[] EMS = new float[] { 0.5f, 0.55f, 0.625f, 0.7f,
            0.75f, 0.8f, 0.875f, 0.95f, 1f, 1.05f, 1.125f, 1.2f, 1.25f, 1.3f,
            1.4f, 1.45f, 1.5f, 1.6f, 1.8f, 2f, 2.2f, 2.25f, 2.3f, 2.35f, 2.45f };

    private static final float[] PERCENT = new float[] { 50f, 55f, 62.5f, 70f,
            75f, 80f, 87.5f, 95f, 100f, 105f, 112.5f, 120f, 125f, 130f, 140f,
            145f, 150f, 160f, 180f, 200f, 220f, 225f, 230f, 235f, 245f };

    @Test
    public final void testCreate() {

        final FontSize testee = new FontSize(12.34f, FontSizeUnit.EM);
        assertThat(testee.getSize()).isEqualTo(12.34f, offset(0.01f));
        assertThat(testee.getUnit()).isEqualTo(FontSizeUnit.EM);

    }

    @Test
    public final void testFromPointsToXxx() {
        for (int i = 0; i < POINTS.length; i++) {
            final FontSize fontSize = new FontSize(POINTS[i],
                    FontSizeUnit.POINT);
            assertThat(fontSize.toPoint()).describedAs(
                    POINTS[i] + "pt => " + POINTS[i] + "pt").isEqualTo(
                    POINTS[i]);
            assertThat((float) fontSize.toPixel()).describedAs(
                    POINTS[i] + "pt => " + PIXELS[i] + "px").isEqualTo(
                    PIXELS[i], offset(1f));
            assertThat(fontSize.toEm()).describedAs(
                    POINTS[i] + "pt => " + EMS[i] + "em").isEqualTo(EMS[i],
                    offset(1f));
            assertThat(fontSize.toPercent()).describedAs(
                    POINTS[i] + "pt => " + PERCENT[i] + "%").isEqualTo(
                    PERCENT[i], offset(10f));
        }
    }

    @Test
    public final void testFromPixelsToXxx() {
        for (int i = 0; i < PIXELS.length; i++) {
            final FontSize fontSize = new FontSize(PIXELS[i],
                    FontSizeUnit.PIXEL);
            assertThat(fontSize.toPoint()).describedAs(
                    PIXELS[i] + "px => " + POINTS[i] + "pt").isEqualTo(
                    POINTS[i], offset(1f));
            assertThat(fontSize.toPixel()).describedAs(
                    PIXELS[i] + "px => " + PIXELS[i] + "px").isEqualTo(
                    PIXELS[i]);
            assertThat(fontSize.toEm()).describedAs(
                    PIXELS[i] + "px => " + EMS[i] + "em").isEqualTo(EMS[i],
                    offset(1f));
            assertThat(fontSize.toPercent()).describedAs(
                    PIXELS[i] + "px => " + PERCENT[i] + "%").isEqualTo(
                    PERCENT[i], offset(10f));
        }
    }

    @Test
    public final void testFromPercentToXxx() {
        for (int i = 0; i < PERCENT.length; i++) {
            final FontSize fontSize = new FontSize(PERCENT[i],
                    FontSizeUnit.PERCENT);
            assertThat(fontSize.toPoint()).describedAs(
                    PERCENT[i] + "% => " + POINTS[i] + "pt").isEqualTo(
                    POINTS[i], offset(1f));
            assertThat((float) fontSize.toPixel()).describedAs(
                    PERCENT[i] + "% => " + PIXELS[i] + "px").isEqualTo(
                    PIXELS[i], offset(1f));
            assertThat(fontSize.toEm()).describedAs(
                    PERCENT[i] + "% => " + EMS[i] + "em").isEqualTo(EMS[i],
                    offset(1f));
            assertThat(fontSize.toPercent()).describedAs(
                    PERCENT[i] + "% => " + PERCENT[i] + "%").isEqualTo(
                    PERCENT[i]);
        }
    }

    @Test
    public final void testFromEmsToXxx() {
        for (int i = 0; i < EMS.length; i++) {
            final FontSize fontSize = new FontSize(EMS[i], FontSizeUnit.EM);
            assertThat(fontSize.toPoint()).describedAs(
                    EMS[i] + "em => " + POINTS[i] + "pt").isEqualTo(POINTS[i],
                    offset(1f));
            assertThat((float) fontSize.toPixel()).describedAs(
                    EMS[i] + "em => " + PIXELS[i] + "px").isEqualTo(PIXELS[i],
                    offset(1f));
            assertThat(fontSize.toEm()).describedAs(
                    EMS[i] + "em => " + EMS[i] + "em").isEqualTo(EMS[i]);
            assertThat(fontSize.toPercent()).describedAs(
                    EMS[i] + "em => " + PERCENT[i] + "%").isEqualTo(PERCENT[i],
                    offset(10f));
        }
    }

    @Test
    public void testEqualsHashCode() throws Exception {
        EqualsVerifier.forClass(FontSize.class).verify();
    }

}
// TESTCODE:END
