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
package org.fuin.objects4j.vo;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

//CHECKSTYLE:OFF
/**
 * Verifies if this field contains multiple ranges of hours of a day (24 hour representation) separated by a '+'.<br>
 * Example: '09:00-12:00+13:00-17:00' From 9 am to 12 noon and from 1 pm to 5 pm
 */
@Target({ ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { MultiDayOfTheWeekStrValidator.class })
@Documented
public @interface MultiDayOfTheWeekStr {

    String message() default "{org.fuin.objects4j.vo.MultiDayOfTheWeekStr.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
// CHECKSTYLE:ON
