package com.app.harmony_chat.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@Table(name = "countries")
public class Country {
    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    private User id;

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
