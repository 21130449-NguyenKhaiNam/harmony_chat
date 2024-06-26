package com.example.harmony_chat.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Information {
    private int code;
    private String json;

    public int getCode() {
        return code;
    }

    public String getJson() {
        return json;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setJson(String json) {
        this.json = json;
    }

    @NonNull
    @Override
    public String toString() {
        return "Information: {\n" +
                "code: " + code + "," +
                "content: " + json + "\n" +
                "}";
    }
}
