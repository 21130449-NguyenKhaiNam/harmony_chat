package com.app.harmony_chat.models;

import lombok.*;

import java.util.UUID;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private UUID userId;
    private UUID otherId;
    private String message;
}
