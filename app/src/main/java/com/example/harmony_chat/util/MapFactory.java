package com.example.harmony_chat.util;

import android.graphics.Point;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MapFactory {

    public static String[] createArrayString(String... params) {
        if(CheckInfomation.isEmpty(params)) {
            return null;
        }
        return params;
    }
    public static Map<String, String> createMapJson(String[] keys, String[] values) {
        if(CheckInfomation.isEmpty(keys, values) || keys.length != values.length) {
            return null;
        }
        Map<String, String> json = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            json.put(keys[i], values[i]);
        }
        return json;
    }
}
