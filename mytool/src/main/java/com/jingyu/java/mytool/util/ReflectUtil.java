package com.jingyu.java.mytool.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @author fengjingyu@foxmail.com
 */
public class ReflectUtil {

    public static Object getValueByFieldName(Object obj, String name) throws Exception {
        Field field = obj.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return field.get(obj);
    }

    public static void setValueByFieldName(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        if (field.isAccessible()) {
            field.set(obj, value);
        } else {
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(false);
        }
    }

    private static <T> T getInstance(Class<T> clazz) {
        try {
            Constructor<T> con = clazz.getConstructor();
            return con.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T copy(T originModel, T resultModel) {
        try {
            if (resultModel == null || originModel == null) {
                return null;
            }

            List<String> originFieldStrings = Arrays.asList(getPropertyNames(originModel));

            Field[] resultFields = getFields(resultModel);

            for (Field field : resultFields) {
                if (originFieldStrings.contains(field.getName())) {
                    try {
                        field.setAccessible(true);
                        field.set(resultModel, getValueByFieldName(originModel, field.getName()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            return resultModel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Field[] getFields(Object obj) {
        try {
            Class cls = obj.getClass();
            Field[] fields = cls.getDeclaredFields();
            return fields;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String[] getPropertyNames(Object object) {
        try {
            Field[] fields = getFields(object);

            String[] result = new String[fields.length];

            for (int i = 0; i < fields.length; i++) {
                result[i] = fields[i].getName();
            }

            return result;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Object[] getPropertyValues(Object obj) {
        try {
            Field[] fields = getFields(obj);

            int length = fields.length;

            Object[] values = new Object[length];

            for (int i = 0; i < length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                values[i] = field.get(obj);
            }

            return values;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
