package com.benz.find;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Fragment mTempFragment;
    private BeautyFragment mBeautyFragment;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchFragment(mBeautyFragment);
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mBeautyFragment = new BeautyFragment();

        BottomNavigationView navigation = findViewById(R.id.main_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        switchFragment(mBeautyFragment);
    }

    private void switchFragment(Fragment fragment) {
        if (fragment == mTempFragment) return;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragment.isAdded()) {
            ft.hide(mTempFragment).show(fragment).commit();
            mTempFragment = fragment;
            return;
        }
        if (mTempFragment == null)
            ft.add(R.id.main_fragment, fragment);
        else
            ft.hide(mTempFragment).add(R.id.main_fragment, fragment);
        ft.commit();
        mTempFragment = fragment;
    }

}
