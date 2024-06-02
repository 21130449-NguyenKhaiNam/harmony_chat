package com.app.harmony_chat.repositories.account;

import com.app.harmony_chat.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<User, UUID> {
    // Tìm theo tên tài khoản
    List<User> findByEmail(String email);
}
