package com.benz.find;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benz.find.adapter.MyTabLayoutAdapter;

import java.util.ArrayList;
import java.util.List;


public class BeautyFragment extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> mTabTitles;
    private List<Fragment> mFragments;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mFragments = new ArrayList<>();
        mTabTitles = new ArrayList<>();
        mTabTitles.add("妹子1");
        mTabTitles.add("妹子2");
        mTabTitles.add("妹子3");
        mTabTitles.add("妹子4");
        mTabTitles.add("妹子5");
        mTabTitles.add("妹子6");
        mTabTitles.add("妹子7");
        mTabTitles.add("妹子8");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_beauty, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View v) {
        mTabLayout = v.findViewById(R.id.beauty_fragment_tab_layout);
        mViewPager = v.findViewById(R.id.beauty_fragment_view_pager);

        //初始化 fragment 集合 等操作
        for (int i = 0; i < mTabTitles.size(); i++) {
            BeautyRecyclerFragment fragment = new BeautyRecyclerFragment();
            Bundle bundle = new Bundle();
            bundle.putString("ID", "-->" + i);
            fragment.setArguments(bundle);
            mFragments.add(fragment);
            mTabLayout.addTab(mTabLayout.newTab().setText(mTabTitles.get(i)));
        }

        MyTabLayoutAdapter myTabLayoutAdapter = new MyTabLayoutAdapter(getChildFragmentManager(), mFragments, mTabTitles);
        mViewPager.setAdapter(myTabLayoutAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        // 默认第几个
        mViewPager.setCurrentItem(0);
    }


}
