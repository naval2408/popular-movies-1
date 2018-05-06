package com.example.android.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavouriteContract {
    public static final String AUTHORITY = "com.example.android.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_FAVOURITES = "favourites";

    public static class FavouriteEntry implements BaseColumns{
        public static final Uri FAVOURITES_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITES).build();
        public static final String TABLE_NAME = "favourites";
        public static final String COLUMN_ID="moviesId";
        public static final String COLUMN_TITLE="moviesTitle";
        public static final String COLUMN_POSTER_PATH="moviesPosterPath";
        public static final String COLUMN_VOTE_AVERAGE="moviesVoteAverage";
        public static final String COLUMN_RELEASE_DATE="moviesReleaseDate";
        public static final String COLUMN_OVERVIEW="moviesOverview";



    }
}
