package com.example.android.popularmovies.moviesDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by navalkishore on 28/03/18.
 */

public class Movies implements Serializable{
    private final String VOTE_COUNT="vote_count";
    private final String ID="id";
    private final String VIDEO="video";
    private final String VOTE_AVERAGE="vote_average";
    private final String TITLE="title";
    private final String POPULARITY="popularity";
    private final String POSTER_PATH="poster_path";
    private final String ORIGINAL_LANGUAGE="original_language";
    private final String ORIGINAL_TITLE="original_title";
    private final String GENRE_IDS="genre_ids";
    private final String BACKDROP_PATH="backdrop_path";
    private final String ADULT="adult";
    private final String OVERVIEW ="overview";
    private final String RELEASE_DATE="release_date";
    private int voteCountValue;
    private int idValue;
    private boolean videoValue;
    private double voteAverageValue;
    private String titleValue;
    private double popularityValue;
    private String posterPathValue;
    private String originalLanguageValue;
    private String originalTitleValue;
    private ArrayList<Integer> genreIdsValue;
    private String backdropPathValue;
    private boolean adultValue;
    private String overviewValue;
    private String releaseDateValue;

    public Movies(JSONObject moviesJson)
    {
        try {
            voteCountValue=moviesJson.getInt(VOTE_COUNT);
            idValue=moviesJson.getInt(ID);
            videoValue=moviesJson.getBoolean(VIDEO);
            voteAverageValue=moviesJson.getDouble(VOTE_AVERAGE);
            titleValue=moviesJson.getString(TITLE);
            popularityValue=moviesJson.getDouble(POPULARITY);
            posterPathValue=moviesJson.getString(POSTER_PATH);
            originalLanguageValue=moviesJson.getString(ORIGINAL_LANGUAGE);
            originalTitleValue=moviesJson.getString(ORIGINAL_TITLE);
            genreIdsValue= new ArrayList<Integer>();
            JSONArray getGenreIdsArray = moviesJson.getJSONArray(GENRE_IDS);
            for(int i=0;i<getGenreIdsArray.length();i++)
            {
                genreIdsValue.add(getGenreIdsArray.getInt(i));
            }
            backdropPathValue=moviesJson.getString(BACKDROP_PATH);
            adultValue=moviesJson.getBoolean(ADULT);
            overviewValue=moviesJson.getString(OVERVIEW);
            releaseDateValue=moviesJson.getString(RELEASE_DATE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getVoteCountValue() {
        return voteCountValue;
    }

    public int getIdValue() {
        return idValue;
    }

    public boolean isVideoValue() {
        return videoValue;
    }

    public double getVoteAverageValue() {
        return voteAverageValue;
    }

    public String getTitleValue() {
        return titleValue;
    }

    public double getPopularityValue() {
        return popularityValue;
    }

    public String getPosterPathValue() {
        return posterPathValue;
    }

    public String getOriginalLanguageValue() {
        return originalLanguageValue;
    }

    public String getOriginalTitleValue() {
        return originalTitleValue;
    }

    public ArrayList<Integer> getGenreIdsValue() {
        return genreIdsValue;
    }

    public String getBackdropPathValue() {
        return backdropPathValue;
    }

    public boolean isAdultValue() {
        return adultValue;
    }

    public String getOverviewValue() {
        return overviewValue;
    }

    public String getReleaseDateValue() {
        return releaseDateValue;
    }
}
