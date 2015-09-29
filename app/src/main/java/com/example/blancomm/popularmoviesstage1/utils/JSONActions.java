package com.example.blancomm.popularmoviesstage1.utils;


import com.example.blancomm.popularmoviesstage1.model.MovieDetailInfo;
import com.example.blancomm.popularmoviesstage1.model.MoviesInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONActions {

    public static MovieDetailInfo parseDetail(JSONObject json) throws JSONException {

        JSONObject jsonMovieDetail = json;

        MovieDetailInfo movie = new MovieDetailInfo();
        movie.setAdult(jsonMovieDetail.getString("adult"));
        movie.setDescription(jsonMovieDetail.getString("overview"));
        // movie.setGenreIds(jsonMovieDetail.getString("genre_ids"));
        movie.setTitle(jsonMovieDetail.getString("title"));
        movie.setId(jsonMovieDetail.getString("id"));
        movie.setImageDetail(jsonMovieDetail.getString("backdrop_path"));
        movie.setOriginalLanguage(jsonMovieDetail.getString("original_language"));
        movie.setOriginalTitle(jsonMovieDetail.getString("original_title"));
        movie.setReleaseDate(jsonMovieDetail.getString("release_date"));
        movie.setThumnail(jsonMovieDetail.getString("poster_path"));
        movie.setPopularity(jsonMovieDetail.getString("popularity"));
        movie.setVideo(jsonMovieDetail.getString("video"));
        movie.setVoteAverage(jsonMovieDetail.getString("vote_average"));
        movie.setVoteCount(jsonMovieDetail.getString("vote_count"));
        movie.setHomepage(jsonMovieDetail.getString("homepage"));
        movie.setImdb_id(jsonMovieDetail.getString("imdb_id"));
        movie.setProduction_companies(jsonMovieDetail.getString("production_companies"));
        movie.setProduction_countries(jsonMovieDetail.getString("production_countries"));
        movie.setRevenue(jsonMovieDetail.getString("revenue"));
        movie.setRuntime(jsonMovieDetail.getString("runtime"));
        movie.setSpoken_languages(jsonMovieDetail.getString("spoken_languages"));
        movie.setStatus(jsonMovieDetail.getString("status"));
        movie.setTagline(jsonMovieDetail.getString("tagline"));

        return movie;
    }

    public static List<MoviesInfo> parse(JSONObject json) throws JSONException {
        ArrayList<MoviesInfo> movies = new ArrayList<MoviesInfo>();

        JSONArray jsonMovies = json.getJSONArray("results");

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

            movies.add(movie);
        }

        return movies;
    }
}
