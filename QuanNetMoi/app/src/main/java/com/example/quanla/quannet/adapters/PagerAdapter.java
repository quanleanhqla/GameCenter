package com.example.quanla.quannet.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.quanla.quannet.fragments.HotFragment;
import com.example.quanla.quannet.fragments.NewFragment;

/**
 * Created by QuanLA on 3/19/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new HotFragment();
                break;
            case 1:
                fragment = new NewFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Hot";
                break;
            case 1:
                title = "New";
                break;
        }
        return title;
    }

}
