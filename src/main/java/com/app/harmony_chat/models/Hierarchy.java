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
@Table(name = "vice_leaders")
public class Hierarchy {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long  id;

    @OneToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @OneToOne
    @JoinColumn(name = "leader_id", referencedColumnName = "id")
    private User leader;

    @ManyToOne
    @JoinColumn(name = "deputy_id", referencedColumnName = "id")
    private User deputy;
}
