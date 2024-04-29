package com.app.harmony_chat.services.relationship;

import com.app.harmony_chat.configs.DefineInfomation;
import com.app.harmony_chat.models.Infomation;
import com.app.harmony_chat.models.Profile;
import com.app.harmony_chat.models.Relationship;
import com.app.harmony_chat.repositories.relationship.FriendRepository;
import com.app.harmony_chat.repositories.relationship.OtherUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class OtherUserService {
    @Autowired
    private OtherUserRepository dao;
    @Autowired
    private FriendRepository daoFriend;

    public Infomation getProfile(String otherUserId) {
        List<Profile> profileList = dao.findById(UUID.fromString(otherUserId)).stream().toList();
        Infomation info = new Infomation();
        if(profileList.size() > 1) {

        } else {
            if(profileList.isEmpty()) {

            } else {
                Profile profile = profileList.get(0);
                info.setCode(DefineInfomation.SUCCESS).setContent(profile);
            }
        }
        return info;
    }

    public Infomation getFriends(String otherUserID) {
        List<Relationship> relationships = daoFriend.findByUser(UUID.fromString(otherUserID));
        Infomation info = new Infomation();
        if(relationships.size() > 1) {

        } else {
            if(relationships.isEmpty()) {

            } else {
                info.setCode(DefineInfomation.SUCCESS).setContent(relationships);
            }
        }
        return info;
    }
}
