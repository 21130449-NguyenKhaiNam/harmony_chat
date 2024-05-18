package com.app.harmony_chat.models;

import com.app.harmony_chat.configs.DefineTableDatabase;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = DefineTableDatabase.PROFILE)
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String username;

    // Lưu đường dẫn
    private String avatar;

    @Temporal(TemporalType.DATE)
    private LocalDate dob;

    @ManyToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "coutries_id", referencedColumnName = "id")
    private Country country;

    private String phone;

    public Profile(User user, String username, String avatar, LocalDate dob, Gender gender, Country country, String phone) {
        this.user = user;
        this.username = username;
        this.avatar = avatar;
        this.dob = dob;
        this.gender = gender;
        this.country = country;
        this.phone = phone;
    }

    public Profile(User user, String username) {
        this.user = user;
        this.username = username;
    }
}
