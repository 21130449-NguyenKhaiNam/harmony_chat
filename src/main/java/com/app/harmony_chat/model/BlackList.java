package com.app.harmony_chat.model;

import com.google.api.client.util.Key;
import jakarta.persistence.*;

@Entity
@Table(name = "black_list")
public class BlackList {
    @Id
    private Long id;

    @Column(name = "user_id")
    @OneToMany
    private User user;

    @Column(name = "block_other")
    @ManyToMany
    private User blockUser;

    public BlackList() {}
}
