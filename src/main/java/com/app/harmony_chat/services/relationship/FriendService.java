package com.app.harmony_chat.services.relationship;

import com.app.harmony_chat.configs.DefineInfomation;
import com.app.harmony_chat.models.Infomation;
import com.app.harmony_chat.models.Relationship;
import com.app.harmony_chat.models.User;
import com.app.harmony_chat.repositories.relationship.FriendRepository;
import com.app.harmony_chat.utils.infomation.CheckInfomation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class FriendService {
    @Autowired
    private FriendRepository dao;
    @Autowired
    private CheckInfomation checkInfomation;

    private long selectRelationship(String userID, String otherID) {
        List<Relationship> relationships = dao.findByUserAndFriend(UUID.fromString(userID), UUID.fromString(otherID));
        if (relationships.size() > 1) {
            return -1;
        } else {
            if (relationships.isEmpty()) {
                return -1;
            } else {
                Relationship relationship = relationships.get(0);
                return relationship.getId();
            }
        }
    }

    public Infomation addFriend(String userID, String otherID) {
        User user = new User(userID);
        User otherUser = new User(otherID);
        Relationship relationship = dao.save(new Relationship(user, otherUser, LocalDate.now()));
        Infomation info = new Infomation();
        info.setCode(DefineInfomation.SUCCESS)
                .setContent(checkInfomation.isEmpty(info) ?
                        DefineInfomation.DEFAULT_HAS_FRIEND : relationship.getId() + "");
        return info;
    }

    public Infomation deleteFriend(String userID, String otherID) {
        User user = new User(userID);
        User otherUser = new User(otherID);
        dao.delete(new Relationship(user, otherUser));
        Infomation info = new Infomation()
                .setCode(DefineInfomation.SUCCESS)
                .setContent(DefineInfomation.DEFAULT_UN_FRIEND_SUCCESS);
        return info;
    }

    public Infomation updateNickName(String userID, String otherID, String nickname) {
        long relaId = selectRelationship(userID, otherID);
        dao.updateNickNameForFirend(relaId, nickname);
        Infomation info = new Infomation()
                .setCode(DefineInfomation.SUCCESS)
                .setContent(DefineInfomation.DEFAULT_RENAME_NICKNAME_FRIEND);
        return info;
    }

    public Infomation getListFriends(String userID) {
        List<Relationship> relationships = dao.findByUser(UUID.fromString(userID));
        List<User> friends = relationships.stream()
                .map(relationship -> relationship.getFriend())
                .collect(Collectors.toList());
        Infomation info = new Infomation();
        info.setCode(DefineInfomation.SUCCESS);
        info.setContent(friends);
        return info;
    }
}