package com.benz.find.apimanager;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface ApiService {

//    CttApi/LoadMeituResultsAll?
    @GET("CttApi/LoadMeituResultsAll")
    Observable<JSONObject> loadMeitu(@QueryMap Map<String, Object> options);

}
