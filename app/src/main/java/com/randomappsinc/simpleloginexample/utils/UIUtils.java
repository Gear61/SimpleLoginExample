package com.randomappsinc.simpleloginexample.utils;

import android.support.annotation.StringRes;
import android.widget.Toast;

public class UIUtils {

    public static void showLongToast(@StringRes int messageId) {
        Toast.makeText(MyApplication.getAppContext(), messageId, Toast.LENGTH_LONG).show();
    }
}
