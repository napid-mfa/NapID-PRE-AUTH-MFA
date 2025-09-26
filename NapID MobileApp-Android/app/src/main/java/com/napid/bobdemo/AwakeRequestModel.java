package com.napid.bobdemo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AwakeRequestModel {

    public AwakeRequestModel(String id, Boolean isEnabled) {
        this.id = id;
        this.isEnabled = isEnabled;
    }

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("isEnabled")
    @Expose
    private Boolean isEnabled;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
