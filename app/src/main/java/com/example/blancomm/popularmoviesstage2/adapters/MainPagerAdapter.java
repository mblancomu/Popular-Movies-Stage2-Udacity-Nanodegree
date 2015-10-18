package com.example.blancomm.popularmoviesstage2.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.blancomm.popularmoviesstage2.ui.MainFragment;


public class MainPagerAdapter extends FragmentStatePagerAdapter {



    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment =  MainFragment.newInstance(position);
                break;
            case 1:
                fragment =  MainFragment.newInstance(position);
                break;
            case 2:
                fragment =  MainFragment.newInstance(position);
                break;
            default:
                fragment =  MainFragment.newInstance(position);
                break;

        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        String title = "";

        switch (position){
            case 0:
                title = "MOST VOTES";
                break;
            case 1:
                title = "MOST POPULAR";
                break;
            case 2:
                title = "HIGHEST RATED";
                break;
            default:
                title = "MOST VOTES";
        }

        return title;
    }
}

