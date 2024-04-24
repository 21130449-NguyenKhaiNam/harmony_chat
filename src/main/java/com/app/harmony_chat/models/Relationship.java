package com.app.harmony_chat.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@Table(name = "relationships")
public class Relationship {
    @Id
    @Column
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id", referencedColumnName = "id")
    private User friend;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate established;

    @Column
    private String nickname;
}
