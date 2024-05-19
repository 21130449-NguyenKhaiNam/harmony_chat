package com.app.harmony_chat.utils.auths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Thực hiện mã hóa một chiều
 */
@Component
public class PasswordEncoder {
    private BCryptPasswordEncoder encoder;
    // Dùng cho việc khởi tạo ra mật khẩu
    String characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public PasswordEncoder() {
        encoder = new BCryptPasswordEncoder();
    }

    // Mã hóa password bằng thư viện BCrypt
    public String hashPass(String password) {
        return encoder.encode(password);
    }

    // Kiểm tra mật khẩu có chuẩn xác
    public boolean isCorrect(String password, String hashPass) {
        return encoder.matches(password, hashPass);
    }

    public String generatePassword(int length) {
        if (length <= 0 || characterSet.isEmpty()) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characterSet.length());
            char randomChar = characterSet.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return hashPass(stringBuilder.toString());
    }
}
