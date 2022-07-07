package com.dudu.dictation;
import android.app.Application;
import android.content.Context;

public class AppContext extends Application {

    private static Context instance;

    @Override
    public void onCreate() 
    {
        instance = getApplicationContext();
    }

    public static Context getContext()
    {
        return instance;
    }

}
