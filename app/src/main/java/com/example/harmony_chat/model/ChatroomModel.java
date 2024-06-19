package com.example.harmony_chat.model;

import com.google.firebase.Timestamp;

import java.util.List;

public class ChatroomModel {
    private  Room room;
    private List<String> userIds;
    private Timestamp lastMessageTimestamp;
    private String lastMessageSenderId;

    public ChatroomModel() {
    }

    public ChatroomModel(Room room, List<String> userIds, Timestamp lastMessageTimestamp, String lastMessageSenderId) {
        this.room = room;
        this.userIds = userIds;
        this.lastMessageTimestamp = lastMessageTimestamp;
        this.lastMessageSenderId = lastMessageSenderId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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
                "roomId='" + room + '\'' +
                ", userIds=" + userIds +
                ", lastMessageTimestamp=" + lastMessageTimestamp +
                ", lastMessageSenderId='" + lastMessageSenderId + '\'' +
                '}';
    }
}
