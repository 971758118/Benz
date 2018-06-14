package com.benz.find.apimanager.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;


import com.benz.find.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.HttpUrl;


public class CookieManager {

    public static CookieManager getInstance() {
        return instance;
    }

    private static final String COOKIE_PREFS = "cookie_prefs";
    private final String KEY_USER_SEX = "sex";
    private final String KEY_USER_SKIN = "skinColor";
    private final String KEY_COUNTRY = "country";
    private final String KEY_CLT_ID = "clt_id";
    private final String KEY_APP_VER = "app_ver";
    private final String KEY_DATA_VER = "version";
    private final String KEY_UUID = "uuid";
    private final String KEY_SDK_INT = "sdk_int";
    private final String KEY_SDK_VER = "sdk_ver";
    private final String V_CLT_ID = "2";//android
    private final String KEY_APP = "app";
    private final String ISVIP = "isvip";

    private final static CookieManager instance = new CookieManager(MyApplication.getAppContext());
    private final Map<String, String> cookies;//存cookie的键值对
    private final SharedPreferences cookiePrefs;

    private CookieManager(Context context) {
        this.cookies = new HashMap<>();
        this.cookiePrefs = context.getSharedPreferences(COOKIE_PREFS, 0);
    }

    public void saveCookies(HttpUrl url, List<Cookie> cookies) {
        if (cookies == null) return;
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        StringBuilder strCookieBuilder = new StringBuilder();
        for (Cookie cookie : cookies) {
            strCookieBuilder.append(cookie.toString());
            strCookieBuilder.append("|");
        }
        strCookieBuilder.deleteCharAt(strCookieBuilder.length() - 1);
        prefsWriter.putString(url.host(), strCookieBuilder.toString());
        prefsWriter.apply();
    }

    /**
     * Get cookies base url.
     */
    public List<Cookie> get(HttpUrl url) {
        List<Cookie> list = new ArrayList<>();
        //Poto cookie
//        list.add(new Cookie.Builder().domain(url.host()).name(KEY_CLT_ID).value(V_CLT_ID).build());
//        String strAppVer = String.valueOf(MyApplication.getInstance().getVersionCode());
//        list.add(new Cookie.Builder().domain(url.host()).name(KEY_APP_VER).value(strAppVer).build());
//        String dataVer   = String.valueOf(MyApplication.getInstance().getVersionCode());
//        list.add(new Cookie.Builder().domain(url.host()).name(KEY_DATA_VER).value(dataVer).build());
//        String uuid = MyApplication.getInstance().getUuid();
//        list.add(new Cookie.Builder().domain(url.host()).name(KEY_UUID).value(uuid).build());
        String sdkVer = Build.VERSION.RELEASE;
        list.add(new Cookie.Builder().domain(url.host()).name(KEY_SDK_VER).value(sdkVer).build());
        String sdkInt = String.valueOf(Build.VERSION.SDK_INT);
        list.add(new Cookie.Builder().domain(url.host()).name(KEY_SDK_INT).value(sdkInt).build());
//        String app = "Manly";
//        list.add(new Cookie.Builder().domain(url.host()).name(KEY_APP).value(app).build());
//        String isvip = ForHonor.get().checkVip() ? "1" : "0";
//        list.add(new Cookie.Builder().domain(url.host()).name(ISVIP).value(isvip).build());
//        String sex = String.valueOf(ResProvider.sharePreferences(PotoConfig.SP_SETTING).getInt(PotoConfig.KEY_USER_SEX, 0));
//        list.add(new Cookie.Builder().domain(url.host()).name(KEY_USER_SEX).value(sex).build());
//        String skin = String.valueOf(ResProvider.sharePreferences(PotoConfig.SP_SETTING).getInt(PotoConfig.KEY_USER_SKIN, 0));
//        list.add(new Cookie.Builder().domain(url.host()).name(KEY_USER_SKIN).value(skin).build());
//        String ccode = MyApplication.getInstance().checkUsUser().toUpperCase(Locale.US);
//        list.add(new Cookie.Builder().domain(url.host()).name(KEY_COUNTRY).value(ccode).build());
        //Origin url cookie
        String strCookies = cookiePrefs.getString(url.host(), null);
        if (strCookies != null) {
            String[] strings = strCookies.split("\\|");
            for (int i = 0; i < strings.length; i++) {
                Cookie cookie = Cookie.parse(url, strings[i]);
                if (cookie != null) list.add(cookie);
            }
        }
        return list;
    }
}
