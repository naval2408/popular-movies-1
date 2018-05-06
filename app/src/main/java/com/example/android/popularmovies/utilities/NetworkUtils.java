package com.example.android.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by navalkishore on 28/03/18.
 */

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie";
    private static final String API_KEY_PARAMETER = "api_key";
    private static final String TRAILERS_KEY_PARAMETER = "videos";
    private static final String REVIEWS_KEY_PARAMETER = "reviews";
    private static final String LANGUAGE_PARAMETER = "language";
    private static final String PAGE_PARAMETER = "page";
    private static final String API_KEY_VALUE = "";//Insert key here
    private static final String LANGUAGE_VALUE = "en-US";
    private static final int PAGE_VALUE = 1;

    public static URL buildURL(String sortQuery) {
        Uri builtUri = Uri.parse(BASE_URL + sortQuery).buildUpon().appendQueryParameter(API_KEY_PARAMETER, API_KEY_VALUE).appendQueryParameter(LANGUAGE_PARAMETER, LANGUAGE_VALUE).appendQueryParameter(PAGE_PARAMETER, Integer.toString(PAGE_VALUE)).build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static URL buildTrailerURL(String inputMovieID) {
        int movieID = Integer.parseInt(inputMovieID);
        Uri builtUri = Uri.parse(BASE_URL + "/" + movieID + "/" + TRAILERS_KEY_PARAMETER).buildUpon().appendQueryParameter(API_KEY_PARAMETER, API_KEY_VALUE).appendQueryParameter(LANGUAGE_PARAMETER, LANGUAGE_VALUE).appendQueryParameter(PAGE_PARAMETER, Integer.toString(PAGE_VALUE)).build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Trailer URI " + url);

        return url;
    }


    public static URL buildReviewsURL(String movieID) {
        Uri builtUri = Uri.parse(BASE_URL + "/" + movieID + "/" + REVIEWS_KEY_PARAMETER).buildUpon().appendQueryParameter(API_KEY_PARAMETER, API_KEY_VALUE).appendQueryParameter(LANGUAGE_PARAMETER, LANGUAGE_VALUE).appendQueryParameter(PAGE_PARAMETER, Integer.toString(PAGE_VALUE)).build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Reviews URI " + url);

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {

            urlConnection.disconnect();
        }
    }


}
