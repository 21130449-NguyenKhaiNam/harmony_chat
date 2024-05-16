package com.app.harmony_chat.repositories.account;

import com.app.harmony_chat.models.Profile;
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
    @Query("SELECT p.id, p.username, p.avatar, p.country, p.dob, p.gender, p.phone FROM Profile p WHERE p.user.id = :userId")
    Optional<Profile> findByUserId(@Param("userId") UUID userId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Profile (user, username) VALUES (:#{#newProfile.user}, :#{#newProfile.username})", nativeQuery = true)
    void saveBasic(Profile newProfile);
}
