package com.randomappsinc.simpleloginexample.utils;

import android.app.Application;
import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.squareup.picasso.Picasso;

import java.util.Collections;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;

public final class MyApplication extends Application {

    private static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Iconify.with(new IoniconsModule());
        instance = getApplicationContext();

        // Need to do this to load Facebook profile images from HTTPS
        OkHttpClient client = new OkHttpClient.Builder()
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .build();
        Picasso picasso = new Picasso.Builder(MyApplication.getAppContext())
                .downloader(new OkHttp3Downloader(client))
                .build();
        Picasso.setSingletonInstance(picasso);
    }

    public static Context getAppContext() {
        return instance;
    }
}
