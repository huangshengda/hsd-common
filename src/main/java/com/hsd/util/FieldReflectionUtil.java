package com.hsd.util;

import com.google.common.collect.Maps;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

/**
 * @author huangshengda
 * @date 2021/3/18
 */
public final class FieldReflectionUtil {

    private static final Map<FieldKey, Field> FIELD_MAP = Maps.newConcurrentMap();

    public static Object getFieldValue(Object obj, String fieldName) throws Exception {
        Field field = getField(obj, fieldName);
        return field.get(obj);
    }

    private static Field getField(Object obj, String fieldName) {
        FieldKey fieldKey = getFieldKey(obj, fieldName);
        Field field = FIELD_MAP.get(fieldKey);
        if (field != null) {
            return field;
        }

        field = buildField(obj.getClass(), fieldName);
        if (field == null) {
            throw new RuntimeException(String.format("fieldName=%s not exists in class=%s", fieldName, obj.getClass().getName()));
        }
        field.setAccessible(true);
        FIELD_MAP.putIfAbsent(fieldKey, field);
        return field;
    }

    private static Field buildField(Class<?> classType, String fieldName) {
        Class<?> currentClassType = classType;
        while (currentClassType != Object.class) {
            try {
                return currentClassType.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                // ignore
            }
            currentClassType = currentClassType.getSuperclass();
        }
        return null;
    }

    private static FieldKey getFieldKey(Object obj, String fieldName) {
        return new FieldKey(obj.getClass(), fieldName);
    }

    @Data
    private static class FieldKey {

        private Class<?> classType;

        private String fieldName;

        public FieldKey() {
        }

        public FieldKey(Class<?> classType, String fieldName) {
            this.classType = classType;
            this.fieldName = fieldName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            FieldKey fieldKey = (FieldKey) o;
            return Objects.equals(classType, fieldKey.classType) &&
                    Objects.equals(fieldName, fieldKey.fieldName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(classType, fieldName);
        }

    }

}
