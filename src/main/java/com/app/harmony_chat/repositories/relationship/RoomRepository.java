package com.app.harmony_chat.repositories.relationship;

import com.app.harmony_chat.models.Hierarchy;
import com.app.harmony_chat.models.Member;
import com.app.harmony_chat.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Hierarchy, Long> {
    @Modifying
    @Transactional
    @Query("SELECT r FROM Room r WHERE r.id = :roomId")
    List<Room> findRoomById(@Param("roomId") Long roomId);

    @Modifying
    @Transactional
    @Query("SELECT h FROM Hierarchy h WHERE h.room.id = :roomId")
    List<Hierarchy> findByRoomId(@Param("roomId") Long roomId);

    @Modifying
    @Transactional
    @Query("SELECT h FROM Hierarchy h WHERE h.leader.id =:userId OR h.deputy.id = :userId")
    List<Hierarchy> findAllByUserId(@Param("userId") String userId);

    @Modifying
    @Transactional
    @Query(value = "insert into rooms (id, published, visible) VALUES (:id, :published, :visible)", nativeQuery = true)
    void saveRoom(@Param("id") long id, @Param("published") LocalDate published, @Param("visible") boolean visible);

    @Modifying
    @Transactional
    @Query(value = "insert into vice_leaders (room_id, leader_id, deputy_id) VALUES (:roomId, :leaderId, :deputyId)", nativeQuery = true)
    void saveHierarchy(@Param("roomId") long roomId, @Param("leaderId") String leaderId, @Param("deputyId") String deputyId);

    @Modifying
    @Transactional
    @Query(value = "insert into members (room_id, user_id) values (:roomId, :userId)", nativeQuery = true)
    void saveMember(@Param("roomId") long roomId, @Param("userId") String userId);

    default void saveRoom(Room room) {
        saveRoom(room.getId(), room.getPublished(), room.isVisible());
    }

    default void saveHierarchy(Hierarchy hierarchy) {
        saveHierarchy(hierarchy.getRoom().getId(), hierarchy.getLeader().getId(), hierarchy.getDeputy().getId());
    }

    default void saveMember(Member member) {
        saveMember(member.getRoom().getId(), member.getUser().getId());
    }
}
