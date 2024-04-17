package com.app.harmony_chat.models;

import jakarta.persistence.*;

@Entity
@Table(name = "black_list")
public class BlackList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_id")
    @OneToOne
    private User user;

    @Column(name = "block_other")
    @OneToOne
    private User blockUser;

    public BlackList() {}
}
