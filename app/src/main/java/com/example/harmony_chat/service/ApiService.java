package com.example.harmony_chat.service;

import com.example.harmony_chat.config.DefineForGson;
import com.example.harmony_chat.config.DefinePathApi;
import com.example.harmony_chat.model.Information;
import com.example.harmony_chat.util.MapperJson;
import java.util.Map;
import java.util.Observer;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    ApiService service = new Retrofit.Builder()
            .baseUrl(DefinePathApi.URL)
            .addConverterFactory(GsonConverterFactory.create(MapperJson.getInstance().createBuilder(DefineForGson.formatDate)))
            .build()
            .create(ApiService.class);

    @POST(DefinePathApi.LOGIN)
    Call<Information> login(@Body Map<String, String> json);

    @POST(DefinePathApi.REGISTER)
    Call<Information> register(@Body Map<String, String> json);

    @POST(DefinePathApi.FORGET)
    Call<Information> forget(@Body Map<String, String> json);

    @POST(DefinePathApi.PROFILE)
    Call<Information> profile(@Body Map<String, String> json);

    @POST(DefinePathApi.UPDATE)
    Call<Information> update(@Body Map<String, String> json);

    @POST(DefinePathApi.FRIEND_ADD)
    Call<Information> addFriend(@Body Map<String, String> json);

    @POST(DefinePathApi.FRIEND_UN)
    Call<Information> unFriend(@Body Map<String, String> json);

    @POST(DefinePathApi.FRIEND_RENAME)
    Call<Information> renameFriend(@Body Map<String, String> json);

    @POST(DefinePathApi.FRIEND_LIST)
    Call<Information> listFriend(@Body Map<String, String> json);

    @GET(DefinePathApi.OTHER_PROFILE)
    Call<Information> otherProfile(@Body Map<String, String> json);

    @GET(DefinePathApi.OTHER_FRIENDS)
    Call<Information> otherFriends(@Body Map<String, String> json);

    @GET(DefinePathApi.SEARCH_FRIEND_MESS)
    Call<Information> searchFriendMess(@Body Map<String, String> json);

    @GET(DefinePathApi.BLACK_LIST)
    Call<Information> getBlackList(@Body Map<String, String> json);
}
