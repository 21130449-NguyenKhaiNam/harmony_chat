package com.example.harmony_chat.model;

public class BlackList {
    private long id;
    private User user;
    private User blockUser;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getBlockUser() {
        return blockUser;
    }

    public void setBlockUser(User blockUser) {
        this.blockUser = blockUser;
    }
}
