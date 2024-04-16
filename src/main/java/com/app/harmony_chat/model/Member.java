package com.app.harmony_chat.model;

import jakarta.persistence.*;

@Entity
@Table(name = "menbers")
public class Member {
    @Id
    private int id;

    @ManyToMany
    @Column(name = "room_id")
    private Room roomId;

    @ManyToMany
    @Column(name = "user_id")
    private User user;
}
