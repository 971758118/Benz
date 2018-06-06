package com.benz.find.benz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.benz.find.benz.apimanager.RESTfulFactory;
import com.benz.find.benz.entity.DuanziEntity;
import com.benz.find.benz.utils.DataService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private final String url1 = "https://r.51wnl.com/api/News/GetInfiniteInfos?" +
            "chnCode=qingsongyike&act=1&cc=CN&carrier=99" +
            "&did=e799b81da45d95c30f7ee0021a0092be&mac=02%253A00%253A00%253A00%253A00%253A00" +
            "&cid=Youloft_Android&height=1440&idfa=a3f60ba58932022b&bd=com.youloft.calendar" +
            "&chn2=20511&av=4.8.2&imei=357216072056691&brand=samsung&lang=zh" +
            "&oudid=a3f60ba58932022b&ov=7.0&width=2560&chn=20511-0&imsi=unknow&model=SM-G935T" +
            "&deviceid=e799b81da45d95c30f7ee0021a0092be&city=101120101&nt=0&t=1528284572" +
            "&tkn=D0513B7CEF494E82AEAFDFF7B2183ECF&citycode=101120101&strategy=1002" +
            "&bsid=5b16519af4e274740d555b57&uid=&utkn=An2ydh9NUM8_M8e9PIQm819M3ClUt8zoFjFNoa6W0m5q" +
            "&lng=116.8937573&lon=116.8937573&lat=36.6727534&authtime=1528284572" +
            "&authsign=c3cbf711ee6a9774d1655fc0286a19f8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                RESTfulFactory.getInstance().createJson(DataService.class)
                        .getData(url1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<JSONObject>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(JSONObject jsonObject) {
                                List<DuanziEntity> poseResEntityList = new ArrayList<>();
                                try {
                                    Type listType = new TypeToken<ArrayList<DuanziEntity>>() {
                                    }.getType();
                                    Gson gson = new Gson();
                                    poseResEntityList = gson.fromJson(jsonObject.getString("data"), listType);
                                    for (int i = 0; i < poseResEntityList.size(); i++) {
                                        Log.i(TAG,"---log---poseResEntityList>"+poseResEntityList.get(i).toString());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                try {
                    Document doc = Jsoup.connect("http://www.xiao688.com/cms/list/typeid-184.html").get();

                    Elements elements = doc.getElementsByClass("content");
                    for (int i = 0; i < elements.size(); i++) {
//                        Log.i(TAG, "---log---elements>" + elements.get(i).text());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
