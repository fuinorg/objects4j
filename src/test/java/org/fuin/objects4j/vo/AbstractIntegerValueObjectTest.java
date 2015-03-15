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

import junitx.extensions.EqualsHashCodeTestCase;

//CHECKSTYLE:OFF
public class AbstractIntegerValueObjectTest extends EqualsHashCodeTestCase {

    public AbstractIntegerValueObjectTest(String name) {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception {
        return new TestIntegerVO(1);
    }

    @Override
    protected Object createNotEqualInstance() throws Exception {
        return new TestIntegerVO(2);
    }
    
    /** Implementation for tests. */
    public static class TestIntegerVO extends AbstractIntegerValueObject {

        private static final long serialVersionUID = 1L;
        
        private final Integer value;
        
        public TestIntegerVO(final Integer value) {
            super();
            this.value = value;
        }
        
        @Override
        public Integer asBaseType() {
            return value;
        }
        
    }

}
//CHECKSTYLE:ON
