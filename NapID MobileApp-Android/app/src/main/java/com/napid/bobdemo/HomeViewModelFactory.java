package com.napid.bobdemo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class HomeViewModelFactory implements ViewModelProvider.Factory{
    private final Application application;
    private final HomeRepository homeRepository;

    public HomeViewModelFactory(Application application, HomeRepository homeRepository){
        this.application = application;
        this.homeRepository = homeRepository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(application,homeRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
