package com.example.popularmovies.data.databasedata;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavoriteContract {

    // Authority to access content provider
    static final String AUTHORITY = "com.example.popularmovies";

    // The base content URI
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "favorites" directory
    static final String PATH_FAVORITES = "favorites";

    // The name of the database
    static final String DATABASE_NAME = "favorites.db";

    // The version of the database
    static final int DATABASE_VERSION = 1;

    /* FavoritesEntry is an inner class that defines the contents of the task table */
    public static final class FavoritesEntry implements BaseColumns {

        // FavoritesEntry content URI
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

        // Favorites table name
        static final String TABLE_NAME = "favorites";

        // Favorites column names
        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_MOVIE_TITLE = "title";

        // Create favorites table
        static final String CREATE_TABLE_FAVORITES =
                "CREATE TABLE " + FavoritesEntry.TABLE_NAME + "(" +
                        FavoritesEntry._ID + " INTEGER PRIMARY KEY, " +
                        FavoritesEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL," +
                        FavoritesEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL);";

        static final String DELETE_TABLE_FAVORITES =
                "DROP TABLE IF EXISTS " + FavoritesEntry.TABLE_NAME;
    }

}
