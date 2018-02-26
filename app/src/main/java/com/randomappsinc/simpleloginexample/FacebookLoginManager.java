package com.randomappsinc.simpleloginexample;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;
import java.util.List;

public class FacebookLoginManager {

    private static final List<String> FACEBOOK_PERMISSIONS = Arrays.asList("public_profile", "email");

    private static FacebookLoginManager instance;

    public static FacebookLoginManager get() {
        if (instance == null) {
            instance = getSync();
        }
        return instance;
    }

    private static synchronized FacebookLoginManager getSync() {
        if (instance == null) {
            instance = new FacebookLoginManager();
        }
        return instance;
    }

    public void setUpLogin(CallbackManager callbackManager, View loginButton, final Activity activity) {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(activity, FACEBOOK_PERMISSIONS);
            }
        });
        LoginManager.getInstance().registerCallback(callbackManager, facebookCallback);
    }

    private final FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            String accessToken = loginResult.getAccessToken().getToken();
            Toast.makeText(MyApplication.getAppContext(), accessToken, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {}

        @Override
        public void onError(FacebookException error) {

        }
    };
}
