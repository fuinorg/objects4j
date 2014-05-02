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
package org.fuin.objects4j.common;

import static org.fest.assertions.Assertions.assertThat;

import org.joda.time.LocalDateTime;
import org.junit.Test;

//TESTCODE:BEGIN
public final class LocalDateTimeAdapterTest {

    @Test
    public final void testMarshalUnmarshal() {

    	// PREPARE
    	final LocalDateTimeAdapter testee = new LocalDateTimeAdapter();
    	final LocalDateTime original = new LocalDateTime(); 
    	
    	// TEST
    	final String str = testee.marshal(original);
    	final LocalDateTime copy = testee.unmarshal(str); 
    	
    	// VERIFY
        assertThat(copy).isEqualTo(original);

    }


}
// TESTCODE:END