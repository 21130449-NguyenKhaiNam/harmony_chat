package com.example.harmony_chat.service;

import com.example.harmony_chat.config.DefineForGson;
import com.example.harmony_chat.config.DefinePathApi;
import com.example.harmony_chat.util.MapperJson;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    ApiService service = new Retrofit.Builder()
            .baseUrl(DefinePathApi.URL)
            .addConverterFactory(GsonConverterFactory.create(MapperJson.getInstance().createBuilder(DefineForGson.formatDate)))
            .build()
            .create(ApiService.class);

    @POST(DefinePathApi.LOGIN)
    Call<DataResponsive> login(@Body Map<String, String> json);

    @POST(DefinePathApi.REGISTER)
    Call<DataResponsive> register(@Body Map<String, String> json);

    @POST(DefinePathApi.FORGET)
    Call<DataResponsive> forget(@Body Map<String, String> json);

    @POST(DefinePathApi.PROFILE)
    Call<DataResponsive> profile(@Body Map<String, String> json);

    @POST(DefinePathApi.UPDATE)
    Call<DataResponsive> update(@Body Map<String, String> json);

    @POST(DefinePathApi.FRIEND_ADD)
    Call<DataResponsive> addFriend(@Body Map<String, String> json);

    @POST(DefinePathApi.FRIEND_UN)
    Call<DataResponsive> unFriend(@Body Map<String, String> json);

    @POST(DefinePathApi.FRIEND_RENAME)
    Call<DataResponsive> renameFriend(@Body Map<String, String> json);

    @POST(DefinePathApi.FRIEND_LIST)
    Call<DataResponsive> listFriend(@Body Map<String, String> json);

    @POST(DefinePathApi.OTHER_PROFILE)
    Call<DataResponsive> otherProfile(@Body Map<String, String> json);

    @POST(DefinePathApi.OTHER_FRIENDS)
    Call<DataResponsive> otherFriends(@Body Map<String, String> json);

    @POST(DefinePathApi.BLACK_LIST)
    Call<DataResponsive> getBlackList(@Body Map<String, String> json);

    @POST(DefinePathApi.ROOM)
    Call<DataResponsive> getRoom(@Body Map<String, String> json);
}