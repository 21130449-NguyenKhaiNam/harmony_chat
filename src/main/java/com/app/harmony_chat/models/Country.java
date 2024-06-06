package com.app.harmony_chat.models;

import com.app.harmony_chat.configs.DefineTableDatabase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@Table(name = DefineTableDatabase.COUNTRY)
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name =  "street_number")
    private String streetNumber;

    @Column(name =  "street_name")
    private String streetName;

    private String district;

    private String city;

    private String state;

    private String country;

    public Country(String streetNumber, String streetName, String district, String city, String state, String country) {
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.district = district;
        this.city = city;
        this.state = state;
        this.country = country;
    }
}
