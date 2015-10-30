package com.crubio.googleimagesearch.handler;

import android.preference.PreferenceActivity;
import android.util.Log;

import com.crubio.googleimagesearch.model.Image;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class GoogleImageHandler extends JsonHttpResponseHandler {

    private List<Image> images;

    public GoogleImageHandler(List<Image> images){
        this.images = images;
    }

    @Override
    public void onSuccess(int statusCode, PreferenceActivity.Header[] headers, JSONObject response) {

        try {
            JSONArray imagesJSON = response.getJSONObject("responseData").getJSONArray("results");
            for (int i = 0; i < imagesJSON.length(); i++) {
                JSONObject imageJSON = imagesJSON.getJSONObject(i);
                String url = imageJSON.getString("url");
                Image image = new Image(url);
                images.add(image);
            }
//            swipeContainer.setRefreshing(false);

        } catch (JSONException e) {
            Log.e("ERROR", "Error parsing JSON", e);
        }

        adapter.notifyDataSetChanged();
    }

}
