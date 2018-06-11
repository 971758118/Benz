package com.benz.find.benz.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.benz.find.benz.R;
import com.benz.find.benz.apimanager.RESTfulFactory;
import com.benz.find.benz.entity.MeituEntity;
import com.benz.find.benz.entity.MeituItems;
import com.benz.find.benz.utils.DataService;
import com.benz.find.benz.utils.picasso.PicassoUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Jiaxu on 2018-06-06
 */

public class MeituFragment extends BaseFragment {
    private final String TAG = "MeituFragment";
    private View mRootView;
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private final String url1 = "https://apic.51wnl.com/CttApi/LoadMeituResultsAll?tabId=0&limit=10&skip=0&sign=6da47ea28e0250c6dfa4454aea1c513a&cc=CN&carrier=99&did=e799b81da45d95c30f7ee0021a0092be&mac=02%253A00%253A00%253A00%253A00%253A00&cid=Youloft_Android&height=1440&idfa=a3f60ba58932022b&bd=com.youloft.calendar&chn2=20511&av=4.8.2&imei=357216072056691&brand=samsung&lang=zh&oudid=a3f60ba58932022b&ov=7.0&width=2560&chn=20511-0&imsi=unknow&model=SM-G935T&deviceid=e799b81da45d95c30f7ee0021a0092be&city=101120101&nt=0&t=1528283709&tkn=D0513B7CEF494E82AEAFDFF7B2183ECF&citycode=101120101&strategy=1002&bsid=5b16519af4e274740d555b57&uid=&utkn=An2ydh9NUM8_M8e9PIQm819M3ClUt8zoFjFNoa6W0m5q&lng=116.8937573&lon=116.8937573&lat=36.6727534&authtime=1528283709&authsign=8bdeb85acbe532d95e918a8a4ca2d67d";

    public static MeituFragment instance() {
        MeituFragment fragment = new MeituFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_meitu, null, false);
        mRecyclerView = mRootView.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        initData();
        return mRootView;
    }

    private void initData() {
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
                                Log.i(TAG, "---log---onError>" + e.getMessage());
                            }

                            @Override
                            public void onNext(JSONObject jsonObject) {
                                Log.i(TAG, "---log---onNext>" + jsonObject);
                                List<MeituEntity> poseResEntityList = new ArrayList<>();
                                try {
                                    Type listType = new TypeToken<ArrayList<MeituEntity>>() {
                                    }.getType();
                                    Gson gson = new Gson();
                                    poseResEntityList = gson.fromJson(jsonObject.getString("data"), listType);
                                    mAdapter = new Adapter(poseResEntityList);
                                    mRecyclerView.setAdapter(mAdapter);
//                                    for (int i = 0; i < poseResEntityList.size(); i++) {
//                                        Log.i(TAG, "---log---poseResEntityList>" + poseResEntityList.get(i).toString());
//                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        }).start();
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {
        List<MeituEntity> mList;
        List<String> mImages;

        public Adapter(List<MeituEntity> list) {
            mList = list;
            mImages = new ArrayList<>();
            if (mList != null) {
                for (int i = 0; i < mList.size(); i++) {
                    MeituItems items = mList.get(i).getItems();
                    if (items != null && items.getImages() != null) {
                        mImages.addAll(items.getImages());
                    }
                }
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_meitu, null, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            if (mImages.size() > position && !TextUtils.isEmpty(mImages.get(position))) {
                PicassoUtils.with(getActivity()).load(mImages.get(position)).into(holder.image);
            }
        }

        @Override
        public int getItemCount() {
            return mImages.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }

        private ImageView image;
    }
}
