package com.app.harmony_chat.models;

import com.app.harmony_chat.configs.DefineTableDatabase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = DefineTableDatabase.ROOM)
public class Room {
    @Id
    private long id;
    
    @Temporal(TemporalType.DATE)
    private LocalDate published;

    private boolean visible;

    // Đường dẫn
    private String image;

    // Đường dẫn
    private String background;

    public Room(long id, LocalDate published, boolean visible) {
        this.id = id;
        this.published = published;
        this.visible = visible;
    }
}
