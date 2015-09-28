package com.example.blancomm.popularmoviesstage1.utils;

public class Constant {

    //API Key MoviesDB
    public static final String API_KEY = "api_key=**********************";

    //Constants for the differente URLs
    public static final String URL_JSON_MOVIE_LIST = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&"
            + API_KEY;
    public static final String URL_DETAIL_IMAGE = "https://image.tmdb.org/t/p/w342/";
    public static final String URL_THUMNAIL_IMAGE = "https://image.tmdb.org/t/p/w342/";
    public static final String URL_DETAIL_MOVIE = "https://api.themoviedb.org/3/movie/";
    public static final String URL_THUMB_MOVIE_DB = "";

    //Constants for several TAGs
    public static final String TAG_JSON = "tag_json";
    public static final String TAG_IMAGE = "tag_image";



}
