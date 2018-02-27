package com.randomappsinc.simpleloginexample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.randomappsinc.simpleloginexample.R;
import com.randomappsinc.simpleloginexample.onboarding.FacebookLoginManager;
import com.randomappsinc.simpleloginexample.onboarding.GoogleLoginManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OnboardingActivity extends AppCompatActivity {

    @BindView(R.id.facebook_button) View facebookLoginButton;

    private FacebookLoginManager facebookLoginManager;
    private GoogleLoginManager googleLoginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        facebookLoginManager = FacebookLoginManager.get();
        googleLoginManager = GoogleLoginManager.get();
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
        public void onLoginSuccessful() {

        }

        @Override
        public void onLoginFailed() {

        }
    };

    private final GoogleLoginManager.Listener googleLoginListener = new GoogleLoginManager.Listener() {
        @Override
        public void onLoginSuccessful() {

        }

        @Override
        public void onLoginFailed() {

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        facebookLoginManager.unregisterListener();
        googleLoginManager.unregisterListener();
    }
}
