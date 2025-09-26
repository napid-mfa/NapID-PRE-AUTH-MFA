package com.napid.bobdemo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MaintenanceManager {
    private static MaintenanceManager instance;
    private final MutableLiveData<Boolean> maintenanceLiveData = new MutableLiveData<>();

    private MaintenanceManager() {}

    public static synchronized MaintenanceManager getInstance() {
        if (instance == null) {
            instance = new MaintenanceManager();
        }
        return instance;
    }

    public LiveData<Boolean> getMaintenanceLiveData() {
        return maintenanceLiveData;
    }

    public void notifyMaintenance() {
        maintenanceLiveData.postValue(true);
    }
}
