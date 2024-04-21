package com.app.harmony_chat.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@Table(name = "profiles")
public class Profile {
    @Id
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // Lưu đường dẫn
    @Column
    private String avatar;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate dob;

    @OneToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private Gender gender;

    @OneToOne
    @JoinColumn(name = "coutries_id", referencedColumnName = "id")
    private Country country;

    @Column
    private String phone;
}
