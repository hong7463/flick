package com.haisenhong.flicker.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hison7463 on 10/14/16.
 */

public class ObjectHolder {

    Map<String, Object> dataHolder;

    private ObjectHolder() {
        dataHolder = new HashMap<String, Object>();
    }

    private static class ObjectHolderInner {
        public static ObjectHolder INSTANCE = new ObjectHolder();
    }

    public static ObjectHolder getInstance() {
        return ObjectHolderInner.INSTANCE;
    }

    public <T extends Object> void put(String key, T object) {
        dataHolder.put(key, object);
    }

    public <T extends Object> T retrieve(String key) {
        return (T) dataHolder.get(key);
    }


}
