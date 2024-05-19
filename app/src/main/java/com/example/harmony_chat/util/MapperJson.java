package com.example.harmony_chat.util;

import com.google.gson.Gson;
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
        return gson.fromJson(json, type);
    }

    // Chuyển đổi json về danh sách
    public <T> List<T> convertListObjFromJson(String json, Class<T> clazz) {
        Type type = TypeToken.getParameterized(List.class, clazz).getType();
        return gson.fromJson(json, type);
    }
}
