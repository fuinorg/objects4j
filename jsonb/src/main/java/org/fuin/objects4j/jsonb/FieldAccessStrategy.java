package org.fuin.objects4j.jsonb;

import jakarta.json.bind.config.PropertyVisibilityStrategy;
import org.fuin.utils4j.TestOmitted;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Makes all fields accessible and methods inaccessible.
 */
@TestOmitted("Nothing to test")
public final class FieldAccessStrategy implements PropertyVisibilityStrategy {

    @Override
    public boolean isVisible(Field field) {
        return true;
    }

    @Override
    public boolean isVisible(Method method) {
        return false;
    }

}
