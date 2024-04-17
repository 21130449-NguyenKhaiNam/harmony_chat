package com.app.harmony_chat.models;

import jakarta.persistence.*;

@Entity
@Table(name = "menbers")
public class Member {
    @Id
    private int id;

    @OneToOne
    @Column(name = "room_id")
    private Room roomId;

    @OneToOne
    @Column(name = "user_id")
    private User user;
}
