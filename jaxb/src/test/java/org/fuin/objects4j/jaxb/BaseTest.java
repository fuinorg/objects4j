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
package org.fuin.objects4j.jaxb;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchIgnore;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import java.util.Collection;
import java.util.Set;

import static com.tngtech.archunit.lang.ConditionEvent.createMessage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

@AnalyzeClasses(packagesOf = CurrencyAmountXmlAdapter.class)
class BaseTest {

    @ArchTest
    static final ArchRule all_classes_should_have_tests =
            classes()
                    .that()
                    .areTopLevelClasses()
                    .and().areNotInterfaces()
                    .and().areNotRecords()
                    .and().areNotEnums()
                    .and().doNotHaveModifier(JavaModifier.ABSTRACT)
                    .and().areNotAnnotatedWith(ArchIgnore.class)
                    .should(haveACorrespondingClassEndingWithTest());

    private static ArchCondition<JavaClass> haveACorrespondingClassEndingWithTest() {
        return new AllTopLevelClassesHaveATestCondition("Test");
    }

    private static class AllTopLevelClassesHaveATestCondition extends ArchCondition<JavaClass> {

        private final String suffix;

        private Set<String> testedClassNames;

        public AllTopLevelClassesHaveATestCondition(final String suffix) {
            super("have a corresponding test class with suffix '" + suffix + "'");
            this.suffix = suffix;
            this.testedClassNames = emptySet();
        }

        @Override
        public void init(Collection<JavaClass> allClasses) {
            testedClassNames = allClasses.stream()
                    .map(JavaClass::getName)
                    .filter(className -> className.endsWith(suffix))
                    .map(className -> className.substring(0, className.length() - suffix.length()))
                    .collect(toSet());
        }

        @Override
        public void check(JavaClass clazz, ConditionEvents events) {
            if (!clazz.getName().endsWith(suffix)) {
                boolean satisfied = testedClassNames.contains(clazz.getName());
                String message = createMessage(clazz, "has " + (satisfied ? "a" : "no") + " corresponding test class");
                events.add(new SimpleConditionEvent(clazz, satisfied, message));
            }
        }

    }


}

