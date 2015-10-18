package com.example.blancomm.popularmoviesstage2.utils;

import android.net.Uri;

import java.io.UnsupportedEncodingException;

/**
 * Created by BlancoMM on 14-Oct-15.
 */
public class URLUtils {

    public static String getURLPopularity(int page) throws UnsupportedEncodingException {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(Constant.SCHEME_URL)
                .authority(Constant.AUTHORITY_URL)
                .appendPath(Constant.PATH_3)
                .appendPath(Constant.PATH_DISCOVER)
                .appendPath(Constant.PATH_MOVIE)
                .appendQueryParameter(Constant.QUERY_PARAM_SORT, Constant.PARAM_POPULARITY + Constant.PARAM_DESC)
                .appendQueryParameter(Constant.QUERY_PARAM_API_KEY, Constant.PARAM_API_KEY)
                .appendQueryParameter(Constant.QUERY_PARAM_PAGE, String.valueOf(page));

        return builder.build().toString();
    }

    public static String getURLLatest(int page) throws UnsupportedEncodingException {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(Constant.SCHEME_URL)
                .authority(Constant.AUTHORITY_URL)
                .appendPath(Constant.PATH_3)
                .appendPath(Constant.PATH_DISCOVER)
                .appendPath(Constant.PATH_MOVIE)
                .appendQueryParameter(Constant.QUERY_PARAM_SORT, Constant.PARAM_VOTE_COUNT + Constant.PARAM_DESC)
                .appendQueryParameter(Constant.QUERY_PARAM_API_KEY, Constant.PARAM_API_KEY)
                .appendQueryParameter(Constant.QUERY_PARAM_PAGE, String.valueOf(page));

        return builder.build().toString();
    }

    public static String getURLRate(int page) throws UnsupportedEncodingException {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(Constant.SCHEME_URL)
                .authority(Constant.AUTHORITY_URL)
                .appendPath(Constant.PATH_3)
                .appendPath(Constant.PATH_DISCOVER)
                .appendPath(Constant.PATH_MOVIE)
                .appendQueryParameter(Constant.QUERY_PARAM_SORT, Constant.PARAM_VOTE_AVERAGE + Constant.PARAM_DESC)
                .appendQueryParameter(Constant.QUERY_PARAM_API_KEY, Constant.PARAM_API_KEY)
                .appendQueryParameter(Constant.QUERY_PARAM_PAGE, String.valueOf(page));

        return builder.build().toString();
    }

    public static String getURLMovieDtail(String idMovie) throws UnsupportedEncodingException {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(Constant.SCHEME_URL)
                .authority(Constant.AUTHORITY_URL)
                .appendPath(Constant.PATH_3)
                .appendPath(Constant.PATH_MOVIE)
                .appendPath(idMovie)
                .appendQueryParameter(Constant.QUERY_PARAM_API_KEY, Constant.PARAM_API_KEY);

        return builder.build().toString();
    }

    public static String getURLMovieReviews(String idMovie) throws UnsupportedEncodingException {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(Constant.SCHEME_URL)
                .authority(Constant.AUTHORITY_URL)
                .appendPath(Constant.PATH_3)
                .appendPath(Constant.PATH_MOVIE)
                .appendPath(idMovie)
                .appendPath(Constant.PATH_REVIEWS)
                .appendQueryParameter(Constant.QUERY_PARAM_API_KEY, Constant.PARAM_API_KEY);

        return builder.build().toString();
    }

    public static String getURLMovieVideos(String idMovie) throws UnsupportedEncodingException {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(Constant.SCHEME_URL)
                .authority(Constant.AUTHORITY_URL)
                .appendPath(Constant.PATH_3)
                .appendPath(Constant.PATH_MOVIE)
                .appendPath(idMovie)
                .appendPath(Constant.PATH_VIDEOS)
                .appendQueryParameter(Constant.QUERY_PARAM_API_KEY, Constant.PARAM_API_KEY);

        return builder.build().toString();
    }

    public static String getURLTrailerYouTube(String keyMovie) throws UnsupportedEncodingException {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(Constant.SCHEME_URLS)
                .authority(Constant.AUTHORITY_URL_YOUTUBE)
                .appendPath(Constant.PATH_WATCH)
                .appendQueryParameter(Constant.QUERY_PARAM_V, keyMovie);

        return builder.build().toString();
    }
}
