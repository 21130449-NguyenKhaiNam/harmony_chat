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
    List<Relationship> findByUserAndFriend(UUID userID, UUID friendID);
    @Modifying
    @Query("UPDATE " + DefineTableDatabase.RELATIONSHIP + " r SET r.nickname = :nickname WHERE r.id = :relationshipID")
    void updateNickNameForFirend(@Param("relationshipID") long id, @Param("nickname") String nickname);
    List<Relationship> findByUser(UUID userID);
}
