package com.hsd.jvm.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @date 2020/9/19
 */
public final class GenericUtil {

    public static Type[] getActualGenericClassesFromInterface(Class<?> classType) {
        return ((ParameterizedType) classType.getGenericInterfaces()[0]).getActualTypeArguments();
    }

    public static Class<?> getActualGenericClassesFromInterface(Class<?> classType, int index) {
        return (Class<?>) getActualGenericClassesFromInterface(classType)[index];
    }

    public static Type[] getActualGenericClassesFromParent(Class<?> classType) {
        return ((ParameterizedType) classType.getGenericSuperclass()).getActualTypeArguments();
    }

    public static Class<?> getActualGenericClassesFromParent(Class<?> classType, int index) {
        return (Class<?>) getActualGenericClassesFromParent(classType)[index];
    }

}
