package com.benz.find;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.benz.find.apimanager.ApiService;
import com.benz.find.apimanager.configsource.model.ArticleConfigModel;
import com.benz.find.apimanager.configsource.model.PictureConfigModel;
import com.benz.find.apimanager.network.RESTfulFactory;
import com.benz.find.entity.DuanziEntity;
import com.benz.find.entity.MeituEntity;
import com.benz.find.entity.MeituItems;
import com.benz.find.utils.picasso.PicassoUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class BeautyRecyclerFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private String Tag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tag = getArguments().getString("Tag");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_beauty_recycler, container, false);
        mRecyclerView = rootView.findViewById(R.id.beauty_recycler_fragment_recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        getData();
//        test();
        return rootView;
    }

    private void getData() {
        RESTfulFactory.getInstance().createJson(ApiService.class)
                .getPicture(new PictureConfigModel().setTag(Tag).getOptions())
                .map(new Func1<JSONObject, List<MeituEntity>>() {
                    @Override
                    public List<MeituEntity> call(JSONObject jsonObject) {
                        Type listType = new TypeToken<ArrayList<MeituEntity>>() {
                        }.getType();
                        try {
                            return new Gson().fromJson(jsonObject.getString("data"), listType);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<MeituEntity>>() {
                    @Override
                    public void call(List<MeituEntity> meituEntities) {
                        mAdapter = new Adapter(meituEntities);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                });
    }

    private void test() {
        RESTfulFactory.getInstance().createJson(ApiService.class)
                .getArticle(new ArticleConfigModel().setChnCode("qingsongyike").getOptions())
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

                        List<DuanziEntity> entities = new ArrayList<>();
                        try {
                            Type listType = new TypeToken<ArrayList<DuanziEntity>>() {
                            }.getType();
                            Gson gson = new Gson();
                            entities = gson.fromJson(jsonObject.getString("data"), listType);

                            for (int i = 0; i < entities.size(); i++) {
                                Log.i(TAG, "---log---entities>" + entities.get(i).toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

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
