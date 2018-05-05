package com.example.android.popularmovies.moviesDb;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Reviews implements Serializable {
    private final String AUTHOR = "author";
    private final String CONTENT = "content";
    private final String REVIEW_ID = "id";
    private final String REVIEW_URL = "url";
    private String authorValue;
    private String contentValue;
    private String reviewIDValue;
    private String reviewURLValue;


    public Reviews(JSONObject reviewsJson) {
        try {
            authorValue = reviewsJson.getString(AUTHOR);
            contentValue = reviewsJson.getString(CONTENT);
            reviewIDValue = reviewsJson.getString(REVIEW_ID);
            reviewURLValue = reviewsJson.getString(REVIEW_URL);

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }


    public String getAuthorValue() {
        return authorValue;
    }

    public String getContentValue() {
        return contentValue;
    }

    public String getReviewIDValue() {
        return reviewIDValue;
    }

    public String getReviewURLValue() {
        return reviewURLValue;
    }


}
