package com.randomappsinc.simpleloginexample.api;

import android.os.Handler;
import android.os.HandlerThread;

import com.randomappsinc.simpleloginexample.api.callbacks.FacebookLoginCallback;
import com.randomappsinc.simpleloginexample.api.models.FacebookLoginRequestBody;
import com.randomappsinc.simpleloginexample.api.models.UserProfileInfo;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private static RestClient instance;

    private OnboardingService onboardingService;
    private Handler handler;

    private Call<UserProfileInfo> currentLoginCall;

    public static RestClient getInstance() {
        if (instance == null) {
            instance = new RestClient();
        }
        return instance;
    }

    private RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        onboardingService = retrofit.create(OnboardingService.class);

        HandlerThread backgroundThread = new HandlerThread("");
        backgroundThread.start();
        handler = new Handler(backgroundThread.getLooper());
    }

    public void loginWithFacebook(final String accessToken) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (currentLoginCall != null) {
                    currentLoginCall.cancel();
                }
                FacebookLoginRequestBody requestBody = new FacebookLoginRequestBody(accessToken);
                currentLoginCall = onboardingService.loginWithFacebook(requestBody);
                currentLoginCall.enqueue(new FacebookLoginCallback());
            }
        });
    }
}
