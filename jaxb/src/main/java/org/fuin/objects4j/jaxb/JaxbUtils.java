/**
 * Copyright (C) 2013 Future Invent Informationsmanagement GmbH. All rights
 * reserved. <http://www.fuin.org/>
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fuin.objects4j.jaxb;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for this package.
 */
public final class JaxbUtils {

    private static final List<XmlAdapter<?, ?>> ADAPTERS = List.of(
            new CurrencyAmountXmlAdapter(),
            new CurrencyXmlAdapter(),
            new DayOfTheWeekXmlAdapter(),
            new DayOpeningHoursXmlAdapter(),
            new EmailAddressXmlAdapter(),
            new HourXmlAdapter(),
            new HourRangeXmlAdapter(),
            new HourRangesXmlAdapter(),
            new LocaleXmlAdapter(),
            new MultiDayOfTheWeekXmlAdapter(),
            new PasswordSha512XmlAdapter(),
            new UserNameXmlAdapter(),
            new UUIDXmlAdapter(),
            new WeeklyOpeningHoursXmlAdapter()
    );

    private JaxbUtils() {
        throw new UnsupportedOperationException("Instances of utility classes are not allowed");
    }

    /**
     * Returns the list of {@link XmlAdapter} objects defined by the package.
     *
     * @return New instance of the adapter list.
     */
    public static List<XmlAdapter<?, ?>> getJaxbAdapters() {
        return new ArrayList<>(ADAPTERS);
    }

}