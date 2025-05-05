package org.fuin.objects4j.common;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for the {@link TypeConstantValidator} class.
 */
class TypeConstantValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @AfterEach
    void tearDown() {
        validator = null;
    }

    @Test
    void isValidDefaultName() {
        assertThat(validator.validate(new MyClassDefaultName())).isEmpty();
    }

    @Test
    void isValidOtherName() {
        assertThat(validator.validate(new MyClassOtherName())).isEmpty();
    }

    @Test
    void isValidNonStatic() {
        assertThat(validator.validate(new MyClassNonStatic())).anyMatch(violation -> violation.getMessage().contains("#1"));
    }

    @Test
    void isValidNoField() {
        assertThat(validator.validate(new MyClassNoField())).anyMatch(violation -> violation.getMessage().contains("#2"));
    }

    @Test
    void isValidWrongType() {
        assertThat(validator.validate(new MyClassWrongType())).anyMatch(violation -> violation.getMessage().contains("#3"));
    }

    @Test
    void isValidNullValue() {
        assertThat(validator.validate(new MyClassNullValue())).anyMatch(violation -> violation.getMessage().contains("#4"));
        assertThat(validator.validate(new MyClassNonFinal())).anyMatch(violation -> violation.getMessage().contains("#5"));
    }

    @Test
    void isValidNonFinal() {
        assertThat(validator.validate(new MyClassNonFinal())).anyMatch(violation -> violation.getMessage().contains("#5"));
    }

    @Test
    void testExtractValueOtherNameOK() {
        final HasPublicStaticType annotation = MyClassOtherName.class.getAnnotation(HasPublicStaticType.class);
        final Object value = HasPublicStaticTypeValidator.extractValue(MyClassOtherName.class, annotation.value(), annotation.name());
        assertThat(value).isEqualTo(String.class);
    }

    @Test
    void testExtractValueDefaultNameOK() {
        final HasPublicStaticType annotation = MyClassDefaultName.class.getAnnotation(HasPublicStaticType.class);
        final Object value = HasPublicStaticTypeValidator.extractValue(MyClassDefaultName.class, annotation.value(), annotation.name());
        assertThat(value).isEqualTo(String.class);
    }

    @Test
    void testExtractValueErrorCase1() {
        final HasPublicStaticType anno1 = MyClassNonStatic.class.getAnnotation(HasPublicStaticType.class);
        assertThatThrownBy( () -> HasPublicStaticTypeValidator.extractValue(MyClassNonStatic.class, anno1.value(), anno1.name()))
                .hasMessageContaining("#1");
    }

    @Test
    void testExtractValueErrorCase2() {
        final HasPublicStaticType anno2 = MyClassNoField.class.getAnnotation(HasPublicStaticType.class);
        assertThatThrownBy( () -> HasPublicStaticTypeValidator.extractValue(MyClassNoField.class, anno2.value(), anno2.name()))
                .hasMessageContaining("#2");
    }

    @Test
    void testExtractValueErrorCase3() {
        final HasPublicStaticType anno3 = MyClassWrongType.class.getAnnotation(HasPublicStaticType.class);
        assertThatThrownBy( () -> HasPublicStaticTypeValidator.extractValue(MyClassWrongType.class, anno3.value(), anno3.name()))
                .hasMessageContaining("#3");
    }

    @Test
    void testExtractValueErrorCase4() {
        final HasPublicStaticType anno4 = MyClassNullValue.class.getAnnotation(HasPublicStaticType.class);
        assertThatThrownBy( () -> HasPublicStaticTypeValidator.extractValue(MyClassNullValue.class, anno4.value(), anno4.name()))
                .hasMessageContaining("#4");
    }

    @Test
    void testExtractValueErrorCase5() {
        final HasPublicStaticType anno5 = MyClassNonFinal.class.getAnnotation(HasPublicStaticType.class);
        assertThatThrownBy( () -> HasPublicStaticTypeValidator.extractValue(MyClassNonFinal.class, anno5.value(), anno5.name()))
                .hasMessageContaining("#5");
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = { HasPublicStaticTypeValidator.class })
    public @interface HasPublicStaticType {
        String name() default "TYPE";
        Class<?> value();
        String message() default "HasPublicStaticType validation failed";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    public static class HasPublicStaticTypeValidator extends TypeConstantValidator<HasPublicStaticType> {

        public HasPublicStaticTypeValidator() {
            super(HasPublicStaticType.class);
        }
    }

    @HasPublicStaticType(name ="X", value = Class.class)
    public static class MyClassOtherName {
        public static final Class<?> X = String.class;
    }

    @HasPublicStaticType(Class.class)
    public static class MyClassDefaultName {
        public static final Class<?> TYPE = String.class;
    }

    @HasPublicStaticType(Class.class)
    public static class MyClassNonFinal {
        public static Class<?> TYPE = String.class;
    }

    @HasPublicStaticType(Class.class)
    public static class MyClassNonStatic {
        public final Class<?> TYPE = String.class;
    }

    @HasPublicStaticType(Class.class)
    public static class MyClassNoField {
        public final static Class<?> FOO = String.class;
    }

    @HasPublicStaticType(Class.class)
    public static class MyClassWrongType {
        public static final String TYPE = "foo";
    }

    @HasPublicStaticType(String.class)
    public static class MyClassNullValue {
        public static final String TYPE = null;
    }

}