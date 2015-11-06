package com.example.blancomm.popularmoviesstage2.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.blancomm.popularmoviesstage2.R;
import com.example.blancomm.popularmoviesstage2.adapters.MainRecyclerAdapter;
import com.example.blancomm.popularmoviesstage2.db.SqlHandler;
import com.example.blancomm.popularmoviesstage2.model.MoviesInfo;
import com.example.blancomm.popularmoviesstage2.utils.ConfigDevice;
import com.example.blancomm.popularmoviesstage2.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manuel on 1/11/15.
 */
public class FavoritesFragment  extends Fragment {

    private static final String TAB_POSITION = "tab_position";
    private static final String TAG = MainFragment.class.getSimpleName();
    private List<MoviesInfo> mMovies;
    private MainRecyclerAdapter mAdapter;
    private GridLayoutManager mLayoutManager;
    private int mPosition;
    private int configDevice;
    private RecyclerView recyclerView;
    private TextView mEmpty;

    public static FavoritesFragment newInstance(int tabPosition) {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        args.putInt(TAB_POSITION, tabPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        mPosition =  getArguments().getInt(TAB_POSITION);

        if (savedInstanceState != null){

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
        View v =  inflater.inflate(R.layout.fragment_favorites, container, false);

        int currentOrientation = getResources().getConfiguration().orientation;

        if (configDevice != currentOrientation){

            getFavorites();

            configDevice = currentOrientation;

        }

        mMovies = new ArrayList<MoviesInfo>();
        mAdapter = new MainRecyclerAdapter(mMovies, getActivity(), mPosition);

        mLayoutManager = new GridLayoutManager(getActivity(), ConfigDevice.getNumberColumnsGrid(getActivity()));
        mEmpty = (TextView)v.findViewById(R.id.no_fav);

        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerview_favorites);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        getFavorites();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        mEmpty.setVisibility(View.GONE);
        getFavorites();

    }

    /**
     * Init request only for the favorites tab, if have value on the database, get all in array, else
     * print the text that havent favorites.
     */
    public void getFavorites(){

        List<MoviesInfo> favorites = SqlHandler.getAllFavorites();

        if (favorites != null) {

            mAdapter.updateResults(favorites, getActivity());
            recyclerView.setVisibility(View.VISIBLE);

        } else {

            mEmpty.setVisibility(mMovies.size() > 0 ? View.GONE : View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

    }
}
