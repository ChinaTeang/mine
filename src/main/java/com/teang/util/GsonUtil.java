package com.teang.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GsonUtil {
    /**
     * Gson解析成某个类
     */
    public static <T> T geDataByJson(String json, Class<T> clazz) {
        try {
            T obj = null;
            Gson gson = new Gson();
            obj = gson.fromJson(json, clazz);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Gson解析成某个类
     */
    public static Object getObjectByJson(String json, Class<?> clazz) {
        try {
            Object obj = null;
            Gson gson = new Gson();
            obj = gson.fromJson(json, clazz);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 转为json字符串
     */
    public static String objectToJson(Object object) {
        String json = "";
        Gson gson = new Gson();
        try {
            json = gson.toJson(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


    /**
     * json转为ArrayList集合
     */
    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz) {

        ArrayList<T> arrayList = new ArrayList<>();
        if (TextUtils.isEmpty(json)) {
            return arrayList;
        }
        try {
            Type type = new TypeToken<ArrayList<JsonObject>>() {
            }.getType();
            ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);
            for (JsonObject jsonObject : jsonObjects) {

                try {
                    arrayList.add(new Gson().fromJson(jsonObject, clazz));
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayList;
    }

    /**
     * json转为ArrayList集合
     */
    public static <K, V> Map<K, V> jsonToHashMap(String json, Class<K> kClass, Class<V> vClass) {
        Map<K, V> map = new HashMap<>();
        if (TextUtils.isEmpty(json)) {// 如果为空字符串就直接返回
            return map;
        }
        try {
            Type type = new TypeToken<HashMap<K, V>>() {
            }.getType();
            HashMap<K, V> hashMap = new Gson().fromJson(json, type);
            for (Map.Entry<K, V> kvEntry : hashMap.entrySet()) {
                map.put(kvEntry.getKey(), kvEntry.getValue());
            }

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * json转为ArrayList集合
     */
    public static <K> K jsonToType(String json, Class<K> kClass) {
        K val = null;
        try {
            Type type = new TypeToken<K>() {
            }.getType();
            val = new Gson().fromJson(json, type);

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return val;
    }
}
