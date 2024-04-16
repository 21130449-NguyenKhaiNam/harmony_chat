package com.app.harmony_chat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column
    private String id;

    @Column
    private String username;

    @Column
    private String password;

}
