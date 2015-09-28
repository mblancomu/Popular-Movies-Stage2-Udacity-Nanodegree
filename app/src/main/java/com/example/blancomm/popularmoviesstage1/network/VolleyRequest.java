package com.example.blancomm.popularmoviesstage1.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.blancomm.popularmoviesstage1.VolleyListeners;
import com.example.blancomm.popularmoviesstage1.app.AppController;
import com.example.blancomm.popularmoviesstage1.utils.Constant;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class VolleyRequest {

    private static String TAG = VolleyRequest.class.getSimpleName();

    public VolleyRequest(Context context) {
    }


    public static void requestJson(final VolleyListeners listeners, String url) {

        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Response: " + response.toString());

                        listeners.onFinishJsonRequest(response);
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

    public static void requestImage(String url, NetworkImageView networkImageView) {

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        // If you are using NetworkImageView
        networkImageView.setImageUrl(url, imageLoader);


        // If you are using normal ImageView
        /*imageLoader.get(Const.URL_IMAGE, new ImageListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e(TAG, "Image Load Error: " + error.getMessage());
			}

			@Override
			public void onResponse(ImageContainer response, boolean arg1) {
				if (response.getBitmap() != null) {
					// load image into imageview
					imageView.setImageBitmap(response.getBitmap());
				}
			}
		});*/

        // Loading image with placeholder and error image
        /*imageLoader.get(Constant.URL_IMAGE_MOVIE_DB, ImageLoader.getImageListener(
                networkImageView, R.drawable.ico_loading, R.drawable.ico_error));*/

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
