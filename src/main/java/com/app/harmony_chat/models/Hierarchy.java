package com.app.harmony_chat.models;

import com.app.harmony_chat.configs.DefineTableDatabase;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = DefineTableDatabase.VICE_LEADER)
public class Hierarchy {
    @Id
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

    public Hierarchy(Room room, User leader, User deputy) {
        this.room = room;
        this.leader = leader;
        this.deputy = deputy;
    }
}
