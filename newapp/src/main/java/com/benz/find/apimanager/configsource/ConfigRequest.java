package com.benz.find.apimanager.configsource;

import android.content.Context;

import com.benz.find.apimanager.ApiService;
import com.benz.find.apimanager.configsource.model.RequestConfigModel;
import com.benz.find.apimanager.network.RESTfulFactory;
import com.google.gson.Gson;

import net.grandcentrix.tray.AppPreferences;

import java.util.Map;

public class ConfigRequest {

    public static Map<String, Object> getConfigRequest() {
        RequestConfigModel model = new RequestConfigModel();
        return model.getOptions();
    }
}
