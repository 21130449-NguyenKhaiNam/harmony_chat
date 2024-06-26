package com.example.harmony_chat.config;

public interface DefineStatusResponsive {
    int SUCCESS = 200;
    int SUCCESS_BUT_NOT_CORRECT = 201;
    int SUCCESS_BUT_NOT_FOUND = 202;
    int SUCCESS_BUT_HAS_ACCOUNT = 203;
    int WARNING = 300;
    int ERROR_CLIENT = 400;
    int ERROR_SERVER = 500;

    // Nội dung mặc định
    String DEFAULT_ERROR_ACCOUNT = "Xuất hiện nhiều hơn 1 tài khoản!!!";
    String DEFAULT_NOT_CORRECT_ACCOUNT = "Tài khoản không đúng";
    String DEFAULT_NOT_ACCOUNT = "Tài khoản không tồn tại";
    String DEFAULT_HAS_ACCOUNT = "Tài khoản đã tồn tại";
    String DEFAULT_NOT_CORRECT_FILEDS = "Có trường nhập không đủ thông tin";
    String DEFAULT_HAS_FRIEND = "Đã kết bạn";
    String DEFAULT_UN_FRIEND_SUCCESS = "Hủy kết bạn thành công";
    String DEFAULT_RENAME_NICKNAME_FRIEND = "Đổi biệt danh thành công";
    String DEFAULT_NO_FRIEND = "Danh sách bạn bè trống";

    String EMPTY = "empty";
}
