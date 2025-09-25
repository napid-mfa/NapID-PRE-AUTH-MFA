package com.napid.bobdemo;



import com.napid.bobdemo.Retrofit.Resource;

import retrofit2.Response;

public abstract class BaseRepository {

    protected <T> Resource<T> handleResponse(Response<T> response) {
        if (response.isSuccessful() && response.body() != null) {
            return Resource.success(response.body());
        } else if (response.code() == 503) {
            MaintenanceManager.getInstance().notifyMaintenance();
            return Resource.maintenance("Service under maintenance");
        } else {
            return Resource.error("Error code: " + response.code());
        }
    }

    protected void handleFailure(Throwable t) {
        t.printStackTrace(); // You can log or track this
    }
}
