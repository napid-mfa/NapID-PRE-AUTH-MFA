package com.napid.bobdemo.Retrofit;


import com.napid.bobdemo.Status;

public class Resource<T> {
    public final Status status;
    public final T data;
    public final String message;

    private Resource(Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String message) {
        return new Resource<>(Status.ERROR, null, message);
    }

    public static <T> Resource<T> maintenance(String message) {
        return new Resource<>(Status.MAINTENANCE, null, message);
    }
}
