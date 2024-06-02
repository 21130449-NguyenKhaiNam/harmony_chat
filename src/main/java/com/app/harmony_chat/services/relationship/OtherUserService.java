package com.app.harmony_chat.services.relationship;

import com.app.harmony_chat.configs.DefineInfomation;
import com.app.harmony_chat.models.Infomation;
import com.app.harmony_chat.models.Profile;
import com.app.harmony_chat.models.Relationship;
import com.app.harmony_chat.repositories.relationship.FriendRepository;
import com.app.harmony_chat.repositories.relationship.OtherUserRepository;
import com.app.harmony_chat.utils.infomation.FilterInfomation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OtherUserService {
    @Autowired
    private OtherUserRepository dao;
    @Autowired
    private FriendRepository daoFriend;
    @Autowired
    private FilterInfomation filterInfomation;

    /**
     * Lấy ra profile của người khác, được tác hàm riêng vì sau này sẽ có những thông tin cần xử lý không được xem
     *
     * @param otherUserId
     * @return
     */
    public Infomation getProfile(String otherUserId) {
        List<Profile> profileList = dao.findByUserId(otherUserId).stream().toList();
        profileList.forEach(profile -> {
            profile.setUser(null);
        });
        return filterInfomation.filterListGetOne(profileList);
    }

    /**
     * Lấy ra danh sách bạn bè của người lạ
     *
     * @param otherUserID
     * @return
     */
    public Infomation getFriends(String otherUserID) {
        List<Relationship> relationships = daoFriend.findByUser(otherUserID);
        Infomation info = new Infomation();
        info.setCode(DefineInfomation.SUCCESS)
                .setContent(relationships.isEmpty() ?
                        DefineInfomation.DEFAULT_NO_FRIEND
                        : relationships);
        return info;
    }
}
