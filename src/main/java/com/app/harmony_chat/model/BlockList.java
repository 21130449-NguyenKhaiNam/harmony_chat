package com.app.harmony_chat.model;

import jakarta.persistence.*;

@Entity
@Table(name = "block_list")
public class BlockList {
    @Id
    @Column
    private int id;

    @ManyToMany
    @Column(name = "room_id")
    private Room room;

    @ManyToMany
    @Column(name = "user_id")
    private User user;
}
