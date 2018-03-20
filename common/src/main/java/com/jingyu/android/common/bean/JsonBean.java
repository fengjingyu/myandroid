package com.jingyu.android.common.bean;

import com.jingyu.java.mytool.bean.CloneBean;
import com.jingyu.java.mytool.util.StringUtil;
import org.json.JSONObject;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author fengjingyu@foxmail.com
 *
 * int类型的字段可以getString获取String
 * String类型的字段可以getInt获取int
 */
public class JsonBean extends CloneBean {

    private HashMap<String, Object> paraMap = new HashMap<String, Object>();

    public Boolean getBoolean(String name) {
        return getBoolean(name, false);
    }

    public Boolean getBoolean(String name, boolean default_value) {
        Object value = paraMap.get(name.toLowerCase());

        if (value == null || value.equals(JSONObject.NULL)) {
            return default_value;
        } else if (value instanceof Boolean) {
            return (Boolean) value;
        } else {
            return default_value;
        }
    }

    public String getString(String name) {
        return getString(name, "");
    }

    public String getString(String name, String default_value) {

        Object value = paraMap.get(name.toLowerCase());

        if (value == null || value.equals(JSONObject.NULL)) {
            return default_value;
        }

        return value + "";
    }

    public Integer getInt(String name) {
        return getInt(name, 0);
    }

    public Integer getInt(String name, int default_value) {
        Object value = paraMap.get(name.toLowerCase());
        if (value == null || value.equals(JSONObject.NULL)) {
            return default_value;
        }
        try {
            if (value instanceof Integer) {
                return (Integer) value;
            } else if (value instanceof String) {
                return Integer.parseInt((String) value);
            } else {
                return default_value;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return default_value;
        }
    }

    public Long getLong(String name) {
        return getLong(name, 0);
    }

    public Long getLong(String name, long default_value) {
        Object value = paraMap.get(name.toLowerCase());
        if (value == null || value.equals(JSONObject.NULL)) {
            return default_value;
        }
        try {
            if (value instanceof Long) {
                return (Long) value;
            } else if (value instanceof Integer) {
                return (long) ((int) value);
            } else if (value instanceof String) {
                return Long.parseLong((String) value);
            } else {
                return default_value;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return default_value;
        }
    }

    public Double getDouble(String name) {
        return getDouble(name, 0);
    }

    public Double getDouble(String name, double default_value) {
        Object value = paraMap.get(name.toLowerCase());
        if (value == null || value.equals(JSONObject.NULL)) {
            return default_value;
        }
        try {
            if (value instanceof Double) {
                return (Double) value;
            } else if (value instanceof String) {
                return Double.parseDouble((String) value);
            } else {
                return default_value;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return default_value;
        }
    }

    public <T extends JsonBean> T instanceModel() {
        try {
            Constructor constructor = this.getClass().getConstructor();
            Object o = constructor.newInstance();
            return (T) o;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T extends JsonBean> T getModel(String name, T default_value) {
        Object value = paraMap.get(name.toLowerCase());
        if (value == null || value.equals(JSONObject.NULL)) {
            return default_value;
        }
        return (T) value;
    }

    public <T extends JsonBean> T getModel(String name) {
        return (T) getModel(name, instanceModel());
    }

    public <T extends JsonBean> List<T> getList(String name, List<T> defualt_value) {
        Object value = paraMap.get(name.toLowerCase());
        if (value == null || value.equals(JSONObject.NULL)) {
            return defualt_value;
        }
        return (List<T>) value;
    }

    public <T extends JsonBean> List<T> getList(String name) {
        return getList(name, new ArrayList<T>());
    }

    public List<String> getStringList(String name, List<String> default_value) {
        Object value = paraMap.get(name.toLowerCase());
        if (value == null || value.equals(JSONObject.NULL)) {
            return default_value;
        }
        return (List<String>) value;
    }

    public List<ArrayList> getListList(String name, List<ArrayList> default_value) {
        Object value = paraMap.get(name.toLowerCase());
        if (value == null || value.equals(JSONObject.NULL)) {
            return default_value;
        }
        return (List<ArrayList>) value;
    }

    public void add(String name, Object value) {
        if (StringUtil.isBlank(name))
            return;
        paraMap.put(name.toLowerCase(), value);
    }

    public void remove(String name) {
        paraMap.remove(name.toLowerCase());
    }

    public Iterator<String> keys() {
        return paraMap.keySet().iterator();
    }

    public void clear() {
        paraMap.clear();
    }

    @Override
    public String toString() {
        return paraMap.toString();
    }
}
