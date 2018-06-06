package com.benz.find.benz.utils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class GsonUtils {

    public static <T> T getJsonObject(File file, Class<T> cls) {
        try {
            InputStream is = new FileInputStream(file);
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
            Gson gson = new Gson();
            return gson.fromJson(buffer.toString(), cls);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getJsonObject(File file, Type type) {
        try {
            InputStream is = new FileInputStream(file);
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
            Gson gson = new Gson();
            return gson.fromJson(buffer.toString(), type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
