package com.randomappsinc.simpleloginexample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.randomappsinc.simpleloginexample.R;
import com.randomappsinc.simpleloginexample.onboarding.FacebookLoginManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OnboardingActivity extends AppCompatActivity {

    private static final int GOOGLE_SIGN_IN_CODE = 1;
    private static final String SERVER_OAUTH_ID = "956612316816-n23cs49obd4fmn1qgs4abhqs7t3f6fnd.apps.googleusercontent.com";

    @BindView(R.id.facebook_button) View facebookLoginButton;

    private CallbackManager callbackManager;
    private FacebookLoginManager facebookLoginManager;

    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        callbackManager = CallbackManager.Factory.create();
        facebookLoginManager = FacebookLoginManager.get();
        facebookLoginManager.setUpLogin(callbackManager, facebookLoginButton, this);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(SERVER_OAUTH_ID)
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, signInOptions);
    }

    @OnClick(R.id.google_button)
    public void loginWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN_CODE) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.w("OnboardingActivity", account.getIdToken());
            Toast.makeText(this, account.getIdToken(), Toast.LENGTH_LONG).show();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(this, "Google log failed with reason: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.w("OnboardingActivity", "signInResult:failed code=" + e.getStatusCode());
        }
    }
}
