package com.app.harmony_chat.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private String id;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate published;

    @Column
    private boolean visible;

    // Đường dẫn
    @Column
    private String image;

    // Đường dẫn
    @Column
    private String background;
}
