package com.jingyu.java.mytool.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @author fengjingyu@foxmail.com
 *
 */
public class ReflectUtil {

    /**
     * 创建一个空的对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
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
                        field.set(resultModel, getValueByName(originModel, field.getName()));
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

    /**
     * 获取字段。包括父类的。
     *
     * @return
     */
    public static <T> Field[] getFields(T target) {
        try {
            Class cls = target.getClass();
            Field[] fields = cls.getDeclaredFields();
            return fields;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static <T> String[] getPropertyNames(T target) {
        try {

            Field[] fields = getFields(target);

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

    public static <T> Object[] getValues(T target) {
        try {
            Field[] fields = getFields(target);

            int length = fields.length;

            Object[] values = new Object[length];

            for (int i = 0; i < length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                Object obj = field.get(target);

                values[i] = obj;
            }

            return values;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static <T> Object getValueByName(T target, String name) {

        try {
            Field field = target.getClass().getDeclaredField(name);
            field.setAccessible(true);
            Object obj = field.get(target);
            return obj;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
