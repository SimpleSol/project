package biz.growapp.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public final class Prefs {
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

        @Nullable
        public static String getString(String key) {
            return settings.getString(key, null);
        }

        @Nullable
        public static String getString(String key, @Nullable String defValue) {
            return settings.getString(key, defValue);
        }

        public static int getInt(String key) {
            return getInt(key, 0);
        }

        public static int getInt(String key, int defValue) {
            return settings.getInt(key, defValue);
        }

        public static boolean getBoolean(String key, boolean defValue) {
            return settings.getBoolean(key, defValue);
        }

        public static SharedPreferences.Editor edit() {
            return editor;
        }

        public static void putString(String key, @Nullable String value) {
            edit().putString(key, value).apply();
        }

        public static void putInt(String key, int value) {
            edit().putInt(key, value).apply();
        }

        public static void putBoolean(String key, boolean value) {
            edit().putBoolean(key, value).apply();
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
}
