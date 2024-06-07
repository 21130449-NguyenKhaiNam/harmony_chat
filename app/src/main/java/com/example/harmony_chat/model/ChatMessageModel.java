package com.example.harmony_chat.model;

import java.sql.Timestamp;

public class ChatMessageModel {
    private String message, senderId;
    private Timestamp timestamp;

    public ChatMessageModel() {
    }

    public ChatMessageModel(String message, String senderId, Timestamp timestamp) {
        this.message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ChatMessageModel{" +
                "message='" + message + '\'' +
                ", senderId='" + senderId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
