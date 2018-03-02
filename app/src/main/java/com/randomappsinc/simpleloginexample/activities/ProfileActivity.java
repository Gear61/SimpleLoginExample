package com.randomappsinc.simpleloginexample.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.randomappsinc.simpleloginexample.R;
import com.randomappsinc.simpleloginexample.api.models.UserProfileInfo;
import com.randomappsinc.simpleloginexample.persistence.PreferencesManager;
import com.randomappsinc.simpleloginexample.utils.MyApplication;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

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
}
