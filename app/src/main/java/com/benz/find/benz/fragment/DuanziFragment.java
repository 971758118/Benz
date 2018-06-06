package com.benz.find.benz.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benz.find.benz.R;

/**
 * Created by Jiaxu on 2018-06-06
 */

public class DuanziFragment extends BaseFragment {
    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_duanzi, null, false);
        return mRootView;
    }
}
