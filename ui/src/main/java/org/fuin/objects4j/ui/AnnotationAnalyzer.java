/**
 * Copyright (C) 2015 Michael Schnell. All rights reserved. 
 * http://www.fuin.org/
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see http://www.gnu.org/licenses/.
 */
package org.fuin.objects4j.ui;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.fuin.objects4j.common.Contract;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Wrapper for a class that has some annotations to perform some actions with.
 */
public final class AnnotationAnalyzer {

    private static final Map<Class<?>, String> CLASS_NAME_MAP = new HashMap<Class<?>, String>();

    static {
        CLASS_NAME_MAP.put(String.class, String.class.getSimpleName());
        CLASS_NAME_MAP.put(Byte.class, Byte.class.getSimpleName());
        CLASS_NAME_MAP.put(Short.class, Short.class.getSimpleName());
        CLASS_NAME_MAP.put(Integer.class, Integer.class.getSimpleName());
        CLASS_NAME_MAP.put(Long.class, Long.class.getSimpleName());
        CLASS_NAME_MAP.put(Float.class, Float.class.getSimpleName());
        CLASS_NAME_MAP.put(String.class, String.class.getSimpleName());
        CLASS_NAME_MAP.put(Double.class, Double.class.getSimpleName());
        CLASS_NAME_MAP.put(Boolean.class, Boolean.class.getSimpleName());
    }

    /**
     * Returns the text information for a given class.
     * 
     * @param clasz
     *            Class.
     * @param locale
     *            Locale to use.
     * @param annotationClasz
     *            Type of annotation to find.
     * 
     * @return Label information - Never <code>null</code>.
     */
    public final ClassTextInfo createClassInfo(@NotNull final Class<?> clasz, @NotNull final Locale locale,
            @NotNull final Class<? extends Annotation> annotationClasz) {

        Contract.requireArgNotNull("clasz", clasz);
        Contract.requireArgNotNull("locale", locale);
        Contract.requireArgNotNull("annotationClasz", annotationClasz);

        final Annotation annotation = clasz.getAnnotation(annotationClasz);
        if (annotation == null) {
            return null;
        }

        try {
            final ResourceBundle bundle = getResourceBundle(annotation, locale, clasz);
            final String text = getText(bundle, annotation, clasz.getSimpleName() + "." + annotationClasz.getSimpleName());
            return new ClassTextInfo(clasz, text);
        } catch (final MissingResourceException ex) {
            if (getValue(annotation).equals("")) {
                return new ClassTextInfo(clasz, null);
            }
            return new ClassTextInfo(clasz, getValue(annotation));
        }

    }

    /**
     * Returns text annotation informations for all field of a class that are annotated with <code>annotationClasz</code>. All other fields
     * are ignored.
     * 
     * @param clasz
     *            Class that contains the fields.
     * @param locale
     *            Locale to use.
     * @param annotationClasz
     *            Type of annotation to find.
     * 
     * @return List of informations - Never <code>null</code>, but may be empty.
     */
    public final List<FieldTextInfo> createFieldInfos(@NotNull final Class<?> clasz, @NotNull final Locale locale,
            @NotNull final Class<? extends Annotation> annotationClasz) {

        Contract.requireArgNotNull("clasz", clasz);
        Contract.requireArgNotNull("locale", locale);
        Contract.requireArgNotNull("annotationClasz", annotationClasz);

        final List<FieldTextInfo> infos = new ArrayList<FieldTextInfo>();

        final Field[] fields = clasz.getDeclaredFields();
        for (final Field field : fields) {
            final Annotation annotation = field.getAnnotation(annotationClasz);
            if (annotation != null) {
                try {
                    final ResourceBundle bundle = getResourceBundle(annotation, locale, field.getDeclaringClass());
                    final String text = getText(bundle, annotation, field.getName() + "." + annotationClasz.getSimpleName());
                    infos.add(new FieldTextInfo(field, text));
                } catch (final MissingResourceException ex) {
                    final String text = toNullableString(getValue(annotation));
                    infos.add(new FieldTextInfo(field, text));
                }
            }
        }

        Class<?> parent = clasz;
        while ((parent = parent.getSuperclass()) != Object.class) {
            infos.addAll(createFieldInfos(parent, locale, annotationClasz));
        }

        return infos;

    }

