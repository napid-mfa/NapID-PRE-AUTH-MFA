package com.napid.bobdemo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserIDListResponseModel {
    @SerializedName("applicationName")
    @Expose
    private String appName;
    @SerializedName("digitalID")
    @Expose
    private String digitalId;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("isEnabled")
    @Expose
    private Boolean isEnabled;
    @SerializedName("alias")
    @Expose
    private String alias;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDigitalId() {
        return digitalId;
    }

    public void setDigitalId(String digitalId) {
        this.digitalId = digitalId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
