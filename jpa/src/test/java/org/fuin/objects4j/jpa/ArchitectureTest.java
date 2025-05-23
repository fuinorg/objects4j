/**
 * Copyright (C) 2013 Future Invent Informationsmanagement GmbH. All rights
 * reserved. <http://www.fuin.org/>
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fuin.objects4j.jpa;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.core.AbstractIntegerValueObject;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.DependencyRules.NO_CLASSES_SHOULD_DEPEND_UPPER_PACKAGES;

@AnalyzeClasses(packagesOf = ArchitectureTest.class, importOptions = ImportOption.DoNotIncludeTests.class)
class ArchitectureTest {

    private static final String COMMON_PACKAGE = ConstraintViolationException.class.getPackageName();

    private static final String CORE_PACKAGE = AbstractIntegerValueObject.class.getPackageName();

    private static final String JPA_PACKAGE = CurrencyAttributeConverter.class.getPackageName();

    @ArchTest
    static final ArchRule no_accesses_to_upper_package = NO_CLASSES_SHOULD_DEPEND_UPPER_PACKAGES;

    @ArchTest
    static final ArchRule accesses_only_defined_packages = classes()
            .that()
            .resideInAPackage(JPA_PACKAGE)
            .should()
            .onlyDependOnClassesThat()
            .resideInAnyPackage(JPA_PACKAGE, COMMON_PACKAGE, CORE_PACKAGE,
                    "java.lang..", "java.util..",
                    "jakarta.validation..", "jakarta.annotation..", "jakarta.persistence..",
                    "javax.annotation.concurrent");


}

