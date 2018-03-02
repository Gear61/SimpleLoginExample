package com.randomappsinc.simpleloginexample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.randomappsinc.simpleloginexample.R;
import com.randomappsinc.simpleloginexample.onboarding.FacebookLoginManager;
import com.randomappsinc.simpleloginexample.onboarding.GoogleLoginManager;
import com.randomappsinc.simpleloginexample.persistence.PreferencesManager;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OnboardingActivity extends AppCompatActivity {

    private FacebookLoginManager facebookLoginManager;
    private GoogleLoginManager googleLoginManager;
    private MaterialDialog loginProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!PreferencesManager.get().isUserLoggedIn()) {
            startActivity(new Intent(this, ProfileActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.onboarding);
        ButterKnife.bind(this);

        facebookLoginManager = FacebookLoginManager.get();
        googleLoginManager = GoogleLoginManager.get();

        loginProgressDialog = new MaterialDialog.Builder(this)
                .progress(true, 0)
                .cancelable(false)
                .build();
    }

    @OnClick(R.id.facebook_button)
    public void loginWithFacebook() {
        facebookLoginManager.loginWithFacebook(this, facebookLoginListener);
    }

    @OnClick(R.id.google_button)
    public void loginWithGoogle() {
        googleLoginManager.loginWithGoogle(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookLoginManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GoogleLoginManager.GOOGLE_SIGN_IN_CODE) {
            googleLoginManager.processSignInResult(data, googleLoginListener);
        }
    }

    private final FacebookLoginManager.Listener facebookLoginListener = new FacebookLoginManager.Listener() {
        @Override
        public void onLoginStart() {
            loginProgressDialog.setContent(R.string.logging_in_facebook);
            loginProgressDialog.show();
        }

        @Override
        public void onLoginSuccessful() {
            transitionToProfile();
        }

        @Override
        public void onLoginFailed() {
            loginProgressDialog.dismiss();
        }
    };

    private final GoogleLoginManager.Listener googleLoginListener = new GoogleLoginManager.Listener() {
        @Override
        public void onLoginStart() {
            loginProgressDialog.setContent(R.string.logging_in_google);
            loginProgressDialog.show();
        }

        @Override
        public void onLoginSuccessful() {
            transitionToProfile();
        }

        @Override
        public void onLoginFailed() {
            loginProgressDialog.dismiss();
        }
    };

    private void transitionToProfile() {
        loginProgressDialog.dismiss();
        startActivity(new Intent(this, ProfileActivity.class));
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (facebookLoginManager != null) {
            facebookLoginManager.unregisterListener();
        }
        if (googleLoginManager != null) {
            googleLoginManager.unregisterListener();
        }
        if (loginProgressDialog != null) {
            loginProgressDialog.dismiss();
        }
    }
}
