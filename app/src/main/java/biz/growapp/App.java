package biz.growapp;


import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.jakewharton.threetenabp.AndroidThreeTen;

import biz.growapp.helpers.Prefs;
import biz.growapp.network.RequestManager;

public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Prefs.init(this);
        RequestManager.init(this);

        AndroidThreeTen.init(this);
        Fresco.initialize(this);
    }

    public static Application getInstance() {
        return instance;
    }
}
