package com.randomappsinc.simpleloginexample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.CallbackManager;
import com.randomappsinc.simpleloginexample.R;
import com.randomappsinc.simpleloginexample.onboarding.FacebookLoginManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnboardingActivity extends AppCompatActivity {

    @BindView(R.id.facebook_button) View facebookLoginButton;

    private CallbackManager callbackManager;
    private FacebookLoginManager facebookLoginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        callbackManager = CallbackManager.Factory.create();
        facebookLoginManager = FacebookLoginManager.get();
        facebookLoginManager.setUpLogin(callbackManager, facebookLoginButton, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
