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

import com.tngtech.archunit.junit.ArchIgnore;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.fuin.utils4j.jaxb.MarshallerBuilder;
import org.fuin.utils4j.jaxb.UnmarshallerBuilder;

import java.io.StringWriter;
import java.util.List;

import static org.fuin.utils4j.jaxb.JaxbUtils.unmarshal;

/**
 * Utility class for this package.
 */
@ArchIgnore
class JaxbHelper {

    private static final List<XmlAdapter<?, ?>> ADAPTERS = List.of(
            new CurrencyAmountConverter(),
            new CurrencyConverter(),
            new DayOfTheWeekConverter(),
            new DayOpeningHoursConverter(),
            new EmailAddressConverter(),
            new HourConverter(),
            new HourRangeConverter(),
            new HourRangesConverter(),
            new LocaleConverter(),
            new MultiDayOfTheWeekConverter(),
            new PasswordConverter(),
            new PasswordSha512Converter(),
            new UserNameConverter(),
            new UUIDConverter(),
            new WeeklyOpeningHoursConverter(),
            new AnyStrConverter()
    );

    private JaxbHelper() {
        throw new UnsupportedOperationException("Instances of utility classes are not allowed");
    }


    public static Data unmarshalData(String xml) {
        final Unmarshaller unmarshaller = new UnmarshallerBuilder()
                .addClassesToBeBound(Data.class)
                .withHandler(event -> false)
                .addAdapters(ADAPTERS)
                .build();
        return unmarshal(unmarshaller, xml);
    }

    public static String marshalData(Data data) {
        final Marshaller marshaller = new MarshallerBuilder()
                .addClassesToBeBound(Data.class)
                .withHandler(event -> false)
                .addAdapters(ADAPTERS)
                .build();
        try {
            final StringWriter writer = new StringWriter();
            marshaller.marshal(data, writer);
            return writer.toString();
        } catch (final JAXBException ex) {
            throw new RuntimeException(ex);
        }
    }

}