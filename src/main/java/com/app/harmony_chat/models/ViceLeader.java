package com.app.harmony_chat.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "vice_leaders")
public class ViceLeader {
    @ManyToMany
    @Column(name = "room_id")
    private Room room;

    @ManyToMany
    @Column(name = "user_id")
    private User user;
}
