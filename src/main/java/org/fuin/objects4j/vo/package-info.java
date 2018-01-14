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

/**
 * Contains immutable object that represents an object whose equality isn't
 * based on identity. That means instances of this type are equal when they have
 * the same value, not necessarily being the same object. Additionally some
 * helper classes are placed in this package.
 */
@Vetoed
@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(value = UUIDConverter.class, type = UUID.class),
        @XmlJavaTypeAdapter(value = LocaleConverter.class, type = Locale.class) })
package org.fuin.objects4j.vo;

import java.util.Locale;
import java.util.UUID;

import javax.enterprise.inject.Vetoed;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
