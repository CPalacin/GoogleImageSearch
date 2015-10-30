package com.crubio.googleimagesearch.handler;

import android.preference.PreferenceActivity;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by crubio on 10/29/2015.
 */
public class GoogleImageHanler extends JsonHttpResponseHandler {
    @Override
    public void onSuccess(int statusCode, PreferenceActivity.Header[] headers, JSONObject response) {

        try {
            instagramPhotos.clear();
            JSONArray photosJSON = response.getJSONArray("data");
            for (int i = 0; i < photosJSON.length(); i++) {
                JSONObject photoJSON = photosJSON.getJSONObject(i);

                String id = photoJSON.getString("id");

                boolean isVideo = photoJSON.getString("type").equalsIgnoreCase("video");
                String photoUrl = null;
                if (isVideo) {
                    JSONObject standardRes = photoJSON.getJSONObject("videos")
                            .getJSONObject("standard_resolution");

                    photoUrl = standardRes.getString("url");
                } else {
                    JSONObject standardRes = photoJSON.getJSONObject("images")
                            .getJSONObject("standard_resolution");

                    photoUrl = standardRes.getString("url");
                }
                long created = photoJSON.getLong("created_time");

                JSONObject user = photoJSON.getJSONObject("user");
                int likes = photoJSON.getJSONObject("likes").getInt("count");
                String userName = user.getString("username");
                String profilePicture = user.getString("profile_picture");

                JSONArray comments = photoJSON.getJSONObject("comments").getJSONArray("data");

                List<Comment> commentList = new ArrayList<Comment>();

                String caption = null;
                if (!photoJSON.isNull("caption")) {
                    caption = photoJSON.getJSONObject("caption").getString("text");
                    Comment comment = new Comment(userName, caption);
                    commentList.add(comment);
                }

                commentList.addAll(getComments(comments, NUM_COMMENTS));

                if (comments.length() > NUM_COMMENTS) {
                    String numComments = photoJSON.getJSONObject("comments").getString("count");
                    Comment comment = new Comment(null, "View all " + numComments + " comments");
                    commentList.add(comment);
                }

                Photo photo = new Photo(isVideo, id, photoUrl, created, likes, userName,
                        profilePicture, commentList, caption);
                instagramPhotos.add(photo);
            }
            swipeContainer.setRefreshing(false);

        } catch (JSONException e) {
            Log.e("ERROR", "Error parsing JSON", e);
        }

        adapter.notifyDataSetChanged();
    }

}
