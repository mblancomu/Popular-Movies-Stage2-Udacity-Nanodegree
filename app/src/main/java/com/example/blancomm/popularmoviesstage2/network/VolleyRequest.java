package com.example.blancomm.popularmoviesstage2.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.blancomm.popularmoviesstage2.VolleyListeners;
import com.example.blancomm.popularmoviesstage2.app.AppController;
import com.example.blancomm.popularmoviesstage2.utils.Constant;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class VolleyRequest {

    private static String TAG = VolleyRequest.class.getSimpleName();

    public VolleyRequest(Context context) {
    }


    /**
     * Request for get all movies from the service.
     * @param listeners
     * @param url
     */
    public static void requestJsonMovies(final VolleyListeners listeners, String url) {

        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        listeners.onFinishJsonMoviesRequest(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        // Request queue on Application class manager.
        AppController.getInstance().addToRequestQueue(jsObjRequest, Constant.TAG_JSON);

    }

    /**
     * Request for get only the json from videos. Need the id of movie for get all trailers.
     * @param listeners
     * @param url
     */
    public static void requestJsonVideos(final VolleyListeners listeners, String url) {

        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        listeners.onFinishJsonVideosRequest(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        // Request queue on Application class manager.
        AppController.getInstance().addToRequestQueue(jsObjRequest, Constant.TAG_JSON);

    }

    /**
     * Request for get only the json from reviews. Need the id of movie for get all reviews.
     * @param listeners
     * @param url
     */
    public static void requestJsonReviews(final VolleyListeners listeners, String url) {

        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Reviews : " + response.toString());

                        listeners.onFinishJsonReviewsRequest(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        // Request queue on Application class manager.
        AppController.getInstance().addToRequestQueue(jsObjRequest, Constant.TAG_JSON);

    }

    /**
     * Request for get the images for any movie, thumbnail and detail.
     * @param url
     * @param networkImageView
     */
    public static void requestImage(String url, NetworkImageView networkImageView) {

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        // If you are using NetworkImageView
        networkImageView.setImageUrl(url, imageLoader);

        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(Constant.URL_DETAIL_IMAGE);
        if (entry != null) {
            try {
                String data = new String(entry.data, "UTF-8");
                // handle data, like converting it to xml, json, bitmap etc.,
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            // cach
        }

    }
}
