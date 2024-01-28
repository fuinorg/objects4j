package org.fuin.objects4j.junit;

import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.fuin.objects4j.common.HasPublicStaticIsValidMethod;
import org.fuin.objects4j.common.HasPublicStaticIsValidMethods;
import org.fuin.objects4j.common.HasPublicStaticValueOfMethod;
import org.fuin.objects4j.common.HasPublicStaticValueOfMethods;
import org.fuin.objects4j.core.AsStringCapable;
import org.fuin.objects4j.core.Password;
import org.fuin.objects4j.core.ValueObjectWithBaseType;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static org.fuin.objects4j.junit.Objects4Conditions.haveACorrespondingPublicStaticIsValidMethod;
import static org.fuin.objects4j.junit.Objects4Conditions.haveACorrespondingPublicStaticValueOfMethod;

/**
 * Collection of architectural rules to be verified.
 * <p>
 * The tests are located in this module and not in the affected modules to avoid a circular dependency.
 */
@AnalyzeClasses(packagesOf = AsStringCapable.class, importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureBaseTest {

    @ArchTest
    static final ArchRule verify_public_static_is_valid_annotations =
            classes()
                    .that()
                    .doNotHaveModifier(JavaModifier.ABSTRACT)
                    .and()
                    .areAnnotatedWith(HasPublicStaticIsValidMethod.class)
                    .should(haveACorrespondingPublicStaticIsValidMethod());

    @ArchTest
    static final ArchRule value_object_classes_should_have_is_valid_method =
            classes()
                    .that()
                    .implement(ValueObjectWithBaseType.class)
                    .and()
                    .doNotHaveModifier(JavaModifier.ABSTRACT)
                    .and()
                    .doNotHaveFullyQualifiedName(Password.class.getName())
                    .should()
                    .beAnnotatedWith(HasPublicStaticIsValidMethod.class)
                    .orShould()
                    .beAnnotatedWith(HasPublicStaticIsValidMethods.class);

    @ArchTest
    static final ArchRule verify_public_static_value_of_annotations =
            classes()
                    .that()
                    .doNotHaveModifier(JavaModifier.ABSTRACT)
                    .and()
                    .areAnnotatedWith(HasPublicStaticValueOfMethod.class)
                    .should(haveACorrespondingPublicStaticValueOfMethod());

    @ArchTest
    static final ArchRule value_object_classes_should_have_value_of_method =
            classes()
                    .that()
                    .implement(ValueObjectWithBaseType.class)
                    .and()
                    .doNotHaveModifier(JavaModifier.ABSTRACT)
                    .and()
                    .doNotHaveFullyQualifiedName(Password.class.getName())
                    .should()
                    .beAnnotatedWith(HasPublicStaticValueOfMethod.class)
                    .orShould()
                    .beAnnotatedWith(HasPublicStaticValueOfMethods.class);

}
