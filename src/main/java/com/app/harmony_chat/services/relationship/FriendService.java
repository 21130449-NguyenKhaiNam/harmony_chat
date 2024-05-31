package com.app.harmony_chat.services.relationship;

import com.app.harmony_chat.configs.DefineInfomation;
import com.app.harmony_chat.models.Infomation;
import com.app.harmony_chat.models.Profile;
import com.app.harmony_chat.models.Relationship;
import com.app.harmony_chat.models.User;
import com.app.harmony_chat.repositories.account.InfoAccountRepository;
import com.app.harmony_chat.repositories.relationship.FriendRepository;
import com.app.harmony_chat.services.auth.AuthServices;
import com.app.harmony_chat.services.image.CloudinaryServices;
import com.app.harmony_chat.utils.infomation.CheckInfomation;
import com.app.harmony_chat.utils.infomation.FilterInfomation;
import com.app.harmony_chat.utils.infomation.MapperJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FriendService {
    @Autowired
    private FriendRepository dao;
    @Autowired
    private InfoAccountRepository infoAccountDao;
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
        List<Relationship> relationships = dao.findByUserAndFriend(userID, otherID);
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
        Infomation info = selectRelationship(userID, otherID);
        if(checkInfomation.checkOneWithAll(true, info.getCode(), DefineInfomation.SUCCESS_BUT_NOT_FOUND)) {
            Profile profileFriend = infoAccountDao.findByUserId(otherID).orElse(null); // Vẫn có thể lỗi nếu không kiểm soát tốt
            Relationship relationship = dao.save(new Relationship(user, otherUser, LocalDate.now(), profileFriend.getUsername()));
            info.setCode(DefineInfomation.SUCCESS)
                    .setContent(relationship.getId() + "");
        } else {
            info.setCode(DefineInfomation.ERROR_CLIENT)
                    .setContent(DefineInfomation.DEFAULT_HAS_FRIEND);
        }
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
        Infomation info = selectRelationship(userID, otherID);
        if(checkInfomation.checkOneWithAll(true, info.getCode(), DefineInfomation.SUCCESS)) {
            dao.deleteFriend(new Relationship(user, otherUser));
            info.setContent(DefineInfomation.EMPTY);
        } else {
            info.setCode(DefineInfomation.ERROR_CLIENT)
                    .setContent(DefineInfomation.DEFAULT_NO_FRIEND);
        }
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
        if(checkInfomation.checkOneWithAll(true, info.getCode(), DefineInfomation.SUCCESS)) {
            String json = mapper.mapToJson(info.getContent());
            System.out.println(json);
            Relationship relationship = mapper.convertObject(json, Relationship.class);
            dao.setNickNameForFriend(relationship.getId(), nickname);
            info.setCode(DefineInfomation.SUCCESS)
                    .setContent(DefineInfomation.DEFAULT_RENAME_NICKNAME_FRIEND);
        } else {
            info.setCode(DefineInfomation.ERROR_CLIENT)
                    .setContent(DefineInfomation.DEFAULT_NO_FRIEND);
        }

        return info;
    }

    /**
     * Lấy ra danh sách bạn của chính mình
     * @param userID
     * @return
     */
    public Infomation getListFriends(String userID) {
        List<Relationship> relationships = dao.findByUser(userID);
        List<Profile> profilesFriends = new ArrayList<>();
        relationships.forEach(relationship -> {
            Profile profile = infoAccountDao.findByUserId(relationship.getFriend().getId()).orElse(null);
            profile.setUser(null);
            if(!checkInfomation.isEmpty(profile)) {
                // Giả ảnh
                profile.setAvatar(CloudinaryServices.getINSTANCE().getRandomImage());
                profilesFriends.add(profile);
            }
        });
        Infomation info = new Infomation();
        info.setCode(DefineInfomation.SUCCESS);
        info.setContent(profilesFriends);
        return info;
    }
}
