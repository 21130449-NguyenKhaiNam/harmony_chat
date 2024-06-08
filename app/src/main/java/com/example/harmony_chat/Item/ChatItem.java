package com.example.harmony_chat.Item;

public class ChatItem {
    private String title;
    private String message;
    private String avatarUrl;
    private String time;
    private String otherUserId;

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
}
