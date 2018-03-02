package com.randomappsinc.simpleloginexample.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.randomappsinc.simpleloginexample.api.models.UserProfileInfo;
import com.randomappsinc.simpleloginexample.utils.MyApplication;

public class PreferencesManager {

    private SharedPreferences prefs;

    // User profile
    private static final String USER_NAME_KEY = "userName";
    private static final String USER_EMAIL_KEY = "userEmail";
    private static final String USER_PROFILE_PICTURE_URL_KEY = "userProfilePictureUrl";

    private static PreferencesManager instance;

    public static PreferencesManager get() {
        if (instance == null) {
            instance = getSync();
        }
        return instance;
    }

    private static synchronized PreferencesManager getSync() {
        if (instance == null) {
            instance = new PreferencesManager();
        }
        return instance;
    }

    private PreferencesManager() {
        Context context = MyApplication.getAppContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isUserLoggedIn() {
        return TextUtils.isEmpty(prefs.getString(USER_NAME_KEY, ""));
    }

    public UserProfileInfo getUserProfile() {
        String name = prefs.getString(USER_NAME_KEY, "");
        String email = prefs.getString(USER_EMAIL_KEY, "");
        String profilePictureUrl = prefs.getString(USER_PROFILE_PICTURE_URL_KEY, "");

        UserProfileInfo profileInfo = new UserProfileInfo();
        profileInfo.setName(name);
        profileInfo.setEmail(email);
        profileInfo.setProfilePictureUrl(profilePictureUrl);
        return profileInfo;
    }

    public void setUserProfile(UserProfileInfo profileInfo) {
        prefs.edit()
                .putString(USER_NAME_KEY, profileInfo.getName())
                .putString(USER_EMAIL_KEY, profileInfo.getEmail())
                .putString(USER_PROFILE_PICTURE_URL_KEY, profileInfo.getProfilePictureUrl())
                .apply();
    }
}
