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

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Size;

//CHECKSTYLE:OFF
/**
 * The string has to be a password with a length between 8 and 20 characters.
 */
@Size(min = 8, max = 20)
@Target({ ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD,
        ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { PasswordStrValidator.class })
@ReportAsSingleViolation
@Documented
public @interface PasswordStr {

    String message() default "{org.fuin.objects4j.vo.PasswordStr.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
// CHECKSTYLE:ON
