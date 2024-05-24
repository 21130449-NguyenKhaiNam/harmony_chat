package com.example.harmony_chat.service;

import com.example.harmony_chat.util.MapperJson;

public class DataResponsive {
    private int code;
    private Object content;

    public DataResponsive() {}

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return MapperJson.getInstance().convertObjToJson(content);
    }

    public void setContent(Object content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "DataResponsive{" +
                "code=" + code +
                ", content=" + content +
                '}';
    }
}
