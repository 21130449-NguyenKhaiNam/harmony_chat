package com.app.harmony_chat.models;

import com.app.harmony_chat.configs.DefineTableDatabase;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = DefineTableDatabase.RELATIONSHIP)
public class Relationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id", referencedColumnName = "id")
    private User friend;

    @Temporal(TemporalType.DATE)
    private LocalDate established;

    private String nickname;

    public Relationship(User user, User friend, LocalDate established) {
        this.user = user;
        this.friend = friend;
        this.established = established;
    }

    public Relationship(User user, User friend) {
        this.user = user;
        this.friend = friend;
    }
}
