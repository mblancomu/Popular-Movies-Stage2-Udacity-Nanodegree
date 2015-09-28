package com.example.blancomm.popularmoviesstage1.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.blancomm.popularmoviesstage1.R;
import com.example.blancomm.popularmoviesstage1.VolleyListeners;
import com.example.blancomm.popularmoviesstage1.model.MovieDetailInfo;
import com.example.blancomm.popularmoviesstage1.network.VolleyRequest;
import com.example.blancomm.popularmoviesstage1.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment implements VolleyListeners {

    private String mIdMovie;
    private NetworkImageView mImageDetail;
    private TextView mTextDetail;

    public DetailActivityFragment() {
    }

    public static DetailActivityFragment newInstance(int tabPosition) {
        DetailActivityFragment fragment = new DetailActivityFragment();
        Bundle args = new Bundle();
        //args.putInt(TAB_POSITION, tabPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        mIdMovie = "550";

        String urlDetail = Constant.URL_DETAIL_MOVIE + mIdMovie +"?"+ Constant.API_KEY;

        Log.e("","La url es: " + urlDetail);

        VolleyRequest.requestJson(this, urlDetail);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        instantiateObjects(rootView);


        return rootView;
    }

    private void instantiateObjects(View view){

        mImageDetail = (NetworkImageView)view.findViewById(R.id.image_detail);
        mTextDetail = (TextView)view.findViewById(R.id.text_detail);
    }

    private MovieDetailInfo parseDetail(JSONObject json) throws JSONException {

            JSONObject jsonMovieDetail = json;

            String title = jsonMovieDetail.getString("title");
            String adult = jsonMovieDetail.getString("adult");
            String imageDetail = jsonMovieDetail.getString("backdrop_path");
            //String genreIds = jsonMovieDetail.getString("genre_ids");
            String id = jsonMovieDetail.getString("id");
            String originalLanguage = jsonMovieDetail.getString("original_language");
            String originalTitle = jsonMovieDetail.getString("original_title");
            String description = jsonMovieDetail.getString("overview");
            String releaseDate = jsonMovieDetail.getString("release_date");
            String thumnail = jsonMovieDetail.getString("poster_path");
            String popularity = jsonMovieDetail.getString("popularity");
            String video = jsonMovieDetail.getString("video");
            String voteAverage = jsonMovieDetail.getString("vote_average");
            String voteCount = jsonMovieDetail.getString("vote_count");
            String homepage = jsonMovieDetail.getString("homepage");
            String imdb_id = jsonMovieDetail.getString("imdb_id");
            String production_companies = jsonMovieDetail.getString("production_companies");
            String production_countries = jsonMovieDetail.getString("production_countries");
            String revenue = jsonMovieDetail.getString("revenue");
            String runtime = jsonMovieDetail.getString("runtime");
            String spoken_languages = jsonMovieDetail.getString("spoken_languages");
            String status = jsonMovieDetail.getString("status");
            String tagline = jsonMovieDetail.getString("tagline");

            MovieDetailInfo movie = new MovieDetailInfo();
            movie.setAdult(adult);
            movie.setDescription(description);
           // movie.setGenreIds(genreIds);
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
            movie.setHomepage(homepage);
            movie.setImdb_id(imdb_id);
            movie.setProduction_companies(production_companies);
            movie.setProduction_countries(production_countries);
            movie.setRevenue(revenue);
            movie.setRuntime(runtime);
            movie.setSpoken_languages(spoken_languages);
            movie.setStatus(status);
            movie.setTagline(tagline);

        return movie;
    }

    @Override
    public void onFinishJsonRequest(JSONObject jsonObject) {

        MovieDetailInfo movieDetail;

        try {
            movieDetail = parseDetail(jsonObject);
            injectData(movieDetail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void injectData(MovieDetailInfo movieDetailInfo){

        String urlImage = Constant.URL_DETAIL_IMAGE + movieDetailInfo.getImageDetail();
        VolleyRequest.requestImage(urlImage, mImageDetail);
        mTextDetail.setText(movieDetailInfo.getDescription());
    }
}
