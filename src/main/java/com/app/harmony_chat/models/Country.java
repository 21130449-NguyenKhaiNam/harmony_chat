package com.app.harmony_chat.models;

import jakarta.persistence.*;

@Entity
@Table(name = "countries")
public class Country {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name =  "streetNumber")
    private String streetNumber;

    @Column(name =  "streetName")
    private String streetName;

    @Column
    private String district;


    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String country;
}
