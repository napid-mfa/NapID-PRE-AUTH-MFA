package com.napid.bobdemo;



import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;


import com.napid.bobdemo.Retrofit.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class HomeViewModel extends AndroidViewModel {
    HomeRepository homeRepository;
    private LiveData<Resource<List<UserIDListResponseModel>>> IDList;
/*    public SingleLiveEvent<Resource<AwakeResponseModel>> awakeResponse = new SingleLiveEvent<>();
    private LiveData<Resource<SleepResponseModel>> sleepResponse = new MutableLiveData<>();
    private LiveData<Resource<KeyRotateResponseModel>> keyRotateResponse = new MutableLiveData<>();*/
    private LiveData<String> stompResponse = new MutableLiveData<>();
    private MutableLiveData<Boolean> showDialog = new MutableLiveData<>();
    private boolean isBiometricFromTimeLapse = false;
    public SingleLiveEvent<Resource<AwakeResponseModel>> awakeResponse = new SingleLiveEvent<>();
    private LiveData<Resource<SleepResponseModel>> sleepResponse = new MutableLiveData<>();
    private LiveData<Resource<StatusResponseModel>> statusResposne = new MutableLiveData<>();



    private Map<String, CountDownTimer> timerMap = new HashMap<>();

    public HomeViewModel(Application application, HomeRepository homeRepository) {
        super(application);
        this.homeRepository = homeRepository;

        loadDataFromRepository();

    }

    private void loadDataFromRepository() {

    }



    public LiveData<Resource<List<UserIDListResponseModel>>> getID(Context context) {
        if (IDList == null) {
            IDList = new MutableLiveData<>();
            loadID(context);

        }else {
            loadID(context);
        }
        return IDList;
    }


    public void loadID(Context context) {

        IDList =  homeRepository.getIDList();


    }

    public void OnIDClicked(String CustomerID){


        awakeResponse =  homeRepository.IdAwake(CustomerID);



    }
    public SingleLiveEvent<Resource<AwakeResponseModel>> checkOncliecked(){
        return awakeResponse;
    }

    public LiveData<Resource<SleepResponseModel>> OnIDsleep(String CustomerID){

        sleepResponse = homeRepository.IDSleep(CustomerID);

        return sleepResponse;
    }

    public LiveData<Resource<StatusResponseModel>> status(String CustomerID){

        statusResposne = homeRepository.Status(CustomerID);

        return statusResposne;
    }





}
