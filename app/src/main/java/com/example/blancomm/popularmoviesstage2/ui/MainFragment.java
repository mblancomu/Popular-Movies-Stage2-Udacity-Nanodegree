package com.example.blancomm.popularmoviesstage2.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blancomm.popularmoviesstage2.R;
import com.example.blancomm.popularmoviesstage2.adapters.MainPagerAdapter;
import com.example.blancomm.popularmoviesstage2.utils.Constant;

import static com.example.blancomm.popularmoviesstage2.R.id.fragment_placeholder;

public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private int configDevice;
    private boolean tabletSize;
    private DetailFragment detail;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabletSize = getActivity().getResources().getBoolean(R.bool.isTablet);

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

        final MainPagerAdapter adapter = new MainPagerAdapter(getActivity().getSupportFragmentManager(), getActivity());
        final ViewPager viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
