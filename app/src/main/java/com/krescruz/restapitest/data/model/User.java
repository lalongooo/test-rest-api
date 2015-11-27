package com.krescruz.restapitest.data.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    private int mUserId;
    @SerializedName("name")
    private String mUserName;
    @SerializedName("email")
    private String mUserEmail;


    public String getUserEmail() {
        return mUserEmail;
    }

    public void setUserEmail(String userEmail) {
        this.mUserEmail = userEmail;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        this.mUserId = userId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        this.mUserName = userName;
    }
}