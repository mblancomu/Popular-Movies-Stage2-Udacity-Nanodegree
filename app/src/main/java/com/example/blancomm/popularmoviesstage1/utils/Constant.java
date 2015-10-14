package com.example.blancomm.popularmoviesstage1.utils;

public class Constant {

    //URLs for get the images
    public static final String URL_DETAIL_IMAGE = "https://image.tmdb.org/t/p/w500/";
    public static final String URL_THUMNAIL_IMAGE = "https://image.tmdb.org/t/p/";
    public static final String URL_YOUTUBE = "https://www.youtube.com/watch?v=";

    //Constants for several TAGs
    public static final String TAG_JSON = "tag_json";
    public static final String TAG_IMAGE = "tag_image";

    //Constants values for argunments data on fragments.
    public static final String TAG_ID_MOVIE = "tag_id_movie";

    //Fragments tags for id the fragment;
    public static final String TAG_DETAIL_FRAGMENT = "fragment_detail";

   //URL for crequest services
    public static final String SCHEME_URL = "http";
    public static final String AUTHORITY_URL = "api.themoviedb.org";

    //Path for create UrlBuilder
    public static final String PATH_3 = "3";
    public static final String PATH_DISCOVER = "discover";
    public static final String PATH_MOVIE = "movie";
    public static final String PATH_VIDEOS = "videos";

    //QUERIES for the url builder
    public static final String QUERY_PARAM_SORT = "sort_by";
    public static final String QUERY_PARAM_API_KEY = "api_key";
    public static final String QUERY_PARAM_PAGE = "page";

    //Param API KEY. Is a personal value.
    public static final String PARAM_API_KEY = "*********************";

    //Queries values for any param in url and field on JSONObject Detail
    public static final String PARAM_ADULT = "adult";
    public static final String PARAM_OVERVIEW = "overview";
    public static final String PARAM_GENRES = "genres";
    public static final String PARAM_GENRES_IDS = "genre_ids";
    public static final String PARAM_BACKDROP = "backdrop_path";
    public static final String PARAM_TITLE = "title";
    public static final String PARAM_ID = "id";
    public static final String PARAM_ORI_LANGUAGE = "original_language";
    public static final String PARAM_ORI_TITLE = "original_title";
    public static final String PARAM_RE_DATE = "release_date";
    public static final String PARAM_POSTER = "poster_path";
    public static final String PARAM_POPULARITY = "popularity";
    public static final String PARAM_VIDEO = "video";
    public static final String PARAM_VOTE_AVERAGE = "vote_average";
    public static final String PARAM_VOTE_COUNT = "vote_count";
    public static final String PARAM_HOMEPAGE = "homepage";
    public static final String PARAM_IMDBID = "imdb_id";
    public static final String PARAM_PRO_COMPANIES = "production_companies";
    public static final String PARAM_PRO_COUNTRIES = "production_countries";
    public static final String PARAM_REVENUE = "revenue";
    public static final String PARAM_RUNTIME = "runtime";
    public static final String PARAM_SPOKEN_LAN = "spoken_languages";
    public static final String PARAM_STATUS = "status";
    public static final String PARAM_TAGLINE = "tagline";

    //Param for order the results on the request
    public static final String PARAM_DESC = ".desc";

}
