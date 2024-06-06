package com.example.harmony_chat.service;

@FunctionalInterface
public interface CallBack {
    void callback(int code, String content);
}