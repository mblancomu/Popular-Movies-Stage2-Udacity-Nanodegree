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
    private DrawerLayout mDrawerLayout;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        boolean tabletSize = getActivity().getResources().getBoolean(R.bool.isTablet);

        if (!tabletSize) {
            setHasOptionsMenu(true);
        }else {
            setHasOptionsMenu(false);
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

        boolean tabletSize = getActivity().getResources().getBoolean(R.bool.isTablet);

        if (!tabletSize) {
            Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
            ((MainActivity) getActivity()).setSupportActionBar(toolbar);
            ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }

        MainPagerAdapter adapter = new MainPagerAdapter(getActivity().getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        mDrawerLayout = (DrawerLayout) v.findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) v.findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                Toast.makeText(getActivity(), menuItem.getTitle(), Toast.LENGTH_LONG).show();
                return true;
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
