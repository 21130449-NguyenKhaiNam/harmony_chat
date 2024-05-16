package com.app.harmony_chat.models;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * Dùng cho việc định hình các thông tin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Infomation {
    private int code;
    private Object content;
}
