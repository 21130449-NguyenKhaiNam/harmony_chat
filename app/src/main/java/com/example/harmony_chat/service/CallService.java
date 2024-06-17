package com.example.harmony_chat.service;

import android.util.Log;

import com.example.harmony_chat.config.DefinePropertyJson;
import com.example.harmony_chat.config.DefineStatusResponsive;
import com.example.harmony_chat.model.BlackList;
import com.example.harmony_chat.model.Hierarchy;
import com.example.harmony_chat.model.Information;
import com.example.harmony_chat.model.Profile;
import com.example.harmony_chat.model.Relationship;
import com.example.harmony_chat.model.User;
import com.example.harmony_chat.util.MapFactory;
import com.example.harmony_chat.util.MapperJson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;

public class CallService {
    // Lý do không để null vì:
    // 1. Sử dụng tính chất Anonymous Class
    // 2. Tránh tình trạng bên FE không bắt tình huống null

    // Các trường hợp khác, bổ sung cho việc nâng cấp hệ thống
//                else if(code == DefineStatusResponsive.SUCCESS_BUT_NOT_CORRECT) {
//                    // Tìm thấy nhưng thông tin không đúng
//                } else if (code == DefineStatusResponsive.SUCCESS_BUT_NOT_FOUND){
//                    // Không tìm thấy tài khoản
//                } else if(code == DefineStatusResponsive.ERROR_CLIENT) {
//                    // Lỗi do người dùng hoặc lập trình viên
//                } else {
//                    // Lỗi hệ thống
//                }

    private static CallService service;

    private CallService() {
    }

    public static CallService getInstance() {
        return service == null ? (service = new CallService()) : service;
    }

    /**
     * Hàm gọi api chung
     */
    public void generalCallApi(Response<DataResponsive> response, CallBack func) {
        Information info = new Information();
        if (response != null && response.isSuccessful() && response.body() != null) {
            DataResponsive dataResponsive = response.body();
            info.setCode(dataResponsive.getCode());
            info.setJson(dataResponsive.getContent());
        } else {
            Log.e("API Error", "Response not successful or body is null");
        }
        int code = info.getCode();
        String content = info.getJson();
        if (code == DefineStatusResponsive.SUCCESS) {
            // Những gì diễn ra nếu gọi thành công với mã hiệu là 200
            func.callback(code, content);
        }
    }

