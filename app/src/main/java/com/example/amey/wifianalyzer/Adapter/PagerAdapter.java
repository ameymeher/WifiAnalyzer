package com.example.amey.wifianalyzer.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.amey.wifianalyzer.Fragments.RecordWifiListFragment;
import com.example.amey.wifianalyzer.Fragments.WifiListFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;

    public PagerAdapter(FragmentManager fm,int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                WifiListFragment wifiListFragment = new WifiListFragment();
                return wifiListFragment;

            case 1:
                RecordWifiListFragment recordWifiListFragment = new RecordWifiListFragment();
                return recordWifiListFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
