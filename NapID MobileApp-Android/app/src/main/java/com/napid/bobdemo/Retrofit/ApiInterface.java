package com.napid.bobdemo.Retrofit;

import com.napid.bobdemo.AwakeRequestModel;
import com.napid.bobdemo.AwakeResponseModel;
import com.napid.bobdemo.SleepRequestModel;
import com.napid.bobdemo.SleepResponseModel;
import com.napid.bobdemo.StatusResponseModel;
import com.napid.bobdemo.UserIDListResponseModel;

import java.util.List;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

import retrofit2.http.Headers;

import retrofit2.http.PUT;

import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {






    @Headers("Content-Type: application/json")
    @GET(URLHELPER.userListUrl)
    Call<List<UserIDListResponseModel>> UserList();

    @Headers("Content-Type: application/json")
    @PUT(URLHELPER.awakeUrl)
    Call<AwakeResponseModel> IDAwake(@Query("") String key, @Body AwakeRequestModel body);

    @Headers("Content-Type: application/json")
    @PUT(URLHELPER.awakeUrl)
    Call<SleepResponseModel> IDSleep(@Query("") String key, @Body SleepRequestModel body);
    @Headers("Content-Type: application/json")
    @GET(URLHELPER.status+"{id}")
    Call<StatusResponseModel> status(@Path(value = "id",encoded = false) String id);


}