    /**
     * Đăng nhập
     * 1. Thành công: Trả về token - Gắn trong id tài khoản
     * 2. Không thành công: Không có gì
     *
     * @param email
     * @param password
     * @return
     */
    public User loginAccount(String email, String password) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.EMAIL, DefinePropertyJson.PASSWORD);
        String[] values = MapFactory.createArrayString(email, password);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        User user = new User();
        Response<DataResponsive> res = null;
        try {
            res = ApiService.service.login(json).execute();
        } catch (IOException e) {
            Log.e("Lỗi gọi api login", e.getMessage());
        }
        generalCallApi(res, (code, content) -> {
            user.setId(content);
        });
        return user;
    }

    /**
     * Đăng ký
     * 1. Thành công: Trả về token - Gắn trong id tài khoản
     * 2. Không thành công: Không có gì
     *
     * @param email
     * @param password
     * @param username
     * @return
     */
    public User registerAccount(String email, String password, String username) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.EMAIL, DefinePropertyJson.PASSWORD, DefinePropertyJson.USERNAME);
        String[] values = MapFactory.createArrayString(email, password, username);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        User user = new User();
        Response<DataResponsive> res = null;
        try {
            res = ApiService.service.register(json).execute();
        } catch (IOException e) {
            Log.e("Lỗi gọi api register", e.getMessage());
        }
        generalCallApi(res, (code, content) -> {
            user.setId(content);
        });
        return user;
    }

    // Quên mật khẩu, gửi mật khẩu mới về mail
    public User forgetAccount(String email) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.EMAIL);
        String[] values = MapFactory.createArrayString(email);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        User user = new User();
        Response<DataResponsive> res = null;
        try {
            res = ApiService.service.forget(json).execute();
        } catch (IOException e) {
            Log.e("Lỗi gọi api forget", e.getMessage());
        }
        return user;
    }

    // Xem trang cá nhân bản thân
    public Profile viewMyProfile(String userId) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.USER_ID);
        String[] values = MapFactory.createArrayString(userId);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        Profile profile = new Profile();
        Response<DataResponsive> res = null;
        try {
            res = ApiService.service.profile(json).execute();
        } catch (IOException e) {
            Log.e("Lỗi gọi api profile", e.getMessage());
        }
        generalCallApi(res, (code, content) -> {
            profile.inject(MapperJson.getInstance().convertObjFromJson(content, Profile.class));
        });
        return profile;
    }

    // Cập nhật thông tin bản thân
    public Profile updateMyInfo(Profile newProfile) {
        return null;
    }

    // Thêm bạn
    public boolean addFriend(String userId, String otherId) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.USER_ID, DefinePropertyJson.OTHER_ID);
        String[] values = MapFactory.createArrayString(userId, otherId);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        Relationship relationship = new Relationship();
        Response<DataResponsive> res = null;
        try {
            res = ApiService.service.addFriend(json).execute();
        } catch (IOException e) {
            Log.e("Lỗi gọi api addFriend", e.getMessage());
        }
        generalCallApi(res, (code, content) -> {
            relationship.setId(Long.parseLong(content));
        });
        return relationship.getId() != 0;
    }

    // Xóa bạn
    public boolean unfriend(String userId, String otherId) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.USER_ID, DefinePropertyJson.OTHER_ID);
        String[] values = MapFactory.createArrayString(userId, otherId);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        Relationship relationship = new Relationship();
        Response<DataResponsive> res = null;
        try {
            res = ApiService.service.unFriend(json).execute();
        } catch (IOException e) {
            Log.e("Lỗi gọi api unFriend", e.getMessage());
        }
        generalCallApi(res, (code, content) -> {
            relationship.setId(-1);
        });
        return relationship.getId() == -1;
    }

    // Đổi biệt danh
    public String renameFriend(String userId, String otherId, String nickname) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.USER_ID, DefinePropertyJson.OTHER_ID);
        String[] values = MapFactory.createArrayString(userId, otherId);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        StringBuilder stringBuilder = new StringBuilder();
        Response<DataResponsive> res = null;
        try {
            res = ApiService.service.renameFriend(json).execute();
        } catch (IOException e) {
            Log.e("Lỗi gọi api renameFriend", e.getMessage());
        }
        generalCallApi(res, (code, content) -> {
            stringBuilder.append(content);
        });
        return stringBuilder.toString();
    }

    // Xem danh sách bạn bè của mình
    public List<Profile> getMyListFriends(String userId) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.USER_ID);
        String[] values = MapFactory.createArrayString(userId);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        List<Profile> profiles = new ArrayList<>();
        Response<DataResponsive> res = null;
        try {
            res = ApiService.service.listFriend(json).execute();
        } catch (IOException e) {
            Log.e("Lỗi gọi api listFriend", e.getMessage());
        }
        generalCallApi(res, (code, content) -> {
            profiles.addAll(MapperJson.getInstance().convertListObjFromJson(content, Profile.class));
        });
        return profiles;
    }

    // Xem trang cá nhân của người khác
    public Profile viewOtherProfile(String otherId) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.OTHER_ID);
        String[] values = MapFactory.createArrayString(otherId);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        Profile profile = new Profile();
        Response<DataResponsive> res = null;
        try {
            res = ApiService.service.otherProfile(json).execute();
        } catch (IOException e) {
            Log.e("Lỗi gọi api otherProfile", e.getMessage());
        }
        generalCallApi(res, (code, content) -> {
            profile.inject(MapperJson.getInstance().convertObjFromJson(content, Profile.class));
        });

        return profile;
    }

    // Xem danh sách bạn bè của người khác
    public List<Profile> getOtherListFriends(String otherId) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.OTHER_ID);
        String[] values = MapFactory.createArrayString(otherId);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        List<Profile> profiles = new ArrayList<>();
        Response<DataResponsive> res = null;
        try {
            res = ApiService.service.otherFriends(json).execute();
        } catch (IOException e) {
            Log.e("Lỗi gọi api otherFriends", e.getMessage());
        }
        generalCallApi(res, (code, content) -> {
            profiles.addAll(MapperJson.getInstance().convertListObjFromJson(content, Profile.class));
        });
        return profiles;
    }

    // Xem danh sách các người dùng bị chặn
    public List<BlackList> getBlackList(String userId) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.USER_ID);
        String[] values = MapFactory.createArrayString(userId);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        ArrayList<BlackList> blackList = new ArrayList<>();
