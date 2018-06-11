package com.benz.find.benz;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.benz.find.benz.activity.BaseActivity;
import com.benz.find.benz.fragment.DuanziFragment;
import com.benz.find.benz.fragment.MeituFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = "MainActivity";
    private List<Fragment> mainFragments = new ArrayList<>();
    private Fragment mCurrentFragment;
    private boolean containerFragmentFirstAdd;
    private DuanziFragment mDuanziFragment;
    private MeituFragment mMeituFragment;
    private View mDuanziView;
    private View mMeituView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDuanziView = findViewById(R.id.btn_duanzi);
        mMeituView = findViewById(R.id.btn_meitu);
        mDuanziView.setOnClickListener(this);
        mMeituView.setOnClickListener(this);

        mMeituView.performClick();
    }

    private void cleanBackground() {
        mDuanziView.setBackgroundColor(Color.parseColor("#00000000"));
        mMeituView.setBackgroundColor(Color.parseColor("#00000000"));
    }


    private void showFragment(final Fragment fragment) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (fragment == null) {
                    return;
                }
                mCurrentFragment = fragment;

                FragmentManager mManager = getSupportFragmentManager();
                FragmentTransaction transaction = mManager.beginTransaction();

                if (!fragment.isAdded()) {
                    if (containerFragmentFirstAdd) {
                        containerFragmentFirstAdd = false;
                        transaction.replace(R.id.fragment_container, fragment);
                    } else {
                        transaction.add(R.id.fragment_container, fragment);
                    }

                    if (mainFragments.contains(fragment)) {
                        mainFragments.remove(fragment);
                    }
                    mainFragments.add(fragment);
                } else {
                    transaction.show(fragment);
                }

                for (Fragment f : mainFragments) {
                    if (!f.equals(fragment)) {
                        transaction.hide(f);
                    }
                }
                try {
                    transaction.commitNowAllowingStateLoss();
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_duanzi:
                cleanBackground();
                mDuanziView.setBackgroundColor(Color.parseColor("#dddddd"));
                if (mDuanziFragment == null) {
                    mDuanziFragment = DuanziFragment.instance();
                }
                showFragment(mDuanziFragment);
                break;
            case R.id.btn_meitu:
                cleanBackground();
                mMeituView.setBackgroundColor(Color.parseColor("#dddddd"));
                if (mMeituFragment == null) {
                    mMeituFragment = MeituFragment.instance();
                }
                showFragment(mMeituFragment);
                break;
        }
    }
}
