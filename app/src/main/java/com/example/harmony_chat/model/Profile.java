package com.example.harmony_chat.model;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Profile {
    private long id;

    private User user;

    private String username;

    // Lưu đường dẫn
    private String avatar;

    private LocalDate dob;

    private Gender gender;

    private Country country;

    private String phone;

}
