package com.example.harmony_chat.config;

public interface DefinePathApi {
    //    String URL = "http://192.168.1.58:8080/";
    String URL = "http://10.0.2.2:8080";
    String LOGIN = "/api/v1/account/login";
    String REGISTER = "/api/v1/account/register";
    String FORGET = "/api/v1/account/forget";
    String PROFILE = "/api/v1/account/profile";
    String UPDATE = "/api/v1/account/profile/update";
    String FRIEND_ADD = "/api/v1/relationship/friend/add";
    String FRIEND_UN = "/api/v1/relationship/friend/unfriend";
    String FRIEND_RENAME = "/api/v1/relationship/friend/rename";
    String FRIEND_LIST = "/api/v1/relationship/friend/list";
    String OTHER_PROFILE = "/api/v1/relationship/other/profile";
    String OTHER_FRIENDS = "/api/v1/relationship/other/friends";
    String SEARCH_FRIEND_MESS = "/api/v1/search/friend/mess";
    String BLACK_LIST = "/api/v1/account/profile/blacklist";
    String ROOM = "/api/v1/relationship/room";
    String ROOM_ADD = "/api/v1/relationship/room/add";
    String ROOM_LIST = "/api/v1/relationship/room/list";
    String IMAGE_UPLOAD = "/api/v1/image/upload";
}
