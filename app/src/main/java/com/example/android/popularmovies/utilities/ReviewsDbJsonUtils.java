package com.example.android.popularmovies.utilities;

import android.content.Context;

import com.example.android.popularmovies.moviesDb.Reviews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReviewsDbJsonUtils {
    public static ArrayList<Reviews> getReviewsObjectFromJson(Context context, JSONObject responseJson) throws JSONException
    {
        if(responseJson==null||responseJson.length()==0)
        {
            return null;
        }
        final String RESULTS ="results";
        final String STATUS_CODE="status_code";
        ArrayList<Reviews> parsedReviewsObjects = new ArrayList<Reviews>();
        if(responseJson.has(STATUS_CODE))
        {
            return null;
        }
        JSONArray resultArray = responseJson.getJSONArray(RESULTS);
        for(int i=0;i<resultArray.length();i++)
        {
            JSONObject reviewJson = resultArray.getJSONObject(i);
            Reviews reviews = new Reviews(reviewJson);
            parsedReviewsObjects.add(reviews);
        }
        return  parsedReviewsObjects;

    }


}

