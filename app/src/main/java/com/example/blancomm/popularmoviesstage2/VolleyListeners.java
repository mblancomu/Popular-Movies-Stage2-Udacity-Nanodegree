package com.example.blancomm.popularmoviesstage2;


import org.json.JSONObject;

public interface VolleyListeners {

    void onFinishJsonMoviesRequest(JSONObject jsonObject);
    void onFinishJsonFavoritesRequest(JSONObject jsonObject);
    void onFinishJsonVideosRequest(JSONObject jsonObject);
    void onFinishJsonReviewsRequest(JSONObject jsonObject);
}
