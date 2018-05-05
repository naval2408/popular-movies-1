package com.example.android.popularmovies.utilities;

import android.content.Context;

import com.example.android.popularmovies.moviesDb.Movies;
import com.example.android.popularmovies.moviesDb.Trailers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrailersDbJsonUtils {
        public static ArrayList<Trailers> getTrailersObjectFromJson(Context context, JSONObject responseJson) throws JSONException
        {
            if(responseJson==null||responseJson.length()==0)
            {
                return null;
            }
            final String RESULTS ="results";
            final String STATUS_CODE="status_code";
            ArrayList<Trailers> parsedTrailersObjects = new ArrayList<Trailers>();
            if(responseJson.has(STATUS_CODE))
            {
                return null;
            }
            JSONArray resultArray = responseJson.getJSONArray(RESULTS);
            for(int i=0;i<resultArray.length();i++)
            {
                JSONObject trailersJson = resultArray.getJSONObject(i);
                Trailers trailers = new Trailers(trailersJson);
                parsedTrailersObjects.add(trailers);
            }
            return  parsedTrailersObjects;

        }


}
