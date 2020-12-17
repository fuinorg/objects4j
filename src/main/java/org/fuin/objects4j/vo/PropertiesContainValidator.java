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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Check that a properties object contains a given number of expected keys.
 */
public final class PropertiesContainValidator implements ConstraintValidator<PropertiesContain, Properties> {

    private Set<String> expectedKeys;

    @Override
    public final void initialize(final PropertiesContain constraintAnnotation) {
        expectedKeys = new HashSet<String>();
        expectedKeys.addAll(Arrays.asList(constraintAnnotation.value()));
    }

    @Override
    public final boolean isValid(final Properties props, final ConstraintValidatorContext context) {
        if (props == null) {
            return true;
        }
        for (final String key : expectedKeys) {
            if (!props.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

}
