package com.example.harmony_chat.model;

import java.time.LocalDate;


public class Relationship {
    private long id;

    private User user;
    private User friend;

    private LocalDate established;

    private String nickname;

    public Relationship(User user, User friend, LocalDate established) {
        this.user = user;
        this.friend = friend;
        this.established = established;
    }
    public Relationship() {
    }
    public Relationship(User user, User friend) {
        this.user = user;
        this.friend = friend;
    }

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

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public LocalDate getEstablished() {
        return established;
    }

    public void setEstablished(LocalDate established) {
        this.established = established;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
