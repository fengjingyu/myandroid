package com.jingyu.java.mytool.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author fengjingyu@foxmail.com
 *
 */
public class CollectionsUtil {

    /**
     * 判断list是不是空的
     */
    public static boolean isListBlank(List list) {

        if (list == null || list.isEmpty()) {
            return true;
        }

        return false;
    }

    /**
     * 判断list是否可用
     */
    public static boolean isListAvaliable(List list) {

        if (list == null || list.isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     * 判断一个Map集合是否为空
     *
     * @param map
     * @return true为是空
     */
    public static boolean isMapBlank(Map map) {

        if (map == null || map.size() == 0) {
            return true;
        }

        return false;
    }

    /**
     * 判断一个Map集合是否为空
     *
     * @param map
     * @return true为是空
     */
    public static boolean isMapAvaliable(Map map) {

        if (map == null || map.size() == 0) {
            return false;
        }

        return true;
    }

    public static List getList(Object... args) {
        List list = new LinkedList();

        if (args != null && args.length > 0) {
            for (Object arg : args) {
                list.add(arg);
            }
        }
        return list;
    }

}
