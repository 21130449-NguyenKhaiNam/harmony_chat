package com.app.harmony_chat.repositories.relationship;

import com.app.harmony_chat.models.Profile;
import com.app.harmony_chat.models.Relationship;
import com.app.harmony_chat.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OtherUserRepository extends JpaRepository<Profile, UUID> {
}
