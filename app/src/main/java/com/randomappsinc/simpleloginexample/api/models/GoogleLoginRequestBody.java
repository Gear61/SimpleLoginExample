package com.randomappsinc.simpleloginexample.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoogleLoginRequestBody {

    @SerializedName("id_token")
    @Expose
    private String idToken;

    public GoogleLoginRequestBody(String idToken) {
        this.idToken = idToken;
    }
}
