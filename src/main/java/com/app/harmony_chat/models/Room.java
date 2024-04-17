package com.app.harmony_chat.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @Column
    private String id;

    @Column
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
