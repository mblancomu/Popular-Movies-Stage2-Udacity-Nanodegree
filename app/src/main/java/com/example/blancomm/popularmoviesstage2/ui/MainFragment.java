package com.example.blancomm.popularmoviesstage2.ui;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.blancomm.popularmoviesstage2.R;
import com.example.blancomm.popularmoviesstage2.VolleyListeners;
import com.example.blancomm.popularmoviesstage2.adapters.EndlessRecyclerOnScrollListener;
import com.example.blancomm.popularmoviesstage2.adapters.MainRecyclerAdapter;
import com.example.blancomm.popularmoviesstage2.db.SqlHandler;
import com.example.blancomm.popularmoviesstage2.model.FavoriteInfo;
import com.example.blancomm.popularmoviesstage2.model.MovieDetailInfo;
import com.example.blancomm.popularmoviesstage2.model.MoviesInfo;
import com.example.blancomm.popularmoviesstage2.network.VolleyRequest;
import com.example.blancomm.popularmoviesstage2.utils.ConfigDevice;
import com.example.blancomm.popularmoviesstage2.utils.Constant;
import com.example.blancomm.popularmoviesstage2.utils.JSONActions;
import com.example.blancomm.popularmoviesstage2.utils.TransparentProgressDialog;
import com.example.blancomm.popularmoviesstage2.utils.URLUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements VolleyListeners {

    private static final String TAB_POSITION = "tab_position";
    private static final String TAG = MainFragment.class.getSimpleName();
    private List<MoviesInfo> mMovies;
    private MainRecyclerAdapter mAdapter;
    private GridLayoutManager mLayoutManager;
    private int mPosition;
    private int current_page = 1;
    private int configDevice;
    private RecyclerView recyclerView;
    private TextView mEmpty;

    public MainFragment() {
    }

    public static MainFragment newInstance(int tabPosition) {
        MainFragment fragment = new MainFragment();
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

    /**
     * Depending of the tab, the url composite is different.
     * @param position
     * @return
     * @throws UnsupportedEncodingException
     */
    private String setURLFromPosition(int position) throws UnsupportedEncodingException {

        String url = "";

        switch (position){
            case 0:
                url =  URLUtils.getURLPopularity(current_page);
                break;
            case 1:
                url =  URLUtils.getURLRate(current_page);
                break;
            default:
                url =  URLUtils.getURLPopularity(current_page);
                break;
        }

        return url;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_main, container, false);

        int currentOrientation = getResources().getConfiguration().orientation;

        if (configDevice != currentOrientation){

           beginRequest(mPosition);
            configDevice = currentOrientation;

        }

        mMovies = new ArrayList<MoviesInfo>();
        mAdapter = new MainRecyclerAdapter(mMovies, getActivity(), mPosition);

        mLayoutManager = new GridLayoutManager(getActivity(), ConfigDevice.getNumberColumnsGrid(getActivity()));
        mEmpty = (TextView)v.findViewById(R.id.no_movies);

        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {

                try {
                    loadMoreData(current_page);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }

        });

        beginRequest(mPosition);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    /**
     * Get data from the request on volleyrequest for all movies.
     * @param jsonObject
     */
    @Override
    public void onFinishJsonMoviesRequest(JSONObject jsonObject) {

        try {
            mMovies = JSONActions.parse(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (mMovies != null){

            mAdapter.updateResults(mMovies, getActivity());
            recyclerView.setVisibility(View.VISIBLE);

        } else {

           mEmpty.setVisibility(mMovies.size() > 0 ? View.GONE : View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

        getActivity().findViewById(R.id.back_progress).setVisibility(View.GONE);
        getActivity().findViewById(R.id.progressbar).setVisibility(View.GONE);
    }

    /**
     * Interface. Dont used here, only in detail view.
     * @param jsonObject
     */
    @Override
    public void onFinishJsonVideosRequest(JSONObject jsonObject) {

    }

    @Override
    public void onFinishJsonReviewsRequest(JSONObject jsonObject) {

    }

    /**
     * Update the grid view with differents pages when the user do scroll.
     * @param current_page
     * @throws UnsupportedEncodingException
     */
    private void loadMoreData(int current_page) throws UnsupportedEncodingException {

        current_page++;

        VolleyRequest.requestJsonMovies(this, URLUtils.getURLPopularity(current_page));

        mAdapter.notifyDataSetChanged();

    }

    /**
     * Init request for every tab, less the favorites tabs.
     * @param position
     */
    private void beginRequest(int position){

        try {

            VolleyRequest.requestJsonMovies(this, setURLFromPosition(position));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


}
