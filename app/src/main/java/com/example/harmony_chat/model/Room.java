package com.example.harmony_chat.model;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Room {
    private UUID id;

    private LocalDate published;

    private boolean visible;

    // Đường dẫn
    private String image;

    // Đường dẫn
    private String background;
}
