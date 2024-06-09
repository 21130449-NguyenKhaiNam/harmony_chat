package com.example.harmony_chat.model;


import com.google.firebase.Timestamp;

public class ChatMessageModel {
    private String message, roomId, senderId;
    private Timestamp timestamp;

    public ChatMessageModel() {
    }

    public ChatMessageModel(String message, String roomId, String senderId, Timestamp timestamp) {
        this.message = message;
        this.roomId = roomId;
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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "ChatMessageModel{" +
                "message='" + message + '\'' +
                ", roomId='" + roomId + '\'' +
                ", senderId='" + senderId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
