package com.app.harmony_chat.models;

import jakarta.persistence.*;

@Entity
@Table(name = "block_list")
public class BlockList {
    @Id
    @Column
    private int id;

    @OneToOne
    @Column(name = "room_id")
    private Room room;

    @OneToOne
    @Column(name = "user_id")
    private User user;
}
