package com.randomappsinc.simpleloginexample.onboarding;

import android.app.Activity;
import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.randomappsinc.simpleloginexample.R;
import com.randomappsinc.simpleloginexample.utils.UIUtils;

import java.util.Arrays;
import java.util.List;

public class FacebookLoginManager {

    private static final List<String> FACEBOOK_PERMISSIONS = Arrays.asList("public_profile", "email");

    public interface Listener {
        void onLoginSuccessful();

        void onLoginFailed();
    }

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

    private CallbackManager callbackManager;
    private Listener listener;

    private FacebookLoginManager() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, facebookCallback);
    }

    public void loginWithFacebook(Activity activity, Listener listener) {
        this.listener = listener;
        LoginManager.getInstance().logInWithReadPermissions(activity, FACEBOOK_PERMISSIONS);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void unregisterListener() {
        listener = null;
    }

    private final FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            String accessToken = loginResult.getAccessToken().getToken();
            // TODO: Send access token to server to onboard
        }

        @Override
        public void onCancel() {}

        @Override
        public void onError(FacebookException error) {
            listener.onLoginFailed();
            UIUtils.showLongToast(R.string.facebook_login_fail);
        }
    };
}
