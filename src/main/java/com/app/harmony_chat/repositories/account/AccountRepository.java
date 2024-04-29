package com.app.harmony_chat.repositories.account;

import com.app.harmony_chat.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Xử lý gọi xuống cơ sở dữ liệu
 */
@Repository
public interface AccountRepository extends JpaRepository<User, UUID> {
    // Mật khâủ được truyền vào phải được mã hóa từ trước
    List<User> findByEmailAndPassword(String username, String password);

    // Tìm theo tên tài khoản
    List<User> findByEmail(String email);
}
