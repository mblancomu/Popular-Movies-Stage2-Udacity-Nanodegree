package com.example.blancomm.popularmoviesstage1;


import android.graphics.Bitmap;

import org.json.JSONObject;

public interface VolleyListeners {

    void onFinishJsonMoviesRequest(JSONObject jsonObject);
    void onFinishJsonVideosRequest(JSONObject jsonObject);
    void onFinishJsonReviewsRequest(JSONObject jsonObject);
}
