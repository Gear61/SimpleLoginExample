package com.randomappsinc.simpleloginexample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.login.LoginManager;
import com.randomappsinc.simpleloginexample.R;
import com.randomappsinc.simpleloginexample.api.models.UserProfileInfo;
import com.randomappsinc.simpleloginexample.onboarding.GoogleLoginManager;
import com.randomappsinc.simpleloginexample.persistence.PreferencesManager;
import com.randomappsinc.simpleloginexample.utils.MyApplication;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.user_image) ImageView userImage;
    @BindView(R.id.user_name) TextView userName;
    @BindView(R.id.user_email) TextView userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        ButterKnife.bind(this);

        UserProfileInfo profileInfo = PreferencesManager.get().getUserProfile();
        Picasso.with(MyApplication.getAppContext()).load(profileInfo.getProfilePictureUrl()).into(userImage);
        userName.setText(profileInfo.getName());
        userEmail.setText(profileInfo.getEmail());
    }

    private void onLogOutConfirmed() {
        LoginManager.getInstance().logOut();
        GoogleLoginManager.get().logOut(this);
        PreferencesManager.get().logOut();
        startActivity(new Intent(this, OnboardingActivity.class));
        finish();
    }

    @OnClick(R.id.log_out)
    public void logOut() {
        new MaterialDialog.Builder(this)
                .title(R.string.log_out_title)
                .content(R.string.log_out_body)
                .positiveText(R.string.yes)
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        onLogOutConfirmed();
                    }
                })
                .show();
    }
}
