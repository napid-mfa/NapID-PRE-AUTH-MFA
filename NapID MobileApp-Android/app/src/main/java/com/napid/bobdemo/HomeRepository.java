package com.napid.bobdemo;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.napid.bobdemo.Retrofit.ApiInterface;
import com.napid.bobdemo.Retrofit.NetworkClass;
import com.napid.bobdemo.Retrofit.Resource;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeRepository extends BaseRepository{
    Retrofit retrofit = NetworkClass.callRetrofit();
    private final ApiInterface apiInterface = retrofit.create(ApiInterface.class);
    Context context;
    private final MutableLiveData<Boolean> _biometricStatus = new MutableLiveData<>();
    //SingleLiveEvent<Resource<AwakeResponseModel>> respone = new SingleLiveEvent<>();
    SingleLiveEvent<Resource<AwakeResponseModel>> respone = new SingleLiveEvent<>();


    public LiveData<Resource<List<UserIDListResponseModel>>> getIDList() {

        MutableLiveData<Resource<List<UserIDListResponseModel>>> data = new MutableLiveData<>();

                Call<List<UserIDListResponseModel>> call = apiInterface.UserList();
                call.enqueue(new Callback<List<UserIDListResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<UserIDListResponseModel>>call, Response<List<UserIDListResponseModel>> response) {



                            Log.e("Application List",""+response);
                            data.postValue(handleResponse(response));



                    }

                    @Override
                    public void onFailure(Call<List<UserIDListResponseModel>> call, Throwable throwable) {
                        //log.e("response","failed"+throwable);
                    }
                });


        return data;
    }
    public SingleLiveEvent<Resource<AwakeResponseModel>> IdAwake(String customerId){








        AwakeRequestModel awake = null;



            awake = new AwakeRequestModel(customerId,true);
            Log.e("customerId",customerId);
            //log.e("json",json);
            Call <AwakeResponseModel> call = apiInterface.IDAwake("",awake);
            call.enqueue(new Callback<AwakeResponseModel>() {
                @Override
                public void onResponse(Call<AwakeResponseModel> call, Response<AwakeResponseModel> response) {
                    if(response.isSuccessful()&&response.code()==200){
                        assert response.body() != null;

                        if (response.body().getStatusCode().equalsIgnoreCase("SUCCESS")){

                            respone.postValue(handleResponse(response));

                        }
                    }else {
                        Log.e("getStatusCode",""+response.body().getStatusCode());
                    }


                }

                @Override
                public void onFailure(Call<AwakeResponseModel> call, Throwable throwable) {
                    //log.e("Log","failed"+throwable);
                }
            });





        return respone;
    }

    public LiveData<Resource<SleepResponseModel>> IDSleep(String customerId){

        MutableLiveData<Resource<SleepResponseModel>> respone = new MutableLiveData<>();
        SleepRequestModel sleep = null;



            sleep = new SleepRequestModel(customerId,false);

        Call <SleepResponseModel> call = apiInterface.IDSleep("",sleep);
        call.enqueue(new Callback<SleepResponseModel>() {
            @Override
            public void onResponse(Call<SleepResponseModel> call, Response<SleepResponseModel> response) {

                if (response.isSuccessful()){

                    respone.postValue(handleResponse(response));
                }
            }

            @Override
            public void onFailure(Call<SleepResponseModel> call, Throwable throwable) {

            }
        });

        return respone;
    }

    public LiveData<Resource<StatusResponseModel>> Status(String customerId){

        MutableLiveData<Resource<StatusResponseModel>> respone = new MutableLiveData<>();





        Call <StatusResponseModel> call = apiInterface.status(customerId);
        call.enqueue(new Callback<StatusResponseModel>() {
            @Override
            public void onResponse(Call<StatusResponseModel> call, Response<StatusResponseModel> response) {

                if (response.isSuccessful()){

                    respone.postValue(handleResponse(response));
                }
            }

            @Override
            public void onFailure(Call<StatusResponseModel> call, Throwable throwable) {

            }
        });

        return respone;
    }





}
