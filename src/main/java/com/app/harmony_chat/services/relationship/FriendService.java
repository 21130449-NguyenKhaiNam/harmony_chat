package com.app.harmony_chat.services.relationship;

import com.app.harmony_chat.configs.DefineInfomation;
import com.app.harmony_chat.models.Infomation;
import com.app.harmony_chat.models.Relationship;
import com.app.harmony_chat.models.User;
import com.app.harmony_chat.repositories.relationship.FriendRepository;
import com.app.harmony_chat.utils.infomation.CheckInfomation;
import com.app.harmony_chat.utils.infomation.FilterInfomation;
import com.app.harmony_chat.utils.infomation.MapperJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FriendService {
    @Autowired
    private FriendRepository dao;
    @Autowired
    private CheckInfomation checkInfomation;
    @Autowired
    private FilterInfomation filterInfomation;
    @Autowired
    private MapperJson mapper;

    /**
     * Trả về mã mối quan hệ nếu có của 2 người
     * @param userID
     * @param otherID
     * @return
     */
    private Infomation selectRelationship(String userID, String otherID) {
        List<Relationship> relationships = dao.findByUserAndFriend(UUID.fromString(userID), UUID.fromString(otherID));
        return filterInfomation.filterListGetOne(relationships);
    }

    /**
     * Thêm bạn với nhau
     * @param userID
     * @param otherID
     * @return
     */
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

    /**
     * Xóa một người bạn
     * @param userID
     * @param otherID
     * @return
     */
    public Infomation deleteFriend(String userID, String otherID) {
        User user = new User(userID);
        User otherUser = new User(otherID);
        dao.delete(new Relationship(user, otherUser));
        Infomation info = new Infomation()
                .setCode(DefineInfomation.SUCCESS)
                .setContent(DefineInfomation.DEFAULT_UN_FRIEND_SUCCESS);
        return info;
    }

    /**
     * Cập nhật biệt danh cho người bạn
     * @param userID
     * @param otherID
     * @param nickname
     * @return
     */
    public Infomation updateNickName(String userID, String otherID, String nickname) {
        Infomation info = selectRelationship(userID, otherID);
        Relationship relationship = mapper.convertObject((String) info.getContent(), Relationship.class);
        dao.setNickNameForFriend(relationship.getId(), nickname);
        info.setCode(DefineInfomation.SUCCESS)
                .setContent(DefineInfomation.DEFAULT_RENAME_NICKNAME_FRIEND);
        return info;
    }

    /**
     * Lấy ra danh sách bạn của chính mình
     * @param userID
     * @return
     */
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
