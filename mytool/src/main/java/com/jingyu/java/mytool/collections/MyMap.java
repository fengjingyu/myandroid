package com.jingyu.java.mytool.collections;

import java.util.HashMap;

public class MyMap<K, V> extends HashMap<K, V> {

    public MyMap<K, V> myPut(K key, V value) {
        super.put(key, value);
        return this;
    }

}
