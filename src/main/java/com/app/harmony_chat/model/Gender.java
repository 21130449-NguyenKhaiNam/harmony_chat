package com.app.harmony_chat.model;

import jakarta.persistence.*;

@Entity
@Table(name = "gendere")
public class Gender {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "name")
    private String name;
}
