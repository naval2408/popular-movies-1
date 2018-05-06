package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavouriteDbHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;
    public FavouriteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVOURITE_TABLE = "CREATE TABLE "  + FavouriteContract.FavouriteEntry.TABLE_NAME + " (" +
                FavouriteContract.FavouriteEntry._ID                + " INTEGER PRIMARY KEY, " +
                FavouriteContract.FavouriteEntry.COLUMN_VOTE_AVERAGE + " TEXT NOT NULL, " +
                FavouriteContract.FavouriteEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                FavouriteContract.FavouriteEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                FavouriteContract.FavouriteEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                FavouriteContract.FavouriteEntry.COLUMN_ID + " TEXT NOT NULL, " +
                FavouriteContract.FavouriteEntry.COLUMN_TITLE    + " TEXT NOT NULL);";


        sqLiteDatabase.execSQL(SQL_CREATE_FAVOURITE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavouriteContract.FavouriteEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
