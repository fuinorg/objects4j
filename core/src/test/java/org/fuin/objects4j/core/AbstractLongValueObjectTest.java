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

//CHECKSTYLE:OFF
public class AbstractLongValueObjectTest {

    @Test
    void testEqualsHashCode() {
        EqualsVerifier.forClass(TestLongVO.class).withRedefinedSuperclass()
                .withPrefabValues(AbstractLongValueObject.class, new TestLongVO(1L), new TestLongVO(2L)).suppress(Warning.NULL_FIELDS)
                .verify();

    }

    /** Implementation for tests. */
    public static final class TestLongVO extends AbstractLongValueObject {

        @Serial
        private static final long serialVersionUID = 1L;

        private final Long value;

        public TestLongVO(final Long value) {
            super();
            this.value = value;
        }

        @Override
        public Long asBaseType() {
            return value;
        }

    }

}
// CHECKSTYLE:ON
