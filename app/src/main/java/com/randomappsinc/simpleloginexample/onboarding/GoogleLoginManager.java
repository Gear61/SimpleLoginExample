package com.randomappsinc.simpleloginexample.onboarding;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.randomappsinc.simpleloginexample.R;
import com.randomappsinc.simpleloginexample.utils.UIUtils;

public class GoogleLoginManager {

    // If you are using this class, don't have an activity request code with the same value!
    public static final int GOOGLE_SIGN_IN_CODE = 350;

    private static final String SERVER_OAUTH_ID = "956612316816-n23cs49obd4fmn1qgs4abhqs7t3f6fnd" +
            ".apps.googleusercontent.com";

    public interface Listener {
        // Invoked when we have successfully fetched the token and are onboarding with our server
        void onLoginStart();

        void onLoginSuccessful();

        void onLoginFailed();
    }

    private static GoogleLoginManager instance;

    public static GoogleLoginManager get() {
        if (instance == null) {
            instance = getSync();
        }
        return instance;
    }

    private static synchronized GoogleLoginManager getSync() {
        if (instance == null) {
            instance = new GoogleLoginManager();
        }
        return instance;
    }

    private GoogleSignInOptions signInOptions;
    private Listener listener;

    private GoogleLoginManager() {
        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(SERVER_OAUTH_ID)
                .build();
    }

    public void loginWithGoogle(Activity activity) {
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(activity, signInOptions);
        activity.startActivityForResult(googleSignInClient.getSignInIntent(), GOOGLE_SIGN_IN_CODE);
    }

    public void processSignInResult(Intent data, Listener listener) {
        this.listener = listener;
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            String idToken = account.getIdToken();
            Log.d("Google Token", idToken);
            // RestClient.getInstance().loginWithGoogle(idToken);
            // listener.onLoginStart();
        } catch (ApiException exception) {
            int errorCode = exception.getStatusCode();
            switch (errorCode) {
                case GoogleSignInStatusCodes.INVALID_ACCOUNT:
                    UIUtils.showLongToast(R.string.google_login_invalid_account);
                    break;
                case GoogleSignInStatusCodes.NETWORK_ERROR:
                    UIUtils.showLongToast(R.string.google_login_network_issue);
                    break;
                default:
                    UIUtils.showLongToast(R.string.google_login_fail);
                    break;
            }
        }
    }

    public void onGoogleLoginSuccess() {
        listener.onLoginSuccessful();
    }

    public void onGoogleLoginError() {
        UIUtils.showLongToast(R.string.google);
        listener.onLoginFailed();
    }

    public void unregisterListener() {
        listener = null;
    }

    public void logOut(Activity activity) {
        GoogleSignIn.getClient(activity, signInOptions).signOut();
    }
}
