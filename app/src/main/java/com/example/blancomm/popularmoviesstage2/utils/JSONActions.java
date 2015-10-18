package com.example.blancomm.popularmoviesstage2.utils;


import com.example.blancomm.popularmoviesstage2.model.MovieDetailInfo;
import com.example.blancomm.popularmoviesstage2.model.MoviesInfo;
import com.example.blancomm.popularmoviesstage2.model.ReviewsInfo;
import com.example.blancomm.popularmoviesstage2.model.VideosInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONActions {

    public static MovieDetailInfo parseDetail(JSONObject json) throws JSONException {

        JSONObject jsonMovieDetail = json;

        MovieDetailInfo movie = new MovieDetailInfo();
        movie.setAdult(jsonMovieDetail.getString(Constant.PARAM_ADULT));
        movie.setDescription(jsonMovieDetail.getString(Constant.PARAM_OVERVIEW));
        movie.setGenreIds(jsonMovieDetail.getString(Constant.PARAM_GENRES));
        movie.setTitle(jsonMovieDetail.getString(Constant.PARAM_TITLE));
        movie.setId(jsonMovieDetail.getString(Constant.PARAM_ID));
        movie.setImageDetail(jsonMovieDetail.getString(Constant.PARAM_BACKDROP));
        movie.setOriginalLanguage(jsonMovieDetail.getString(Constant.PARAM_ORI_LANGUAGE));
        movie.setOriginalTitle(jsonMovieDetail.getString(Constant.PARAM_ORI_TITLE));
        movie.setReleaseDate(jsonMovieDetail.getString(Constant.PARAM_RE_DATE));
        movie.setThumnail(jsonMovieDetail.getString(Constant.PARAM_POSTER));
        movie.setPopularity(jsonMovieDetail.getString(Constant.PARAM_POPULARITY));
        movie.setVideo(jsonMovieDetail.getString(Constant.PARAM_VIDEO));
        movie.setVoteAverage(jsonMovieDetail.getString(Constant.PARAM_VOTE_AVERAGE));
        movie.setVoteCount(jsonMovieDetail.getString(Constant.PARAM_VOTE_COUNT));
        movie.setHomepage(jsonMovieDetail.getString(Constant.PARAM_HOMEPAGE));
        movie.setImdb_id(jsonMovieDetail.getString(Constant.PARAM_IMDBID));
        movie.setProduction_companies(jsonMovieDetail.getString(Constant.PARAM_PRO_COMPANIES));
        movie.setProduction_countries(jsonMovieDetail.getString(Constant.PARAM_PRO_COUNTRIES));
        movie.setRevenue(jsonMovieDetail.getString(Constant.PARAM_REVENUE));
        movie.setRuntime(jsonMovieDetail.getString(Constant.PARAM_RUNTIME));
        movie.setSpoken_languages(jsonMovieDetail.getString(Constant.PARAM_SPOKEN_LAN));
        movie.setStatus(jsonMovieDetail.getString(Constant.PARAM_STATUS));
        movie.setTagline(jsonMovieDetail.getString(Constant.PARAM_TAGLINE));

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

    public static List<String> getGenres(String genresList) throws JSONException {

        JSONArray mJSONArray = new JSONArray(genresList);
        int genresSize = mJSONArray.length();
        List<String> genres = new ArrayList<>();

        for (int ii = 0; ii < genresSize; ii++) {
            genres.add(mJSONArray.getJSONObject(ii).getString("name"));
        }

        return genres;

    }

    public static List<String> getCountries(String countriesList) throws JSONException {

        JSONArray mJSONArray = new JSONArray(countriesList);
        int genresSize = mJSONArray.length();
        List<String> genres = new ArrayList<>();

        for (int ii = 0; ii < genresSize; ii++) {
            genres.add(mJSONArray.getJSONObject(ii).getString("name"));
        }

        return genres;

    }

    public static List<VideosInfo> getVideos(JSONObject json) throws JSONException {

        JSONArray jsonVideos = json.getJSONArray("results");
        int videosSize = jsonVideos.length();

        List<VideosInfo> videos = new ArrayList<>();

        for (int i = 0; i < videosSize; i++) {

            JSONObject jsonVideo = jsonVideos.getJSONObject(i);

            VideosInfo video = new VideosInfo();
            video.setId(jsonVideo.getString("id"));
            video.setIso(jsonVideo.getString("iso_639_1"));
            video.setKey(jsonVideo.getString("key"));
            video.setName(jsonVideo.getString("name"));
            video.setSite(jsonVideo.getString("site"));
            video.setSize(jsonVideo.getString("size"));
            video.setType(jsonVideo.getString("type"));
            videos.add(video);
        }

        return videos;

    }

    public static List<ReviewsInfo> getReviews(JSONObject json) throws JSONException {

        JSONArray jsonReviews = json.getJSONArray("results");
        int reviewsSize = jsonReviews.length();

        List<ReviewsInfo> reviews = new ArrayList<>();

        for (int i = 0; i < reviewsSize; i++) {

            JSONObject jsonReview = jsonReviews.getJSONObject(i);

            ReviewsInfo review = new ReviewsInfo();
            review.setId(jsonReview.getString("id"));
            review.setAuthor(jsonReview.getString("author"));
            review.setContent(jsonReview.getString("content"));
            review.setUrl(jsonReview.getString("url"));
            reviews.add(review);

        }

        return reviews;

    }
}
