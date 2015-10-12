package com.example.blancomm.popularmoviesstage1.utils;

public class Constant {

    //API Key MoviesDB
    public static final String API_KEY = "api_key=++++++++++++++++";
    public static final String NEW_PAGE = "&page=";

    //Constants for the differente URLs
    public static final String URL_JSON_MOVIE_LIST_POPULARITY = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&"
            + API_KEY;
    public static final String URL_JSON_MOVIE_LIST_LATEST = "http://api.themoviedb.org/3/discover/movie?sort_by=vote_count.desc&"
            + API_KEY;
    public static final String URL_JSON_MOVIE_LIST_HIGHTEST_RATE = "http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&"
            + API_KEY;
    public static final String URL_DETAIL_IMAGE = "https://image.tmdb.org/t/p/w500/";
    public static final String URL_THUMNAIL_IMAGE = "https://image.tmdb.org/t/p/";
    public static final String URL_THUMNAIL_IMAGE_DETAIL = "https://image.tmdb.org/t/p/w185/";
    public static final String URL_DETAIL_MOVIE = "https://api.themoviedb.org/3/movie/";
    public static final String URL_THUMB_MOVIE_DB = "";

    //Constants for several TAGs
    public static final String TAG_JSON = "tag_json";
    public static final String TAG_IMAGE = "tag_image";

    //Constants values for argunments data on fragments.
    public static final String TAG_ID_MOVIE = "tag_id_movie";

    //Fragments tags for id the fragment;
    public static final String TAG_DETAIL_FRAGMENT = "fragment_detail";



}
