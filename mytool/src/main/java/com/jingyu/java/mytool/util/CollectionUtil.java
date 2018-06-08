package com.jingyu.java.mytool.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author fengjingyu@foxmail.com
 */
public class CollectionUtil {

    public static boolean isEmpty(List list) {
        if (list == null || list.isEmpty()) {
            return true;
        }

        return false;
    }

    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }

    public static boolean isEmpty(Map map) {
        if (map == null || map.size() == 0) {
            return true;
        }

        return false;
    }

    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
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