    /**
     * Returns the text information for a given field of a class.
     * 
     * @param field
     *            Field to inspect.
     * @param locale
     *            Locale to use.
     * @param annotationClasz
     *            Type of annotation to find.
     * 
     * @return Label information - May be <code>null</code> in case the annotation was not found.
     */
    public final FieldTextInfo createFieldInfo(@NotNull final Field field, @NotNull final Locale locale,
            @NotNull final Class<? extends Annotation> annotationClasz) {

        Contract.requireArgNotNull("field", field);
        Contract.requireArgNotNull("locale", locale);
        Contract.requireArgNotNull("annotationClasz", annotationClasz);

        final Annotation annotation = field.getAnnotation(annotationClasz);
        if (annotation == null) {
            return null;
        }

        try {
            final ResourceBundle bundle = getResourceBundle(annotation, locale, field.getDeclaringClass());
            final String text = getText(bundle, annotation, field.getName() + "." + annotationClasz.getSimpleName());
            return new FieldTextInfo(field, text);
        } catch (final MissingResourceException ex) {
            return new FieldTextInfo(field, toNullableString(getValue(annotation)));
        }

    }

    /**
     * Returns the text for the annotation. If no entry is found in the resource bundle for <code>Annotation#key()</code> then
     * <code>Annotation#value()</code> will be returned instead. If <code>Annotation#value()</code> is also empty then <code>null</code> is
     * returned. If <code>Annotation#key()</code> is empty <code>defaultKey</code> will be used as key in the properties file.
     * 
     * @param bundle
     *            Resource bundle to use.
     * @param annotation
     *            Annotation wrapper for the field.
     * @param defaultKey
     *            Default key if <code>Annotation#key()</code> is empty.
     * 
     * @return Text or <code>null</code>.
     */
    private String getText(@NotNull final ResourceBundle bundle, @NotNull final Annotation annotation, @NotNull final String defaultKey) {

        Contract.requireArgNotNull("bundle", bundle);
        Contract.requireArgNotNull("annotation", annotation);
        Contract.requireArgNotNull("defaultKey", defaultKey);

        final String key;
        if (getKey(annotation).equals("")) {
            key = defaultKey;
        } else {
            key = getKey(annotation);
        }

        try {
            return bundle.getString(key);
        } catch (final MissingResourceException ex) {
            return toNullableString(getValue(annotation));
        }

    }

    /**
     * Returns the resource bundle for a given annotation. If <code>Annotation#bundle()</code> is empty the <code>clasz</code> is used to
     * create a path and filename information.
     * 
     * @param annotation
     *            Annotation with bundle name.
     * @param locale
     *            Locale to use.
     * @param clasz
     *            Class to use if the <code>Annotation#bundle()</code> is empty. Example: <code>a.b.c.MyClass</code> is used as
     *            <code>a/b/c/MyClass.properties</code> (default) or <code>a/b/c/MyClass_en.properties</code> (with {@link Locale#ENGLISH}).
     * 
     * @return Resource bundle - Never <code>null</code>.
     */
    private ResourceBundle getResourceBundle(@NotNull final Annotation annotation, @NotNull final Locale locale,
            @NotNull final Class<?> clasz) {

        if (getBundle(annotation).equals("")) {
            final String path = clasz.getPackage().getName().replace('.', '/');
            final String baseName = path + "/" + clasz.getSimpleName();
            return ResourceBundle.getBundle(baseName, locale);
        }
        return ResourceBundle.getBundle(getBundle(annotation), locale);

    }

    /**
     * Returns <code>null</code> if the argument is an empty string.
     * 
     * @param value
     *            Argument to check.
     * 
     * @return Argument or <code>null</code>.
     */
    private final String toNullableString(final String value) {
        if (value.equals("")) {
            return null;
        }
        return value;
    }

    private String getBundle(final Annotation annotation) {
        return invoke(annotation, "bundle");
    }

    private String getValue(final Annotation annotation) {
        return invoke(annotation, "value");
    }

    private String getKey(final Annotation annotation) {
        return invoke(annotation, "key");
    }

    /**
     * Calls a method with no arguments using reflection and maps all errors into a runtime exception.
     * 
     * @param obj
     *            The object the underlying method is invoked from - Cannot be <code>null</code>.
     * @param methodName
     *            Name of the Method - Cannot be <code>null</code>.
     * 
     * @return The result of dispatching the method represented by this object on <code>obj</code> with parameters <code>args</code>.
     * 
     * @param <T>
     *            Return type.
     */
    @SuppressWarnings("unchecked")
    private static <T> T invoke(final Object obj, final String methodName) {
        return (T) invoke(obj, methodName, null, null);
    }

