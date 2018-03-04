package com.randomappsinc.simpleloginexample.api.callbacks;

import com.randomappsinc.simpleloginexample.api.ApiConstants;
import com.randomappsinc.simpleloginexample.api.models.UserProfileInfo;
import com.randomappsinc.simpleloginexample.onboarding.GoogleLoginManager;
import com.randomappsinc.simpleloginexample.persistence.PreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoogleLoginCallback implements Callback<UserProfileInfo> {

    @Override
    public void onResponse(Call<UserProfileInfo> call, Response<UserProfileInfo> response) {
        if (response.code() == ApiConstants.HTTP_STATUS_OK) {
            PreferencesManager.get().setUserProfile(response.body());
            GoogleLoginManager.get().onGoogleLoginSuccess();
        } else {
            GoogleLoginManager.get().onGoogleLoginError();
        }
    }

    @Override
    public void onFailure(Call<UserProfileInfo> call, Throwable t) {
        GoogleLoginManager.get().onGoogleLoginError();
    }
}
