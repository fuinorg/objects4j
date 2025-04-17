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

import org.junit.jupiter.api.Test;

import java.io.Serial;

import static org.assertj.core.api.Assertions.assertThat;

public final class UniquelyNumberedExceptionTest {

    @Test
    void testCreateNumberMessage() {

        // PREPARE
        final String message = "A very important error message";
        final long number = 1L;

        // TEST
        final MyException testee = new MyException(number, message);

        // VERIFY
        assertThat(testee.getUniqueNumber()).isEqualTo(number);

    }

    @Test
    void testCreateNumberCause() {

        // PREPARE
        final Exception cause = new NullPointerException();
        final long number = 1L;

        // TEST
        final MyException testee = new MyException(number, cause);

        // VERIFY
        assertThat(testee.getUniqueNumber()).isEqualTo(number);

    }

    @Test
    void testCreateNumberMessageCause() {

        // PREPARE
        final String message = "A very important error message";
        final Exception cause = new NullPointerException();
        final long number = 1L;

        // TEST
        final MyException testee = new MyException(number, message, cause);

        // VERIFY
        assertThat(testee.getUniqueNumber()).isEqualTo(number);

    }

    public static final class MyException extends UniquelyNumberedException {

        @Serial
        private static final long serialVersionUID = 1L;

        public MyException(long number, String message, Throwable cause) {
            super(number, message, cause);
        }

        public MyException(long number, String message) {
            super(number, message);
        }

        public MyException(long number, Throwable cause) {
            super(number, cause);
        }

    }

}
