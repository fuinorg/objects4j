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
package org.fuin.objects4j.core;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.io.Serial;
import java.util.UUID;

//CHECKSTYLE:OFF
public class AbstractUuidValueObjectTest {

    @Test
    void testEqualsHashCode() {
        EqualsVerifier.forClass(TestStringVO.class).withRedefinedSuperclass()
                .withPrefabValues(AbstractUuidValueObject.class, new TestStringVO(UUID.randomUUID()), new TestStringVO(UUID.randomUUID()))
                .suppress(Warning.NULL_FIELDS).verify();

    }

    /** Implementation for tests. */
    public static final class TestStringVO extends AbstractUuidValueObject {

        @Serial
        private static final long serialVersionUID = 1L;

        private final UUID value;

        public TestStringVO(final UUID value) {
            super();
            this.value = value;
        }

        @Override
        public UUID asBaseType() {
            return value;
        }

    }

}
// CHECKSTYLE:ON
