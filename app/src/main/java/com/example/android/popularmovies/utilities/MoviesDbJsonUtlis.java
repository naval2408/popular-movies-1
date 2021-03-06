package com.example.android.popularmovies.utilities;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Movie;

import com.example.android.popularmovies.moviesDb.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by navalkishore on 28/03/18.
 */

public class MoviesDbJsonUtlis {
    public static ArrayList<Movies> getMoviesObjectFromJson(Context context,JSONObject responseJson) throws JSONException
    {
        if(responseJson==null||responseJson.length()==0)
        {
            return null;
        }
        final String RESULTS ="results";
        final String STATUS_CODE="status_code";
        ArrayList<Movies> parsedMovieObjects = new ArrayList<Movies>();
        if(responseJson.has(STATUS_CODE))
        {
            return null;
        }
        JSONArray resultArray = responseJson.getJSONArray(RESULTS);
        for(int i=0;i<resultArray.length();i++)
        {
            JSONObject movieJson = resultArray.getJSONObject(i);
            Movies movies = new Movies(movieJson);
            parsedMovieObjects.add(movies);
        }
        return  parsedMovieObjects;

    }

    public static ArrayList<Movies> getMoviesObjectFromCursor(Context context,Cursor movieCursor)
    {
        ArrayList<Movies> parsedMovieObjects = new ArrayList<Movies>();
        if(movieCursor==null||movieCursor.getCount()==0)
        {
            return parsedMovieObjects;
        }

        for (movieCursor.moveToFirst(); !movieCursor.isAfterLast(); movieCursor.moveToNext()) {
            Movies movie = new Movies(movieCursor);
            parsedMovieObjects.add(movie);

        }
        movieCursor.close();
    return parsedMovieObjects;
    }


}
