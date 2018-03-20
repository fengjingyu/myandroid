package com.jingyu.android.common.sp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;


/**
 * @author fengjingyu@foxmail.com
 */
public class SPHelper {

    private SharedPreferences sharedPreferences;

    /**
     * @param application
     * @param fileName    不要加 ".xml"的后缀名
     *                    Context.Mode 下有四种模式常量(仅推荐private模式)
     */
    public SPHelper(Application application, String fileName) {
        sharedPreferences = application.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void putFloat(String key, float value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void putStringSet(String key, Set<String> values) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, values);
        editor.apply();
    }

    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

    public Set<String> getStringSet(String key, Set<String> defValues) {
        return sharedPreferences.getStringSet(key, defValues);
    }

    //-------------------------------------------------------------------------------------

    public void spPut(String key, boolean value) {
        putBoolean(key, value);
    }

    public void spPut(String key, int value) {
        putInt(key, value);
    }

    public void spPut(String key, long value) {
        putLong(key, value);
    }

    public void spPut(String key, float value) {
        putFloat(key, value);
    }

    public void spPut(String key, String value) {
        putString(key, value);
    }

    public void spPut(String key, Set<String> set) {
        putStringSet(key, set);
    }

    public String spGet(String key, String default_value) {
        return getString(key, default_value);
    }

    public int spGet(String key, int default_value) {
        return getInt(key, default_value);
    }

    public long spGet(String key, long default_value) {
        return getLong(key, default_value);
    }

    public boolean spGet(String key, boolean default_value) {
        return getBoolean(key, default_value);
    }

    public float spGet(String key, float default_value) {
        return getFloat(key, default_value);
    }

    public Set<String> get(String key, Set<String> default_value) {
        return getStringSet(key, default_value);
    }

}