package com.example.harmony_chat;

public class ChatItem {
    private String title;
    private String message;
    private String avatarUrl;
    private String time;

    public ChatItem(String title, String message, String avatarUrl, String time) {
        this.title = title;
        this.message = message;
        this.avatarUrl = avatarUrl;
        this.time = time;
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
}
