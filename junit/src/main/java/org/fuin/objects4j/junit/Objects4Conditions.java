package org.fuin.objects4j.junit;

import com.tngtech.archunit.PublicAPI;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.lang.ArchCondition;

import static com.tngtech.archunit.PublicAPI.Usage.ACCESS;

/**
 * Defines ArchUnit conditions for classes depending on this library.
 */
public final class Objects4Conditions {

    private Objects4Conditions() {
    }

    @PublicAPI(usage = ACCESS)
    public static ArchCondition<JavaClass> haveACorrespondingPublicStaticIsValidMethod() {
        return new HasPublicStaticIsValidMethodCondition();
    }

    @PublicAPI(usage = ACCESS)
    public static ArchCondition<JavaClass> haveACorrespondingPublicStaticValueOfMethod() {
        return new HasPublicStaticValueOfMethodCondition();
    }

}