    /**
     * Calls a method with reflection and maps all errors into a runtime exception.
     * 
     * @param obj
     *            The object the underlying method is invoked from - Cannot be <code>null</code>.
     * @param methodName
     *            Name of the Method - Cannot be <code>null</code> or empty.
     * @param argTypes
     *            The list of parameters - May be <code>null</code>.
     * @param args
     *            Arguments the arguments used for the method call - May be <code>null</code> if "argTypes" is also <code>null</code>.
     * 
     * @return The result of dispatching the method represented by this object on <code>obj</code> with parameters <code>args</code>.
     */
    private static Object invoke(@NotNull final Object obj, @NotEmpty final String methodName, @Nullable final Class<?>[] argTypes,
            @Nullable final Object[] args) {

        Contract.requireArgNotNull("obj", obj);
        Contract.requireArgNotNull("methodName", methodName);

        final Class<?>[] argTypesIntern;
        final Object[] argsIntern;
        if (argTypes == null) {
            argTypesIntern = new Class[] {};
            if (args != null) {
                throw new IllegalArgumentException("The argument 'argTypes' is null but " + "'args' containes values!");
            }
            argsIntern = new Object[] {};
        } else {
            argTypesIntern = argTypes;
            if (args == null) {
                throw new IllegalArgumentException("The argument 'argTypes' contains classes " + "but 'args' is null!");
            }
            argsIntern = args;
        }
        checkSameLength(argTypesIntern, argsIntern);

        Class<?> returnType = UNKNOWN_CLASS.class;
        try {
            final Method method = obj.getClass().getMethod(methodName, argTypesIntern);
            if (method.getReturnType() == null) {
                returnType = void.class;
            } else {
                returnType = method.getReturnType();
            }
            return method.invoke(obj, argsIntern);
        } catch (final SecurityException ex) {
            throw new RuntimeException("Security problem with '" + getMethodSignature(returnType, methodName, argTypesIntern) + "'! ["
                    + obj.getClass().getName() + "]", ex);
        } catch (final NoSuchMethodException ex) {
            throw new RuntimeException("Method '" + getMethodSignature(returnType, methodName, argTypesIntern) + "' not found! ["
                    + obj.getClass().getName() + "]", ex);
        } catch (final IllegalArgumentException ex) {
            throw new RuntimeException("Argument problem with '" + getMethodSignature(returnType, methodName, argTypesIntern) + "'! ["
                    + obj.getClass().getName() + "]", ex);
        } catch (final IllegalAccessException ex) {
            throw new RuntimeException("Access problem with '" + getMethodSignature(returnType, methodName, argTypesIntern) + "'! ["
                    + obj.getClass().getName() + "]", ex);
        } catch (final InvocationTargetException ex) {
            throw new RuntimeException("Got an exception when calling '" + getMethodSignature(returnType, methodName, argTypesIntern)
                    + "'! [" + obj.getClass().getName() + "]", ex);
        }

    }

    private static void checkSameLength(final Class<?>[] argTypes, final Object[] args) {
        if (argTypes.length != args.length) {
            throw new IllegalArgumentException("The argument 'argTypes' contains " + argTypes.length + " classes "
                    + "but 'args' only contains " + args.length + " arguments!");
        }
    }

    /**
     * Creates a textual representation of the method.
     * 
     * @param returnType
     *            Return type of the method - Cannot be <code>null</code>.
     * @param methodName
     *            Name of the method - Cannot be <code>null</code>.
     * @param argTypes
     *            The list of parameters - Can be <code>null</code>.
     * 
     * @return Textual signature of the method.
     */
    public static String getMethodSignature(@NotNull final Class<?> returnType, @NotNull final String methodName,
            final Class<?>[] argTypes) {
        final StringBuilder sb = new StringBuilder();
        sb.append(name(returnType));
        sb.append(" ");
        sb.append(methodName);
        sb.append("(");
        if (argTypes != null) {
            for (int i = 0; i < argTypes.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(name(argTypes[i]));
            }
        }
        sb.append(")");
        return sb.toString();
    }

    private static String name(@NotNull final Class<?> clasz) {
        final String name = CLASS_NAME_MAP.get(clasz);
        if (name == null) {
            return clasz.getName();
        }
        return name;
    }

    private static final class UNKNOWN_CLASS {

    }

}
