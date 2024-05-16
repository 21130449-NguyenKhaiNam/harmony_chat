package com.example.harmony_chat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    private long id;

    private User user;

    private String streetNumber;
    private String streetName;

    private String district;

    private String city;

    private String state;

    private String country;
}
