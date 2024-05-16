package com.example.harmony_chat.util;

import com.google.gson.Gson;

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
}
