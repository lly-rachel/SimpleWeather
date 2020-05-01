package com.example.simpleweather.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class CityFragmentPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList;

    public CityFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    int childCount = 0;

    @Override
    public void notifyDataSetChanged() {
        this.childCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if(childCount>0){
            childCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }
}
