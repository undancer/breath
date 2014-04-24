package com.undancer.breath.core.util;

import java.lang.reflect.Field;

/**
 * Created by undancer on 14-4-21.
 */
public class ReflectionUtils {

    public static <T> T getPrivateField(Object obj, String fieldName) {
        Field field = org.springframework.util.ReflectionUtils.findField(obj.getClass(), fieldName);
        if (field != null) {
            field.setAccessible(true);
            return (T) org.springframework.util.ReflectionUtils.getField(field, obj);
        }
        return null;
    }

}
