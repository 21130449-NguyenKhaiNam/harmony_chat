package com.example.harmony_chat.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MapperJson {
    private Gson gson;
    private static MapperJson mapper;

    private MapperJson() {
        gson = new Gson();
    }

    public static MapperJson getInstance() {
        return mapper == null ? (mapper = new MapperJson()) : mapper;
    }

    public Gson createBuilder(String formatDate) {
        return gson.newBuilder()
                .setDateFormat(formatDate)
                .create();
    }

    // Chuyển một json về đối tượng
    public <T> T convertObjFromJson(String json, Class<T> type) {
        T obj = null;
        try {
            obj = gson.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            Log.e("MapperJson", "Failed to convert JSON to object: " + e.getMessage());
        }
        return obj;
    }

    // Chuyển đổi json về danh sách
    public <T> List<T> convertListObjFromJson(String json, Class<T> clazz) {
        Type type = TypeToken.getParameterized(List.class, clazz).getType();
        return gson.fromJson(json, type);
    }

    // Chuyển một đối tượng về json
    public String convertObjToJson(Object obj) {
        return gson.toJson(obj);
    }
}
