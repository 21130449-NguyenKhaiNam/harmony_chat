package com.app.harmony_chat.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@Table(name = "profiles")
public class Profile {
    @Id
    @Unique
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Lưu đường dẫn
    @Column
    private String avatar;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate dob;

    @OneToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @OneToOne
    @JoinColumn(name = "coutries_id")
    private Country country;

    @Column
    private String phone;
}
