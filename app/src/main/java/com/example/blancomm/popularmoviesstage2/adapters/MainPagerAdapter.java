package com.example.blancomm.popularmoviesstage2.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.blancomm.popularmoviesstage2.R;
import com.example.blancomm.popularmoviesstage2.ui.FavoritesFragment;
import com.example.blancomm.popularmoviesstage2.ui.GridFragment;
import com.example.blancomm.popularmoviesstage2.ui.GridTabletFragment;


public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;

    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        boolean tabletSize = context.getResources().getBoolean(R.bool.isTablet);
        switch (position){
            case 0:
                fragment =  GridFragment.newInstance(position);
                break;
            case 1:
                fragment =  tabletSize ? GridTabletFragment.newInstance(position): GridFragment.newInstance(position);
                break;
            case 2:
                fragment =  FavoritesFragment.newInstance(position);
                break;
            default:
                fragment =  GridFragment.newInstance(position);
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
                title = context.getString(R.string.tab_popular);
                break;
            case 1:
                title = context.getString(R.string.tab_rated);
                break;
            case 2:
                title = context.getString(R.string.tab_favorites);
                break;
            default:
                title = context.getString(R.string.tab_popular);
        }

        return title;
    }
}

