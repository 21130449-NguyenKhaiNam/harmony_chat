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
@Table(name = DefineTableDatabase.GENDER)
public class Gender {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;
}
