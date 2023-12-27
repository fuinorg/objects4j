/**
 * Copyright (C) 2015 Michael Schnell. All rights reserved. 
 * http://www.fuin.org/
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
 * along with this library. If not, see http://www.gnu.org/licenses/.
 */
package org.fuin.objects4j.ui;

import jakarta.validation.constraints.NotNull;
import org.fuin.objects4j.common.Contract;
import org.fuin.objects4j.common.Immutable;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Size of a font including the unit.
 */
@Immutable
public final class FontSize implements Serializable {

    private static final long serialVersionUID = -1572749922357083439L;

    private final float size;

    @NotNull
    private final FontSizeUnit unit;

    /**
     * Constructor with size and unit.
     * 
     * @param size
     *            Size of the font.
     * @param unit
     *            Unit of the size.
     */
    public FontSize(final float size, final FontSizeUnit unit) {
        super();
        this.size = size;
        this.unit = unit;
        Contract.requireValid(this);
    }

    /**
     * Returns the size of the font.
     * 
     * @return Size.
     */
    public final float getSize() {
        return size;
    }

    /**
     * Returns the unit of the size.
     * 
     * @return Unit.
     */
    public final FontSizeUnit getUnit() {
        return unit;
    }

    /**
     * Returns the font size expressed in pixels.
     * 
     * @return Pixels.
     */
    public final int toPixel() {
        if (unit == FontSizeUnit.PIXEL) {
            return Math.round(size);
        }
        if (unit == FontSizeUnit.EM) {
            return Math.round(16 * size);
        }
        if (unit == FontSizeUnit.PERCENT) {
            return Math.round(size / 100 * 16);
        }
        if (unit == FontSizeUnit.POINT) {
            return Math.round(size / 3 * 4);
        }
        throw new IllegalStateException("Unknown unit: " + unit);
    }

    /**
     * Returns the font size expressed in points.
     * 
     * @return Points.
     */
    public final float toPoint() {
        if (unit == FontSizeUnit.PIXEL) {
            return (size / 4 * 3);
        }
        if (unit == FontSizeUnit.EM) {
            return (size * 12);
        }
        if (unit == FontSizeUnit.PERCENT) {
            return (size / 100 * 12);
        }
        if (unit == FontSizeUnit.POINT) {
            return size;
        }
        throw new IllegalStateException("Unknown unit: " + unit);
    }

    /**
     * Returns the font size expressed in EM.
     * 
     * @return EM.
     */
    public final float toEm() {
        if (unit == FontSizeUnit.PIXEL) {
            return (size / 16);
        }
        if (unit == FontSizeUnit.EM) {
            return size;
        }
        if (unit == FontSizeUnit.PERCENT) {
            return (size / 100);
        }
        if (unit == FontSizeUnit.POINT) {
            // 1em = 12pt
            return size / 12;
        }
        throw new IllegalStateException("Unknown unit: " + unit);
    }

    /**
     * Returns the font size expressed in percent.
     * 
     * @return Percent.
     */
    public final float toPercent() {
        if (unit == FontSizeUnit.PIXEL) {
            return (size / 16 * 100 + 1f);
        }
        if (unit == FontSizeUnit.EM) {
            return (size * 100);
        }
        if (unit == FontSizeUnit.PERCENT) {
            return size;
        }
        if (unit == FontSizeUnit.POINT) {
            return (size / 12 * 100);
        }
        throw new IllegalStateException("Unknown unit: " + unit);
    }

    /**
     * Returns the size as pixel including the unit.
     * 
     * @return Size and unit (Example: "16px").
     */
    public final String toPixelStr() {
        return toPixel() + "px";
    }

    /**
     * Returns the size as points including the unit.
     * 
     * @return Size and unit (Example: "12pt").
     */
    public final String toPointStr() {
        return new DecimalFormat("%.1f").format(toPoint()) + "pt";
    }

    /**
     * Returns the size as EM including the unit.
     * 
     * @return Size and unit (Example: "1em").
     */
    public final String toEmStr() {
        return new DecimalFormat("%.3f").format(toEm()) + "em";
    }

    /**
     * Returns the size as percent including the unit.
     * 
     * @return Size and unit (Example: "100%").
     */
    public final String toPercentStr() {
        return new DecimalFormat("%.1f").format(toPercent()) + "%";
    }

    @Override
    public String toString() {
        if (unit == FontSizeUnit.PIXEL) {
            return toPixelStr();
        }
        if (unit == FontSizeUnit.EM) {
            return toEmStr();
        }
        if (unit == FontSizeUnit.PERCENT) {
            return toPercentStr();
        }
        if (unit == FontSizeUnit.POINT) {
            return toPointStr();
        }
        throw new IllegalStateException("Unknown unit: " + unit);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(size);
        result = prime * result + unit.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FontSize other = (FontSize) obj;
        if (Float.floatToIntBits(size) != Float.floatToIntBits(other.size)) {
            return false;
        }
        return unit.equals(other.unit);
    }

}
