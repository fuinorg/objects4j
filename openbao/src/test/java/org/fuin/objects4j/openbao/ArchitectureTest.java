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
package org.fuin.objects4j.openbao;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.fuin.objects4j.common.ConstraintViolationException;
import org.fuin.objects4j.crypto.EncryptedData;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.DependencyRules.NO_CLASSES_SHOULD_DEPEND_UPPER_PACKAGES;
import static org.fuin.units4j.archunit.Units4JConditions.ALL_CLASSES_SHOULD_HAVE_A_THREAD_SAFETY_ANNOTATION;

@AnalyzeClasses(packagesOf = ArchitectureTest.class, importOptions = ImportOption.DoNotIncludeTests.class)
class ArchitectureTest {

    private static final String COMMON_PACKAGE = ConstraintViolationException.class.getPackageName();

    private static final String CRYPTO_PACKAGE = EncryptedData.class.getPackageName();

    private static final String OPENBAO_PACKAGE = ArchitectureTest.class.getPackageName();

    @ArchTest
    static final ArchRule no_accesses_to_upper_package = NO_CLASSES_SHOULD_DEPEND_UPPER_PACKAGES;

    @ArchTest
    static final ArchRule all_classes_have_a_thread_safety_annotation = ALL_CLASSES_SHOULD_HAVE_A_THREAD_SAFETY_ANNOTATION;

    @ArchTest
    static final ArchRule openbao_access_only_to_defined_packages = classes()
            .that()
            .resideInAPackage(OPENBAO_PACKAGE)
            .should()
            .onlyDependOnClassesThat()
            .resideInAnyPackage(OPENBAO_PACKAGE, COMMON_PACKAGE, CRYPTO_PACKAGE,
                    "java.lang..", "java.util..", "java.io..", "java.net..", "java.nio..",
                    "jakarta.json..", "jakarta.validation..", "org.jspecify..");

}
