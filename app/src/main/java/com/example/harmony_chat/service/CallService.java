package com.example.harmony_chat.service;

import com.example.harmony_chat.config.DefinePropertyJson;
import com.example.harmony_chat.config.DefineStatusResponsive;
import com.example.harmony_chat.model.Information;
import com.example.harmony_chat.model.Profile;
import com.example.harmony_chat.model.Relationship;

import android.util.Log;

import com.example.harmony_chat.config.DefinePropertyJson;
import com.example.harmony_chat.config.DefineStatusResponsive;
import com.example.harmony_chat.model.BlackList;
import com.example.harmony_chat.model.Information;
import com.example.harmony_chat.model.User;
import com.example.harmony_chat.util.MapFactory;
import com.example.harmony_chat.util.MapperJson;
import com.example.harmony_chat.util.RxHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
        Callback callback = new Callback();
        RxHelper.performImmediately(() -> {
            ApiService.service.login(json).enqueue(callback);
            Information info = callback.getInfo();
            int code = info.getCode();
            String content = info.getJson();
            if (code == DefineStatusResponsive.SUCCESS) {
                user.setId(content);
            }
        });
        return user;
    }

    public User loginGoogleAccount(String email) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.EMAIL, DefinePropertyJson.PASSWORD);
        String[] values = MapFactory.createArrayString(email, "");
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        User user = new User();
        Callback callback = new Callback();
        RxHelper.performImmediately(() -> {
            ApiService.service.login(json).enqueue(callback);
            Information info = callback.getInfo();
            int code = info.getCode();
            String content = info.getJson();
            if (code == DefineStatusResponsive.SUCCESS) {
                user.setId(content);
            }
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
        Callback callback = new Callback();
        RxHelper.performImmediately(() -> {
            ApiService.service.login(json).enqueue(callback);
            Information info = callback.getInfo();
            int code = info.getCode();
            String content = info.getJson();
            if (code == DefineStatusResponsive.SUCCESS) {
                user.setId(content);
            }
        });
        return user;
    }

    public User registerGoogleAccount(String email,String username) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.EMAIL, DefinePropertyJson.PASSWORD, DefinePropertyJson.USERNAME);
        String[] values = MapFactory.createArrayString(email,"", username);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        User user = new User();
        Callback callback = new Callback();
        RxHelper.performImmediately(() -> {
            ApiService.service.login(json).enqueue(callback);
            Information info = callback.getInfo();
            int code = info.getCode();
            String content = info.getJson();
            if (code == DefineStatusResponsive.SUCCESS) {
                user.setId(content);
            }
        });
        return user;
    }

    // Quên mật khẩu, gửi mật khẩu mới về mail
    public User forgetAccount(String email) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.EMAIL);
        String[] values = MapFactory.createArrayString(email);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        User user = new User();
        Callback callback = new Callback();
        RxHelper.performImmediately(() -> {
            ApiService.service.login(json).enqueue(callback);
            Information info = callback.getInfo();
            int code = info.getCode();
            String content = info.getJson();
            if (code == DefineStatusResponsive.SUCCESS) {
                user.setEmail(content);
            }
            // Các trường hợp khác
//                else if(code == DefineStatusResponsive.SUCCESS_BUT_NOT_CORRECT) {
//                    // Tìm thấy nhưng thông tin không đúng
//                } else if (code == DefineStatusResponsive.SUCCESS_BUT_NOT_FOUND){
//                    // Không tìm thấy tài khoản
//                } else if(code == DefineStatusResponsive.ERROR_CLIENT) {
//                    // Lỗi do người dùng hoặc lập trình viên
//                } else {
//                    // Lỗi hệ thống
//                }

        });
        return user;
    }

    // Xem trang cá nhân bản thân
    public Profile viewMyProfile(String userId) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.USER_ID);
        String[] values = MapFactory.createArrayString(userId);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        Profile profile = new Profile();
        Callback callback = new Callback();
        RxHelper.performImmediately(() -> {
            ApiService.service.login(json).enqueue(callback);
            Information info = callback.getInfo();
            int code = info.getCode();
            String content = info.getJson();
            if (code == DefineStatusResponsive.SUCCESS) {
                Profile convertProfileFromJson = MapperJson.getInstance().convertObjFromJson(content, Profile.class);
                profile.inject(convertProfileFromJson);
            }
            // Các trường hợp khác
//                else if(code == DefineStatusResponsive.SUCCESS_BUT_NOT_CORRECT) {
//                    // Tìm thấy nhưng thông tin không đúng
//                } else if (code == DefineStatusResponsive.SUCCESS_BUT_NOT_FOUND){
//                    // Không tìm thấy tài khoản
//                } else if(code == DefineStatusResponsive.ERROR_CLIENT) {
//                    // Lỗi do người dùng hoặc lập trình viên
//                } else {
//                    // Lỗi hệ thống
//                }

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
        Callback callback = new Callback();
        Relationship relationship = new Relationship();
        RxHelper.performImmediately(() -> {
            ApiService.service.login(json).enqueue(callback);
            Information info = callback.getInfo();
            int code = info.getCode();
            String content = info.getJson();
            if (code == DefineStatusResponsive.SUCCESS) {
                relationship.setId(Long.parseLong(content));
            }
        });
        return relationship.getId() != 0;
    }

    // Xóa bạn
    public boolean unfriend(String userId, String otherId) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.USER_ID, DefinePropertyJson.OTHER_ID);
        String[] values = MapFactory.createArrayString(userId, otherId);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        Callback callback = new Callback();
        Relationship relationship = new Relationship();
        RxHelper.performImmediately(() -> {
            ApiService.service.login(json).enqueue(callback);
            Information info = callback.getInfo();
            int code = info.getCode();
            String content = info.getJson();
            if (code == DefineStatusResponsive.SUCCESS) {
                relationship.setId(-1);
            }
        });
        return relationship.getId() == -1;
    }

    public String changePasswordAuto(String email, String password) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.EMAIL, DefinePropertyJson.PASSWORD);
        String[] values = MapFactory.createArrayString(email, password);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        Callback callback = new Callback();
        StringBuilder stringBuilder = new StringBuilder();
        RxHelper.performImmediately(() -> {
            ApiService.service.login(json).enqueue(callback);
            Information info = callback.getInfo();
            int code = info.getCode();
            String content = info.getJson();
            if (code == DefineStatusResponsive.SUCCESS) {
                stringBuilder.append(content);
            }
        });
        return stringBuilder.toString();
    }

    // Đổi biệt danh
    public String renameFriend(String userId, String otherId, String nickname) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.USER_ID, DefinePropertyJson.OTHER_ID);
        String[] values = MapFactory.createArrayString(userId, otherId);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        Callback callback = new Callback();
        StringBuilder stringBuilder = new StringBuilder();
        RxHelper.performImmediately(() -> {
            ApiService.service.login(json).enqueue(callback);
            Information info = callback.getInfo();
            int code = info.getCode();
            String content = info.getJson();
            if (code == DefineStatusResponsive.SUCCESS) {
                stringBuilder.append(content);
            }
        });
        return stringBuilder.toString();
    }

    // Xem danh sách bạn bè của mình
    public List<Profile> getMyListFriends(String userId) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.USER_ID);
        String[] values = MapFactory.createArrayString(userId);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        Callback callback = new Callback();
        List<Profile> profiles = new ArrayList<>();
        RxHelper.performImmediately(() -> {
            ApiService.service.login(json).enqueue(callback);
            Information info = callback.getInfo();
            int code = info.getCode();
            String content = info.getJson();
            if (code == DefineStatusResponsive.SUCCESS) {
                profiles.addAll(MapperJson.getInstance().convertListObjFromJson(content, Profile.class));
            }
        });
        return profiles;
    }

    // Xem trang cá nhân của người khác
    public Profile viewOtherProfile(String otherId) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.OTHER_ID);
        String[] values = MapFactory.createArrayString(otherId);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        Profile profile = new Profile();
        Callback callback = new Callback();
        RxHelper.performImmediately(() -> {
            ApiService.service.login(json).enqueue(callback);
            Information info = callback.getInfo();
            int code = info.getCode();
            String content = info.getJson();
            if (code == DefineStatusResponsive.SUCCESS) {
                Profile convertProfileFromJson = MapperJson.getInstance().convertObjFromJson(content, Profile.class);
                profile.inject(convertProfileFromJson);
            }
        });
        return profile;
    }

    // Xem danh sách bạn bè của người khác
    public List<Profile> getOtherListFriends(String otherId) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.OTHER_ID);
        String[] values = MapFactory.createArrayString(otherId);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        Callback callback = new Callback();
        List<Profile> profiles = new ArrayList<>();
        RxHelper.performImmediately(() -> {
            ApiService.service.login(json).enqueue(callback);
            Information info = callback.getInfo();
            int code = info.getCode();
            String content = info.getJson();
            if (code == DefineStatusResponsive.SUCCESS) {
                profiles.addAll(MapperJson.getInstance().convertListObjFromJson(content, Profile.class));
            }
        });
        return profiles;
    }

    // Xem danh sách các người dùng bị chặn
    public ArrayList<BlackList> getBlackList(String userId) {
        String[] keys = MapFactory.createArrayString(DefinePropertyJson.USER_ID);
        String[] values = MapFactory.createArrayString(userId);
        Map<String, String> json = MapFactory.createMapJson(keys, values);
        Callback callback = new Callback();
        ArrayList<BlackList> blackList = new ArrayList<>();
        RxHelper.performImmediately(() -> {
            ApiService.service.getBlackList(json).enqueue(callback);
            Information info = callback.getInfo();
            int code = info.getCode();
            String content = info.getJson();
            if (code == DefineStatusResponsive.SUCCESS) {
                blackList.addAll(MapperJson.getInstance().convertListObjFromJson(content, BlackList.class));
            }
        });
        return blackList;
    }
}