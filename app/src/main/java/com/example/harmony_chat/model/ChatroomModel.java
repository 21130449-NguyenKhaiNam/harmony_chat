package com.example.harmony_chat.model;

import java.sql.Timestamp;
import java.util.List;

public class ChatroomModel {
    private  String roomId;
    private List<String> userIds;
    private Timestamp lastMessageTimestamp;
    private String lastMessageSenderId;

    public ChatroomModel() {
    }

    public ChatroomModel(String roomId, List<String> userIds, Timestamp lastMessageTimestamp, String lastMessageSenderId) {
        this.roomId = roomId;
        this.userIds = userIds;
        this.lastMessageTimestamp = lastMessageTimestamp;
        this.lastMessageSenderId = lastMessageSenderId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public Timestamp getLastMessageTimestamp() {
        return lastMessageTimestamp;
    }

    public void setLastMessageTimestamp(Timestamp lastMessageTimestamp) {
        this.lastMessageTimestamp = lastMessageTimestamp;
    }

    public String getLastMessageSenderId() {
        return lastMessageSenderId;
    }

    public void setLastMessageSenderId(String lastMessageSenderId) {
        this.lastMessageSenderId = lastMessageSenderId;
    }

    @Override
    public String toString() {
        return "ChatroomModel{" +
                "roomId='" + roomId + '\'' +
                ", userIds=" + userIds +
                ", lastMessageTimestamp=" + lastMessageTimestamp +
                ", lastMessageSenderId='" + lastMessageSenderId + '\'' +
                '}';
    }
}
