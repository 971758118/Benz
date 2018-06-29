package com.benz.find.apimanager;

import com.benz.find.apimanager.network.RESTfulFactory;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface ApiService {

    //    CttApi/LoadMeituResultsAll?
    @GET("CttApi/LoadMeituResultsAll")
    Observable<JSONObject> loadMeitu(@QueryMap Map<String, Object> options);


    @Headers({RESTfulFactory.HEADER_KEY + ":" + RESTfulFactory.HEADER_VALUE_PIC})
    @GET("CttApi/LoadMeituResultsAll")
    Observable<JSONObject> getPicture(@QueryMap Map<String, Object> options);

    @Headers({RESTfulFactory.HEADER_KEY + ":" + RESTfulFactory.HEADER_VALUE_ART})
    @GET()
    Observable<JSONObject> getArticle(@QueryMap Map<String, Object> options);

    @Headers({RESTfulFactory.HEADER_KEY + ":" + RESTfulFactory.HEADER_VALUE_THREE})
    @GET()
    Observable<JSONObject> getOther(@QueryMap Map<String, Object> options);


}
