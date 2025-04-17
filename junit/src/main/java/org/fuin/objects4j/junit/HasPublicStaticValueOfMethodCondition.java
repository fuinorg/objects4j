package org.fuin.objects4j.junit;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.fuin.objects4j.common.HasPublicStaticValueOfMethod;
import org.fuin.objects4j.common.HasPublicStaticValueOfMethods;

import java.util.Optional;

import static com.tngtech.archunit.lang.ConditionEvent.createMessage;

/**
 * Verifies if a class has a public static "valueOf" method that matches the conditions defined
 * with the annotation {@link HasPublicStaticValueOfMethods} attached to that class.
 */
public final class HasPublicStaticValueOfMethodCondition extends ArchCondition<JavaClass> {

    public HasPublicStaticValueOfMethodCondition() {
        super("have a public static 'value of' method with the signature defined by the annotation");
    }

    @Override
    public void check(JavaClass clazz, ConditionEvents events) {
        if (clazz.isAnnotatedWith(HasPublicStaticValueOfMethods.class)) {
            final HasPublicStaticValueOfMethods annotations = clazz.getAnnotationOfType(HasPublicStaticValueOfMethods.class);
            for (final HasPublicStaticValueOfMethod annotation : annotations.value()) {
                verify(clazz, annotation, events);
            }
        } else if (clazz.isAnnotatedWith(HasPublicStaticValueOfMethod.class)) {
            verify(clazz, clazz.getAnnotationOfType(HasPublicStaticValueOfMethod.class), events);
        }
    }

    private static void verify(JavaClass clazz, HasPublicStaticValueOfMethod annotation, ConditionEvents events) {
        final boolean satisfied;
        final Optional<JavaMethod> optionalMethod = clazz.tryGetMethod(annotation.method(), annotation.param());
        if (optionalMethod.isEmpty()) {
            satisfied = false;
        } else {
            final JavaMethod method = optionalMethod.get();
            if (!method.getModifiers().contains(JavaModifier.STATIC)) {
                satisfied = false;
            } else {
                satisfied = method.getReturnType().getName().equals(clazz.getName());
            }
        }
        final String message = createMessage(clazz, "has " + (satisfied ? "a" : "no")
                + " method: public static " + clazz.getSimpleName() + " " + annotation.method() + "(" + annotation.param().getSimpleName() + ")");
        events.add(new SimpleConditionEvent(clazz, satisfied, message));
    }

}
