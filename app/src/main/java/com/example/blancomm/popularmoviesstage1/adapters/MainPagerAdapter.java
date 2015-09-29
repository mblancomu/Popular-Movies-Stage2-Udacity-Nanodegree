package com.example.blancomm.popularmoviesstage1.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.blancomm.popularmoviesstage1.ui.MainActivityFragment;


public class MainPagerAdapter extends FragmentStatePagerAdapter {



    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment =  MainActivityFragment.newInstance(position);
                break;
            case 1:
                fragment =  MainActivityFragment.newInstance(position);
                break;
            case 2:
                fragment =  MainActivityFragment.newInstance(position);
                break;
            default:
                fragment =  MainActivityFragment.newInstance(position);
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
                title = "ALL MOVIES";
                break;
            case 1:
                title = "MOST POPULAR";
                break;
            case 2:
                title = "HIGHEST RATED";
                break;
            default:
                title = "ALL MOVIES";
        }

        return title;
    }
}

