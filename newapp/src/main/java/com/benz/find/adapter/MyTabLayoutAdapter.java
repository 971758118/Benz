package com.benz.find.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;


import java.util.List;

/**
 * Created by anancncn on 2017/3/21.
 */

public class MyTabLayoutAdapter extends DragDetailFragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> titleList;


    public MyTabLayoutAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position % titleList.size());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }
}