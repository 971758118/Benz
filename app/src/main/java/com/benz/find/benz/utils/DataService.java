package com.benz.find.benz.utils;


import org.json.JSONObject;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

public interface DataService {

    @GET
    Observable<JSONObject>
    getData(@Url String url);

    @GET("login")
    Observable<JSONObject>
    login(@Query("phone") String phoneNumber, @Query("code") String code);

    @GET("getHomeList")
    Observable<JSONObject>
    getHomeJson(@Query("version") int version);
}
