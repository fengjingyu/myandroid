package com.jingyu.android.common.bean;

import com.jingyu.android.common.log.Logger;
import com.jingyu.java.mytool.util.StringUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author fengjingyu@foxmail.com
 */
public class JsonParse {

    /**
     * { } 解析json对象
     *
     * 注：解析失败一定得返回null
     */
    public static <T extends JsonBean> T getJsonParseData(String json, Class<T> beanClass) {
        try {
            if (!StringUtil.isBlank(json)) {
                T result = getBean(beanClass);
                JSONObject obj = new JSONObject(json);
                return parse(result, obj, beanClass);
            }
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JsonBean getJsonParseData(String json) {
        return getJsonParseData(json, JsonBean.class);
    }

    /**
     * [ ] 解析json数组
     */
    public static <T extends JsonBean> List<T> getJsonListParseData(String json, Class<T> beanClass) {
        try {
            List<T> list = new ArrayList<T>();
            JSONArray array = new JSONArray(json);
            int size = array.length();
            for (int i = 0; i < size; i++) {
                JSONObject json_object = array.getJSONObject(i);
                if (json_object != null) {
                    T bean = getJsonParseData(json_object.toString(), beanClass);
                    if (bean == null) {
                        // 表示有解析失败的json
                        return null;
                    }
                    list.add(bean);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * [ ] 解析json数组
     */
    public static List<JsonBean> getJsonListParseData(String json) {
        return getJsonListParseData(json, JsonBean.class);
    }

    /**
     * 创建一个JsonBean对象
     */
    private static <T extends JsonBean> T getBean(Class<T> beanClass) {
        try {
            Constructor<T> con = beanClass.getConstructor();
            return con.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析
     */
    private static <T extends JsonBean> T parse(T result, JSONObject obj, Class<T> beanClass) {
        try {
            Object o, object;
            JSONArray array;
            Iterator it = obj.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                o = obj.get(key);
                if (o instanceof JSONObject) {
                    T bean = getBean(beanClass);
                    parse(bean, (JSONObject) o, beanClass);
                    result.add(key, bean);
                } else if (o instanceof JSONArray) {
                    array = (JSONArray) o;
                    List list = new ArrayList();
                    result.add(key, list);
                    for (int i = 0; i < array.length(); i++) {
                        object = array.get(i);
                        if (object instanceof JSONObject) {
                            T bean = getBean(beanClass);
                            parse(bean, (JSONObject) object, beanClass);
                            list.add(bean);
                        } else {
                            list.add(object);
                        }
                    }
                } else {
                    result.add(key, o);
                }
            }
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final String TAB = "\t\t";
    public static final String RETURN = "\n";

    /**
     * 格式化json数据,方便打印到控制台
     */
    public static String format(String jsonString) {

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            boolean isInQuotationMark = false;
            String currentTab = "";

            for (int i = 0; i < jsonString.length(); i++) {

                String c = jsonString.substring(i, i + 1);

                switch (c) {

                    case "\"": {
                        if (i > 0) {
                            if (jsonString.charAt(i - 1) != '\\') {
                                isInQuotationMark = !isInQuotationMark;
                            }
                            sb.append(c);
                        }
                        break;
                    }

                    case "[":
                    case "{": {
                        if (!isInQuotationMark) {
                            currentTab += TAB;
                            sb.append(c);
                            sb.append(RETURN + currentTab);
                        } else {
                            sb.append(c);
                        }
                        break;
                    }

                    case "]":
                    case "}": {
                        if (!isInQuotationMark) {
                            currentTab = currentTab.substring(TAB.length());
                            sb.append(RETURN + currentTab);
                            sb.append(c);
                            sb.append(RETURN);
                        } else {
                            sb.append(c);
                        }

                        break;
                    }
                    case ",": {

                        if (!isInQuotationMark) {
                            sb.append(c);
                            sb.append(RETURN + currentTab);
                        } else {
                            sb.append(c);
                        }

                        break;
                    }
                    default: {
                        sb.append(c);
                    }
                }
            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("格式化json异常----jsonformat---" + jsonString, e);
            return jsonString;
        }
    }

}
