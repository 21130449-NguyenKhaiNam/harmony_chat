package com.app.harmony_chat.configs;

/**
 * Giúp định nghĩa các thông tin cho lớp Infomation
 */
public interface DefineInfomation {
    // Mã thông báo
    int SUCCESS = 200;
    int WARNING = 300;
    int ERROR_CLIENT = 400;
    int ERROR_SERVER = 500;

    // Nội dung mặc định
    String DEFAULT_SUCCESS = "Thực hiện thành công";
    String DEFAULT_WARNING = "Có cảnh báo";
    String DEFAULT_ERROR = "Thực hiện không thành công";
    String DEFAULT_ERROR_ACCOUNT = "Xuất hiện nhiều hơn 1 tài khoản!!!";
    String DEFAULT_NOT_CORRECT_ACCOUNT = "Tài khoản không đúng";
    String DEFAULT_NOT_ACCOUNT = "Tài khoản không tồn tại";
    String DEFAULT_HAS_ACCOUNT = "Tài khoản đã tồn tại";
    String DEFAULT_NOT_CORRECT_FILEDS = "Có trường nhập không đủ thông tin";

    String EMPTY = "empty";
}
