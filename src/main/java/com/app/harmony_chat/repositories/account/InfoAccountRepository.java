package com.app.harmony_chat.repositories.account;

import com.app.harmony_chat.models.BlackList;
import com.app.harmony_chat.models.Profile;
import com.app.harmony_chat.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ListResourceBundle;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InfoAccountRepository extends JpaRepository<Profile, Long> {
    @Query("SELECT p FROM Profile p WHERE p.user.id = :userId")
    Optional<Profile> findByUserId(@Param("userId") String userId);

    @Modifying
    @Transactional
    @Query(value = "insert into profiles (user_id, username) VALUES (:user, :username)", nativeQuery = true)
    void saveBasic(@Param("user") String userId, @Param("username") String username);

    default void saveBasic(Profile profile) {
        saveBasic(profile.getUser().getId(), profile.getUsername());
    }
}
