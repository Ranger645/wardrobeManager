package com.android.wardrobeManager;

import android.app.Application;
import android.content.Context;


public class WardrobeManager extends Application {
    private static WardrobeManager instance;

    public static WardrobeManager getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance;
        // or return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
