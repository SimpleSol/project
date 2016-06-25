package biz.growapp.helpers;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import biz.growapp.App;


public final class GUIDGenerator {
    private static final String EXCLUDED_ANDROID_ID = "9774d56d682e549c";
    public static final String PREF_DEV_GUID = "PREF_DEV_GUID";
    public static final String PREF_APP_GUID = "PREF_APP_GUID";

    private static final Object UID_LOCK = new Object();

    private static Context getContext() {
        return App.getInstance();
    }

    public static String getDeviceUid() {
        synchronized (UID_LOCK) {
            String result = Prefs.get().getString(PREF_DEV_GUID, null);
            if (result != null) {
                return result;
            }
            final String androidId = Settings.Secure.getString(
                    getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            try {
                if (!EXCLUDED_ANDROID_ID.equals(androidId)) {
                    result = UUID.nameUUIDFromBytes(androidId.getBytes("utf8")).toString();
                } else {
                    TelephonyManager telephonyManager =
                            (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
                    final String deviceId = telephonyManager.getDeviceId();
                    result = deviceId != null ?
                            UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")).toString() :
                            UUID.randomUUID().toString();
                }
                Prefs.edit().putString(PREF_DEV_GUID, result).apply();
                return result;
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }

    public static String getAppUid() {
        synchronized (UID_LOCK) {
            String result = Prefs.get().getString(PREF_APP_GUID, null);
            if (result == null) {
                result = UUID.randomUUID().toString();
                Prefs.edit().putString(PREF_APP_GUID, result).apply();
            }
            return result;
        }
    }
}
