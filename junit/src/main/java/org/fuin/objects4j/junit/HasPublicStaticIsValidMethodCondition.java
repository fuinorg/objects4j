package org.fuin.objects4j.junit;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.fuin.objects4j.common.HasPublicStaticIsValidMethod;
import org.fuin.objects4j.common.HasPublicStaticIsValidMethodValidator;
import org.fuin.objects4j.common.HasPublicStaticIsValidMethods;

import java.util.Optional;

import static com.tngtech.archunit.lang.ConditionEvent.createMessage;

public final class HasPublicStaticIsValidMethodCondition extends ArchCondition<JavaClass> {

    private final HasPublicStaticIsValidMethodValidator validator;

    public HasPublicStaticIsValidMethodCondition() {
        super("have a public static 'is valid' method with the signature defined by the annotation");
        validator = new HasPublicStaticIsValidMethodValidator();
    }

    @Override
    public void check(JavaClass clazz, ConditionEvents events) {
        if (clazz.isAnnotatedWith(HasPublicStaticIsValidMethods.class)) {
            final HasPublicStaticIsValidMethods annotations = clazz.getAnnotationOfType(HasPublicStaticIsValidMethods.class);
            for (final HasPublicStaticIsValidMethod annotation : annotations.value()) {
                verify(clazz, annotation, events);
            }
        } else if (clazz.isAnnotatedWith(HasPublicStaticIsValidMethod.class)) {
            verify(clazz, clazz.getAnnotationOfType(HasPublicStaticIsValidMethod.class), events);
        }
    }

    private static void verify(JavaClass clazz, HasPublicStaticIsValidMethod annotation, ConditionEvents events) {
        final boolean satisfied;
        final Optional<JavaMethod> optionalMethod = clazz.tryGetMethod(annotation.method(), annotation.param());
        if (optionalMethod.isEmpty()) {
            satisfied = false;
        } else {
            final JavaMethod method = optionalMethod.get();
            if (!method.getModifiers().contains(JavaModifier.STATIC)) {
                satisfied = false;
            } else {
                satisfied = method.getReturnType().getName().equals(boolean.class.getName());
            }
        }
        final String message = createMessage(clazz, "has " + (satisfied ? "a" : "no")
                + " method: public static boolean " + annotation.method() + "(" + annotation.param().getSimpleName() + ")");
        events.add(new SimpleConditionEvent(clazz, satisfied, message));
    }

}
