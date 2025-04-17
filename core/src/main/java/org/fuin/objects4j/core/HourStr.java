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
package org.fuin.objects4j.core;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//CHECKSTYLE:OFF
/**
 * Verifies the string is an hour of a day (24 hours, sometimes called Military Time). Valid examples are:
 * <ul>
 * <li>'00:00' Midnight next/new day</li>
 * <li>'01:00' One hour after midnight</li>
 * <li>'11:30' Half hour before noon</li>
 * <li>'12:00' Noon</li>
 * <li>'13:00' One hour after noon</li>
 * <li>'23:59' One minute before midnight</li>
 * <li>'24:00' Midnight previous/current day</li>
 * </ul>
 */
@Target({ ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { HourStrValidator.class })
@Documented
public @interface HourStr {

    String message() default "{org.fuin.objects4j.core.HourStr.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
// CHECKSTYLE:ON
