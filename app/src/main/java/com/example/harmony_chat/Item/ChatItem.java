package com.example.harmony_chat.Item;

import com.example.harmony_chat.model.Room;
import com.example.harmony_chat.model.User;

import java.io.Serializable;

public class ChatItem{
    private String title;
    private String message;
    private String avatarUrl;
    private String time;
    private String otherUserId;
    private Room room;
    private User leader, deputy;

    public ChatItem(String title, String message, String avatarUrl, String time) {
        this.title = title;
        this.message = message;
        this.avatarUrl = avatarUrl;
        this.time = time;
    }

    public ChatItem(String title, String message, String avatarUrl, String time, String otherUserId) {
        this.title = title;
        this.message = message;
        this.avatarUrl = avatarUrl;
        this.time = time;
        this.otherUserId = otherUserId;
    }

    public ChatItem(String title, String message, String avatarUrl, String time, String otherUserId, Room room, User leader, User deputy) {
        this.title = title;
        this.message = message;
        this.avatarUrl = avatarUrl;
        this.time = time;
        this.otherUserId = otherUserId;
        this.room = room;
        this.leader = leader;
        this.deputy = deputy;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(String otherUserId) {
        this.otherUserId = otherUserId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public User getDeputy() {
        return deputy;
    }

    public void setDeputy(User deputy) {
        this.deputy = deputy;
    }
}
