package com.randomappsinc.simpleloginexample.api.callbacks;

import com.randomappsinc.simpleloginexample.api.ApiConstants;
import com.randomappsinc.simpleloginexample.api.models.UserProfileInfo;
import com.randomappsinc.simpleloginexample.onboarding.FacebookLoginManager;
import com.randomappsinc.simpleloginexample.persistence.PreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacebookLoginCallback implements Callback<UserProfileInfo> {

    @Override
    public void onResponse(Call<UserProfileInfo> call, Response<UserProfileInfo> response) {
        if (response.code() == ApiConstants.HTTP_STATUS_OK) {
            PreferencesManager.get().setUserProfile(response.body());
            FacebookLoginManager.get().onFacebookLoginSuccess();
        } else {
            FacebookLoginManager.get().onFacebookLoginError();
        }
    }

    @Override
    public void onFailure(Call<UserProfileInfo> call, Throwable t) {
        FacebookLoginManager.get().onFacebookLoginError();
    }
}
