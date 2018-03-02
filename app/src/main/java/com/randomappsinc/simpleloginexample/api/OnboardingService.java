package com.randomappsinc.simpleloginexample.api;

import com.randomappsinc.simpleloginexample.api.models.FacebookLoginRequestBody;
import com.randomappsinc.simpleloginexample.api.models.GoogleLoginRequestBody;
import com.randomappsinc.simpleloginexample.api.models.UserProfileInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OnboardingService {

    @POST("onboarding/facebook")
    Call<UserProfileInfo> loginWithFacebook(@Body FacebookLoginRequestBody facebookRequest);

    @POST("onboarding/google")
    Call<UserProfileInfo> loginWithGoogle(@Body GoogleLoginRequestBody googleRequest);
}
