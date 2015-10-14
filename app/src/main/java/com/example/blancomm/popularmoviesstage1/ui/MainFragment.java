package com.example.blancomm.popularmoviesstage1.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.blancomm.popularmoviesstage1.R;
import com.example.blancomm.popularmoviesstage1.VolleyListeners;
import com.example.blancomm.popularmoviesstage1.adapters.EndlessRecyclerOnScrollListener;
import com.example.blancomm.popularmoviesstage1.adapters.MainRecyclerAdapter;
import com.example.blancomm.popularmoviesstage1.model.MoviesInfo;
import com.example.blancomm.popularmoviesstage1.network.VolleyRequest;
import com.example.blancomm.popularmoviesstage1.utils.ConfigDevice;
import com.example.blancomm.popularmoviesstage1.utils.Constant;
import com.example.blancomm.popularmoviesstage1.utils.URLUtils;

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

        Log.e(TAG,"Position : " + mPosition);

        try {
            VolleyRequest.requestJson(this, setURLFromPosition(mPosition));

            Log.e(TAG, "Url: " + setURLFromPosition(mPosition));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private String setURLFromPosition(int position) throws UnsupportedEncodingException {

        String url = "";

        switch (position){

            case 0:
                url = URLUtils.getURLLatest(current_page);
                break;
            case 1:
                url =  URLUtils.getURLPopularity(current_page);
                break;
            case 2:
                url =  URLUtils.getURLRate(current_page);
                break;
            default:
                url =  URLUtils.getURLLatest(current_page);
                break;
        }

        return url;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();
        int tabPosition = args.getInt(TAB_POSITION);

        mMovies = new ArrayList<MoviesInfo>();
        mAdapter = new MainRecyclerAdapter(mMovies, getActivity());

        mLayoutManager = new GridLayoutManager(getActivity(), ConfigDevice.getNumberColumnsGrid(getActivity()));

        View v =  inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.recyclerview);
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

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onFinishJsonRequest(JSONObject jsonObject) {

        try {

            JSONArray jsonMovies = jsonObject.getJSONArray("results");

            for(int i =0; i < jsonMovies.length(); i++) {
                JSONObject jsonMovie = jsonMovies.getJSONObject(i);

                MoviesInfo movie = new MoviesInfo();
                movie.setAdult(jsonMovie.getString(Constant.PARAM_ADULT));
                movie.setDescription(jsonMovie.getString(Constant.PARAM_OVERVIEW));
                movie.setGenreIds(jsonMovie.getString(Constant.PARAM_GENRES_IDS));
                movie.setTitle(jsonMovie.getString(Constant.PARAM_TITLE));
                movie.setId(jsonMovie.getString(Constant.PARAM_ID));
                movie.setImageDetail(jsonMovie.getString(Constant.PARAM_BACKDROP));
                movie.setOriginalLanguage(jsonMovie.getString(Constant.PARAM_ORI_LANGUAGE));
                movie.setOriginalTitle(jsonMovie.getString(Constant.PARAM_ORI_TITLE));
                movie.setReleaseDate(jsonMovie.getString(Constant.PARAM_RE_DATE));
                movie.setThumnail(jsonMovie.getString(Constant.PARAM_POSTER));
                movie.setPopularity(jsonMovie.getString(Constant.PARAM_POPULARITY));
                movie.setVideo(jsonMovie.getString(Constant.PARAM_VIDEO));
                movie.setVoteAverage(jsonMovie.getString(Constant.PARAM_VOTE_AVERAGE));
                movie.setVoteCount(jsonMovie.getString(Constant.PARAM_VOTE_COUNT));

                mMovies.add(movie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mAdapter.updateResults(mMovies,getActivity());
    }

    private void loadMoreData(int current_page) throws UnsupportedEncodingException {

        current_page++;

        VolleyRequest.requestJson(this, URLUtils.getURLPopularity(current_page));

        mAdapter.notifyDataSetChanged();

    }
}
