package com.app.harmony_chat.models;

import com.app.harmony_chat.configs.DefineTableDatabase;
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
@Table(name = DefineTableDatabase.COUNTRY)
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name =  "street_number")
    private String streetNumber;

    @Column(name =  "street_name")
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
