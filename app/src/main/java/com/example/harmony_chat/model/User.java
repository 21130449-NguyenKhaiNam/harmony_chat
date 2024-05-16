package com.example.harmony_chat.model;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private UUID id;
    private String email;
    private String password;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String id) {
        this.id = UUID.fromString(id);
    }

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }
}
