package com.benz.find.apimanager.network;


import com.benz.find.MyApplication;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class RESTfulFactory {

    public static final String HEADER_KEY = "url_name";
    public static final String HEADER_VALUE_PIC = "picture";
    public static final String HEADER_VALUE_ART = "article";
    public static final String HEADER_VALUE_THREE = "";
    public static final String BASE_URL_PIC = "https://apic.51wnl.com/";
    public static final String BASE_URL_ART = "https://r.51wnl.com/";
    public static final String BASE_URL_THREE = "";

    public static final String DEFAULT_RUL = "https://apic.51wnl.com/";
    //api地址
    //Todo 添加debug切换
    private final static String BASE_URL = DEFAULT_RUL;
    private final static int DEFAULT_TIMEOUT = 5;
    private final static Integer CODE_SUCCESS = 1;

    private Retrofit mRetrofit;
    private Retrofit mJsonRetrofit;

    /*
    * 私有化构造方法
    * */
    private RESTfulFactory() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        if (MyApplication.isDebugMode()) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(interceptor);
        }
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl oldHttpUrl = request.url();
                Request.Builder builder = request.newBuilder();
                List<String> headerValues = request.headers(HEADER_KEY);
                if (headerValues != null && headerValues.size() > 0) {
                    builder.removeHeader(HEADER_KEY);
                    String headerValue = headerValues.get(0);
                    HttpUrl newBaseUrl;
                    if (HEADER_VALUE_PIC.equals(headerValue)) {
                        newBaseUrl = HttpUrl.parse(BASE_URL_PIC);
                    } else if (HEADER_VALUE_ART.equals(headerValue)) {
                        newBaseUrl = HttpUrl.parse(BASE_URL_ART);
                    } else if (HEADER_VALUE_THREE.equals(headerValue)) {
                        newBaseUrl = HttpUrl.parse(BASE_URL_THREE);
                    } else {
                        newBaseUrl = oldHttpUrl;
                    }
                    HttpUrl newFullUrl = oldHttpUrl
                            .newBuilder()
                            .scheme(newBaseUrl.scheme())
                            .host(newBaseUrl.host())
                            .port(newBaseUrl.port())
                            .build();
                    return chain.proceed(builder.url(newFullUrl).build());
                } else {
                    return chain.proceed(request);
                }
            }
        });
//        httpClientBuilder.cookieJar(new CookieJar() {
//            @Override
//            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//                CookieManager.getInstance().saveCookies(url, cookies);
//            }
//
//            @Override
//            public List<Cookie> loadForRequest(HttpUrl url) {
//                return CookieManager.getInstance().get(url);
//            }
//        });

//        httpClientBuilder.addNetworkInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                Request newRequest;
//
//                newRequest = request.newBuilder()
//                        .addHeader("version", String.valueOf(MyApplication.getInstance().getVersionCode()))
//                        .addHeader("Accept-Language", LocaleUtils.localeToBcp47Language(Locale.getDefault()))
//                        .build();
//                return chain.proceed(newRequest);
//            }
//        });

        mRetrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        //JsonRetrofit
        mJsonRetrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(JsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }


    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final RESTfulFactory INSTANCE = new RESTfulFactory();
    }

    //获取单例
    public static RESTfulFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    //创建Service的模版方法
    public <T> T create(Class<T> clazz) {
        return mRetrofit.create(clazz);
    }

    public <T> T createJson(Class<T> clazz) {
        return mJsonRetrofit.create(clazz);
    }

    //发起网路请求
    public static <T> Observable<T> handleRequest(Observable<HttpResponse<T>> tObserver) {
        return tObserver
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new RESTfulFactory.HttpResultFunc<T>())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //发起网路请求
    public static <T> Observable<T> handleJsonRequest(Observable<HttpResponse<T>> tObserver) {
        return tObserver
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new RESTfulFactory.HttpResultFunc<T>())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //网络请求返回预处理
    private static class HttpResultFunc<T> implements Func1<HttpResponse<T>, T> {
        @Override
        public T call(HttpResponse<T> tHttpResponse) {
            //处理返回码
            if (tHttpResponse.getCode() != CODE_SUCCESS) {
                //Todo 抛出错误异常
            }
            return tHttpResponse.getData();
        }
    }

}
