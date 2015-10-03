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
import com.example.blancomm.popularmoviesstage1.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        setHasOptionsMenu(true);

        mPosition =  getArguments().getInt(TAB_POSITION);

        Log.e(TAG,"Position : " + mPosition);

        VolleyRequest.requestJson(this, setURLFromPosition(mPosition));

        Log.e(TAG,"La url: " +  setURLFromPosition(mPosition));

    }

    private String setURLFromPosition(int position){

        String url = "";

        switch (position){

            case 0:
                url =  Constant.URL_JSON_MOVIE_LIST_LATEST;
                break;
            case 1:
                url =  Constant.URL_JSON_MOVIE_LIST_POPULARITY;
                break;
            case 2:
                url =  Constant.URL_JSON_MOVIE_LIST_HIGHTEST_RATE;
                break;
            default:
                url =  Constant.URL_JSON_MOVIE_LIST_LATEST;
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

        Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int orientation = display.getRotation();

        if (orientation == 0){
            mLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.columns_grid_portait));
        }else {
            mLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.columns_grid_landscape));
        }

        View v =  inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {

                loadMoreData(current_page);

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
                movie.setAdult(jsonMovie.getString("adult"));
                movie.setDescription(jsonMovie.getString("overview"));
                movie.setGenreIds(jsonMovie.getString("genre_ids"));
                movie.setTitle(jsonMovie.getString("title"));
                movie.setId(jsonMovie.getString("id"));
                movie.setImageDetail(jsonMovie.getString("backdrop_path"));
                movie.setOriginalLanguage(jsonMovie.getString("original_language"));
                movie.setOriginalTitle(jsonMovie.getString("original_title"));
                movie.setReleaseDate(jsonMovie.getString("release_date"));
                movie.setThumnail(jsonMovie.getString("poster_path"));
                movie.setPopularity(jsonMovie.getString("popularity"));
                movie.setVideo(jsonMovie.getString("video"));
                movie.setVoteAverage(jsonMovie.getString("vote_average"));
                movie.setVoteCount(jsonMovie.getString("vote_count"));

                mMovies.add(movie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mAdapter.updateResults(mMovies,getActivity());
    }

    private void loadMoreData(int current_page) {

        current_page++;

        VolleyRequest.requestJson(this, Constant.URL_JSON_MOVIE_LIST_POPULARITY + Constant.NEW_PAGE + current_page);

        mAdapter.notifyDataSetChanged();

    }
}
