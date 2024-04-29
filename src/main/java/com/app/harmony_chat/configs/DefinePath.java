package com.app.harmony_chat.configs;

public interface DefinePath {
    // Account
    String ACCOUNT = "/api/v1/account";
    String ACCOUNT_LOGIN = "/login";
    String ACCOUNT_REGISTER = "/register";
    String ACCOUNT_FORGET = "/forget";

    // Relationship
    String RELATIONSHIP = "/api/v1/relationship";
    String FRIEND_ADD = "/api/v1/relationship/friend/add";
    String FRIEND_DELETE = "/api/v1/relationship/friend/unfriend";
    String FRIEND_RENAME_NICKNAME = "/api/v1/relationship/friend/rename";
    String FRIEND_LIST = "/api/v1/relationship/friend/list";
    String OTHER_PROFILE = "/api/v1/relationship/other/profile";
    String OTHER_FRIEND = "/api/v1/relationship/other/friends";
}
