package com.app.harmony_chat.configs;

public interface DefinePath {
    // Account
    String ACCOUNT = "/api/v1/account";
    String ACCOUNT_LOGIN = "/login";
    String ACCOUNT_REGISTER = "/register";
    String ACCOUNT_FORGET = "/forget";
    String ACCOUNT_PROFILE = "/profile";
    String ACCOUNT_PROFILE_UPDATE = "/profile/update";

    // Relationship
    String RELATIONSHIP = "/api/v1/relationship";
    String FRIEND_ADD = "/friend/add";
    String FRIEND_DELETE = "/friend/unfriend";
    String FRIEND_RENAME_NICKNAME = "/friend/rename";
    String FRIEND_LIST = "/friend/list";

    // Other
    String OTHER_PROFILE = "/other/profile";
    String OTHER_FRIEND = "/other/friends";

    // View
    String SEARCH = "/api/v1/search";
    String SEARCH_FRIEND_MESS = "/friend/mess";
}
