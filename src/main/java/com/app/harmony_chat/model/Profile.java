package com.app.harmony_chat.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @Column
    private String id;

    @OneToOne
    @Column(name = "user_id")
    private User user;

    // Lưu đường dẫn
    @Column
    private String avatar;

    @Column
    private LocalDate dob;

    @Column(name = "sex")
    @OneToOne
    private Gender gender;

    @Column
    @OneToOne
    private Country country;

    @Column
    private String phone;

    public Profile() {
    }
}
