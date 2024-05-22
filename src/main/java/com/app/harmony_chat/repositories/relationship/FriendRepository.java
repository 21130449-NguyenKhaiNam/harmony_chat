package com.app.harmony_chat.repositories.relationship;

import com.app.harmony_chat.models.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Relationship, Long> {
    @Query("SELECT r FROM Relationship r WHERE r.user.id = :user AND r.friend.id = :friend")
    List<Relationship> findByUserAndFriend(@Param("user") String user, @Param("friend") String friend);

    @Modifying
    @Transactional
    @Query("UPDATE Relationship r SET r.nickname = :nickname WHERE r.id = :relationshipId")
    void setNickNameForFriend(@Param("relationshipId") long id, @Param("nickname") String nickname);

    @Query("SELECT r FROM Relationship r WHERE r.user.id = :userId")
    List<Relationship> findByUser(@Param("userId") String userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Relationship  r WHERE r.user.id = :userId AND r.friend.id = :otherId")
    void deleteFriend(@Param("userId") String userId, @Param("otherId") String otherId);

    default void deleteFriend(Relationship relationship) {
        deleteFriend(relationship.getUser().getId(), relationship.getFriend().getId());
    }
}
