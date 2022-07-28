package com.dudu.dictation;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class AppContext extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = getApplicationContext();
    }

    public static Context getContext()
    {
        return instance;
    }

}
