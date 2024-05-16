package com.example.harmony_chat.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public Relationship(User user, User friend) {
        this.user = user;
        this.friend = friend;
    }
}
