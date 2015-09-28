package com.example.blancomm.popularmoviesstage1.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.blancomm.popularmoviesstage1.R;
import com.example.blancomm.popularmoviesstage1.VolleyListeners;
import com.example.blancomm.popularmoviesstage1.adapters.MainRecyclerAdapter;
import com.example.blancomm.popularmoviesstage1.model.MoviesInfo;
import com.example.blancomm.popularmoviesstage1.network.VolleyRequest;
import com.example.blancomm.popularmoviesstage1.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivityFragment extends Fragment implements VolleyListeners {

    private static final String TAB_POSITION = "tab_position";
    private List<MoviesInfo> mMovies;
    private MainRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public MainActivityFragment() {
    }

    public static MainActivityFragment newInstance(int tabPosition) {
        MainActivityFragment fragment = new MainActivityFragment();
        Bundle args = new Bundle();
        args.putInt(TAB_POSITION, tabPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        VolleyRequest.requestJson(this, Constant.URL_JSON_MOVIE_LIST);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();
        int tabPosition = args.getInt(TAB_POSITION);
       /* Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int orientation = display.getRotation();
        Log.e("","Orientación: " + orientation);*/

        mMovies = new ArrayList<MoviesInfo>();
        mAdapter = new MainRecyclerAdapter(mMovies, getActivity());

        Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int orientation = display.getRotation();

        if (orientation == 0){
            mLayoutManager = new GridLayoutManager(getActivity(), 2);
        }else if (orientation == 1){
            mLayoutManager = new GridLayoutManager(getActivity(), 3);
        }

        View v =  inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private List<MoviesInfo> parse(JSONObject json) throws JSONException {
        ArrayList<MoviesInfo> movies = new ArrayList<MoviesInfo>();

        JSONArray jsonMovies = json.getJSONArray("results");

        for(int i =0; i < jsonMovies.length(); i++) {
            JSONObject jsonMovie = jsonMovies.getJSONObject(i);

            String title = jsonMovie.getString("title");
            String adult = jsonMovie.getString("adult");
            String imageDetail = jsonMovie.getString("backdrop_path");
            String genreIds = jsonMovie.getString("genre_ids");
            String id = jsonMovie.getString("id");
            String originalLanguage = jsonMovie.getString("original_language");
            String originalTitle = jsonMovie.getString("original_title");
            String description = jsonMovie.getString("overview");
            String releaseDate = jsonMovie.getString("release_date");
            String thumnail = jsonMovie.getString("poster_path");
            String popularity = jsonMovie.getString("popularity");
            String video = jsonMovie.getString("video");
            String voteAverage = jsonMovie.getString("vote_average");
            String voteCount = jsonMovie.getString("vote_count");

            MoviesInfo movie = new MoviesInfo();
            movie.setAdult(adult);
            movie.setDescription(description);
            movie.setGenreIds(genreIds);
            movie.setTitle(title);
            movie.setId(id);
            movie.setImageDetail(imageDetail);
            movie.setOriginalLanguage(originalLanguage);
            movie.setOriginalTitle(originalTitle);
            movie.setReleaseDate(releaseDate);
            movie.setThumnail(thumnail);
            movie.setPopularity(popularity);
            movie.setVideo(video);
            movie.setVoteAverage(voteAverage);
            movie.setVoteCount(voteCount);

            movies.add(movie);
        }

        return movies;
    }

    @Override
    public void onFinishJsonRequest(JSONObject jsonObject) {

        try {
            mMovies = parse(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mAdapter.updateResults(mMovies,getActivity());
    }
}
