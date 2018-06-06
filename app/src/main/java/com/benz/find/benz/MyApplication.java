package com.benz.find.benz;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.UUID;

public class MyApplication extends Application {

    public static MyApplication THIS;

    public static Context getAppContext() {
        return THIS.getApplicationContext();
    }

    public static MyApplication getInstance() {
        return THIS;
    }

    public static boolean isDebugMode() {
        return false;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        THIS = this;
    }

    /**
     * 获取VersionCode
     *
     * @return
     * @throws Exception
     */
    public int getVersionCode() {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            int code = packInfo.versionCode;
            return code;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getUuid() {

        return "G-" + getDeviceId().toString();
    }

    public String getLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= 24) {
            locale = getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = getResources().getConfiguration().locale;

        }
        return locale.getLanguage();
    }

    public UUID uuid = null;
    protected static final String PREFS_FILE = "device_id.xml";
    protected static final String PREFS_DEVICE_ID = "device_id";

    public UUID getDeviceId() {
        if (uuid == null) {
            final SharedPreferences prefs = getSharedPreferences(PREFS_FILE, 0);
            final String id = prefs.getString(PREFS_DEVICE_ID, null);
            if (id != null) {
                // Use the ids previously computed and stored in the prefs file
                uuid = UUID.fromString(id);
            } else {
                final String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                // Use the Android ID unless it's broken, in which case fallback on deviceId,
                // unless it's not available, then fallback on a random number which we store
                // to a prefs file
                try {
                    if (!"9774d56d682e549c".equals(androidId)) {
                        try {
                            uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    } else {
                        final String deviceId = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                        uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.fromString("");
                    }

                } catch (UnsupportedEncodingException e) {
                    uuid = UUID.fromString("");
                }
                // Write the value out to the prefs file
                prefs.edit().putString(PREFS_DEVICE_ID, uuid.toString()).commit();
            }
        }
        return uuid;
    }

}
