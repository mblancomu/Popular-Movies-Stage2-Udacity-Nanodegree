package com.example.blancomm.popularmoviesstage2.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.blancomm.popularmoviesstage2.R;
import com.example.blancomm.popularmoviesstage2.adapters.MainPagerAdapter;
import com.example.blancomm.popularmoviesstage2.utils.Constant;

public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private int configDevice;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);

        boolean tabletSize = getActivity().getResources().getBoolean(R.bool.isTablet);

        if (!tabletSize) {
            setRetainInstance(false);
        }else {
            setRetainInstance(true);
        }

        if (savedInstanceState != null) {

            configDevice = savedInstanceState.getInt(Constant.CONFIG_DEVICE);
        } else {
            configDevice = getResources().getConfiguration().orientation;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(Constant.CONFIG_DEVICE, configDevice);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        int currentOrientation = getResources().getConfiguration().orientation;

        if (configDevice != currentOrientation) {
            configDevice = currentOrientation;

        }

        MainPagerAdapter adapter = new MainPagerAdapter(getActivity().getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
