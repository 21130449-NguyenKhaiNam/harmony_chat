package com.app.harmony_chat.services.relationship;

import com.app.harmony_chat.configs.DefineInfomation;
import com.app.harmony_chat.models.*;
import com.app.harmony_chat.repositories.account.InfoAccountRepository;
import com.app.harmony_chat.repositories.relationship.RoomRepository;
import com.app.harmony_chat.services.auth.AuthServices;
import com.app.harmony_chat.services.image.CloudinaryServices;
import com.app.harmony_chat.utils.infomation.CheckInfomation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository repository;
    @Autowired
    private InfoAccountRepository infoAccountRepository;
    @Autowired
    private CheckInfomation checkInfomation;

    public Infomation getAllRoom(String userId) {
        userId = userId.replaceAll("\"", "");
        Profile profile = infoAccountRepository.findByUserId(userId).orElse(null);
        Infomation info = new Infomation();
        if(checkInfomation.isEmpty(profile)) {
            info.setCode(DefineInfomation.SUCCESS_BUT_NOT_FOUND)
                    .setContent(DefineInfomation.DEFAULT_NO_FRIEND);
        } else {
            List<Hierarchy> rooms = repository.findAllByUserId(userId);
            rooms.forEach(h -> {
                Room room = h.getRoom();
                User user = h.getLeader();
                User other = h.getDeputy();
                user.setPassword(null);
                other.setPassword(null);
                room.setImage(CloudinaryServices.getINSTANCE().getRandomImageGroup());
                room.setBackground(CloudinaryServices.getINSTANCE().getRandomBackground());
            });
            info.setCode(DefineInfomation.SUCCESS)
                    .setContent(rooms);
        }
        return info;
    }
}
