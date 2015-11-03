package com.crubio.googleimagesearch.handler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.crubio.googleimagesearch.model.Image;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class GoogleImageHandler extends JsonHttpResponseHandler {

    private List<Image> images;
    private RecyclerView.Adapter adapter;

    public GoogleImageHandler(List<Image> images, RecyclerView.Adapter adapter){
        this.images = images;
        this.adapter = adapter;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

        try {
            JSONArray imagesJSON = response.getJSONObject("responseData").getJSONArray("results");
            for (int i = 0; i < imagesJSON.length(); i++) {
                JSONObject imageJSON = imagesJSON.getJSONObject(i);

                String url = imageJSON.getString("url");
                String tbUrl = imageJSON.getString("tbUrl");
                String title = imageJSON.getString("titleNoFormatting");
                int tbWidth = imageJSON.getInt("tbWidth");
                int tbHeight = imageJSON.getInt("tbHeight");
                Image image = new Image(url, tbUrl, title, tbWidth, tbHeight);
                images.add(image);
            }

        } catch (JSONException e) {
            Log.e("ERROR", "Error parsing JSON", e);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        super.onFailure(statusCode, headers, responseString, throwable);
        Log.e("GoogleImageHandler", "Error response. Code: "+ statusCode);
    }
}
