package com.example.harmony_chat.util;

public class CheckInfomation {

    /**
     * Kiểm tra 1 phần tử với tất cả
     * @param isAnd - Chỉ định phép toán and hay or
     * @param condition
     * @param params
     * @return
     */
    public static boolean checkOneWithAll(boolean isAnd, Object condition, Object... params) {
        if (isEmpty(condition, params))
            return false;
        for (Object param : params) {
            if (isAnd) {
                if(!isEmpty(param) && !param.equals(condition))
                    return false;
            } else {
                if(!isEmpty(param) && param.equals(condition))
                    return true;
            }
        }
        return isAnd;
    }

    // Kiểm tra có giá trị nào rỗng không
    public static boolean isEmpty(Object... params) {
        if (params == null)
            return true;
        for (Object param : params) {
            if (param == null)
                return true;
        }
        return false;
    }
}
