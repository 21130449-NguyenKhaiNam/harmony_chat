package com.app.harmony_chat.repositories.relationship;

import com.app.harmony_chat.configs.DefineTableDatabase;
import com.app.harmony_chat.models.Relationship;
import com.app.harmony_chat.models.User;
import com.app.harmony_chat.services.relationship.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FriendRepository extends JpaRepository<Relationship, Long> {
    @Query("SELECT r FROM Relationship r WHERE r.user = :user AND r.friend = :friend")
    List<Relationship> findByUserAndFriend(@Param("user") UUID user, @Param("friend") UUID friend);

    @Modifying
    @Query("UPDATE Relationship r SET r.nickname = :nickname WHERE r.id = :relationshipId")
    void setNickNameForFriend(@Param("relationshipId") long id, @Param("nickname") String nickname);

    @Query("SELECT r FROM Relationship r WHERE r.user = :userId")
    List<Relationship> findByUser(@Param("userId") UUID userId);
}
