package com.randomappsinc.simpleloginexample.utils;

import android.widget.Toast;

import androidx.annotation.StringRes;

public class UIUtils {

    public static void showLongToast(@StringRes int messageId) {
        Toast.makeText(MyApplication.getAppContext(), messageId, Toast.LENGTH_LONG).show();
    }
}