//        RxHelper.performImmediately(() -> {
//            ApiService.service.getBlackList(json).enqueue(callback);
//            Information info = callback.getInfo();
//            int code = info.getCode();
//            String content = info.getJson();
//            if (code == DefineStatusResponsive.SUCCESS) {
//                blackList.addAll(MapperJson.getInstance().convertListObjFromJson(content, BlackList.class));
//            }
//        });
        Response<DataResponsive> res = null;
        try {
            res = ApiService.service.getBlackList(json).execute();
        } catch (IOException e) {
            Log.e("Lỗi gọi api getBlackList", e.getMessage());
        }
        generalCallApi(res, (code, content) -> {
            blackList.addAll(MapperJson.getInstance().convertListObjFromJson(content, BlackList.class));
        });
        return blackList;
    }

    public List<Hierarchy> getRoom(String userId) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.USER_ID);
        String[] values = MapFactory.createArrayString(userId);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        List<Hierarchy> rooms = new ArrayList<>();
        Response<DataResponsive> res = null;
        try {
            res = ApiService.service.getRoom(json).execute();
        } catch (IOException e) {
            Log.e("Lỗi gọi api getRoom", e.getMessage());
        }
        generalCallApi(res, (code, content) -> {
            rooms.addAll(MapperJson.getInstance().convertListObjFromJson(content, Hierarchy.class));
        });
        return rooms;
    }

    public List<User> getAllMembersRoom(String roomId) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.ROOM_ID);
        String[] values = MapFactory.createArrayString(roomId);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        List<User> members = new ArrayList<>();
        Response<DataResponsive> res = null;
        try {
            res = ApiService.service.getAllMembersRoom(json).execute();
        } catch (IOException e) {
            Log.e("Lỗi gọi api getAllMembersRoom", e.getMessage());
        }
        generalCallApi(res, (code, content) -> {
            members.addAll(MapperJson.getInstance().convertListObjFromJson(content, User.class));
        });
        Log.e("Tincoder", members.size()+"");
        return members;
    }

    public Observable<List<User>> getAllMembersRoomWObservalbe(String roomId) {
        return Observable.fromCallable(() -> {
            String[] keys = MapFactory.createArrayString(DefinePropertyJson.ROOM_ID);
            String[] values = MapFactory.createArrayString(roomId);
            Map<String, String> json = MapFactory.createMapJson(keys, values);
            List<User> members = new ArrayList<>();
            Response<DataResponsive> res = null;
            try {
                res = ApiService.service.getAllMembersRoom(json).execute();
            } catch (IOException e) {
                Log.e("Lỗi gọi api getAllMembersRoom", e.getMessage());
                throw e; // Throw the exception to be handled by Observable
            }
            generalCallApi(res, (code, content) -> {
                members.addAll(MapperJson.getInstance().convertListObjFromJson(content, User.class));
            });
            return members;
        });
    }
}