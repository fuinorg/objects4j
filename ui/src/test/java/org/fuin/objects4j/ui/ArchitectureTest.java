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
package org.fuin.objects4j.ui;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.DependencyRules;
import org.fuin.objects4j.common.ConstraintViolationException;

@AnalyzeClasses(packagesOf = ArchitectureTest.class, importOptions = ImportOption.DoNotIncludeTests.class)
class ArchitectureTest {

    private static final String COMMON_PACKAGE = ConstraintViolationException.class.getPackageName();

    private static final String UI_PACKAGE = AnnotationAnalyzer.class.getPackageName();

    @ArchTest
    static final ArchRule no_accesses_to_upper_package = DependencyRules.NO_CLASSES_SHOULD_DEPEND_UPPER_PACKAGES;

    @ArchTest
    static final ArchRule ui_access_only_to_defined_packages = ArchRuleDefinition.classes()
            .that()
            .resideInAPackage(UI_PACKAGE)
            .should()
            .onlyDependOnClassesThat()
            .resideInAnyPackage(COMMON_PACKAGE, UI_PACKAGE,
                    "java.lang..", "java.util..", "java.io..", "java.text..",
                    "jakarta.validation..", "jakarta.annotation..", "jakarta.enterprise.inject..",
                    "javax.annotation.concurrent");

}

