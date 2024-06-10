package com.app.harmony_chat.models;

import com.app.harmony_chat.configs.DefinePropertyJson;
import com.app.harmony_chat.configs.DefineTableDatabase;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = DefineTableDatabase.USER)
public class User {
    @Id
    private String id;
    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String id) {
        this.id = id;
    }

}
