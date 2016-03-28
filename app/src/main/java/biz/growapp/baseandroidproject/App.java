package biz.growapp.baseandroidproject;


import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import net.danlew.android.joda.JodaTimeAndroid;

import biz.growapp.baseandroidproject.network.RequestManager;
import biz.growapp.baseandroidproject.helpers.Prefs;

public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Prefs.init(this);
        RequestManager.init(this);

        JodaTimeAndroid.init(this);
        Fresco.initialize(this);
    }

    public static Application getInstance() {
        return instance;
    }
}
