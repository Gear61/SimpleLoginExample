package com.randomappsinc.simpleloginexample.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacebookLoginRequestBody {

    @SerializedName("access_token")
    @Expose
    private String accessToken;

    public FacebookLoginRequestBody(String accessToken) {
        this.accessToken = accessToken;
    }
}
