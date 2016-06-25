package biz.growapp.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public final class Prefs {
    private Prefs() {
    }

    private static SharedPreferences settings;
    private static SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public static void init(Context context) {
        settings = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        editor = settings.edit();
    }

    public static SharedPreferences get() {
        return settings;
    }

    public static SharedPreferences.Editor edit() {
        return editor;
    }

    public static void clear() {
        editor.clear().apply();
    }

    public static void remove(String ... keys) {
        for (String key : keys) {
            editor.remove(key);
        }
        editor.apply();
    }
}
