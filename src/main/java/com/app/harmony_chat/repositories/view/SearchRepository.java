package com.app.harmony_chat.repositories.view;

import com.app.harmony_chat.models.Profile;
import com.app.harmony_chat.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SearchRepository extends JpaRepository<Profile, User> {
    List<String> findByUserName(String username);
}
